// routes/client.js
const express = require('express');
const axios = require('axios');
const { createProxyMiddleware } = require('http-proxy-middleware');
const { authenticate } = require('../middleware/jwtAuth');

const publicRouter = express.Router();
const authRouter = express.Router();

/**
 * Rota pública para autocadastro de cliente via SAGA.
 * Inicia processo de orquestração que cria usuário + cliente + envia email.
 * 
 * Fluxo SAGA:
 * 1. API Gateway → ms-cliente (inicia SAGA)
 * 2. ms-cliente → ms-sagas (orquestrador)
 * 3. ms-sagas → ms-auth (criar usuário + senha)
 * 4. ms-auth → ms-sagas (confirmação)
 * 5. ms-sagas → ms-cliente (criar cliente)
 * 6. ms-cliente → ms-sagas (confirmação)
 * 7. Email enviado com senha temporária
 * 
 * @route POST /autocadastro
 * @param {Object} req.body - Dados do cliente para autocadastro
 * @returns {Object} Confirmação com correlationId para acompanhamento
 */
publicRouter.post('/autocadastro', async (req, res) => {
  try {
    const response = await axios.post(
      `${process.env.MS_CLIENTE_URL}/clientes/autocadastro`,
      req.body,
      { timeout: 5000 }
    );
    res.status(response.status).json(response.data);
  } catch (error) {
    // Erro de timeout (serviço não respondeu a tempo)
    if (error.code === 'ECONNABORTED') {
      return res.status(504).json({
        error: 'Gateway Timeout',
        message: 'Cliente Service não respondeu a tempo'
      });
    }

    // Erro de resposta do microserviço
    const status = error.response?.status || 500;
    const data = error.response?.data || { error: error.message };

    res.status(status).json({
      ...data,
      service: 'Cliente Service'
    });
  }
});

/**
 * Proxy para rotas autenticadas de clientes.
 * Encaminha requisições para o microserviço de clientes (ms-cliente).
 * Requer autenticação via middleware JWT.
 * 
 * Rotas disponíveis:
 * - GET /clientes: Lista todos os clientes
 * - GET /clientes/{cpf}: Busca cliente por CPF
 * - PUT /clientes/{cpf}: Atualiza dados do cliente
 * - POST /clientes: Cadastro normal de cliente
 */
const clientProxy = createProxyMiddleware({
  target: process.env.MS_CLIENTE_URL,
  changeOrigin: true,
  pathRewrite: { '^/api/clientes': '/clientes' },
  timeout: 5000
});

// Aplica autenticação JWT antes do proxy
authRouter.use(authenticate);
authRouter.use(clientProxy);

module.exports = { publicRouter, authRouter };
