const express = require('express');
const router = express.Router();
const axios = require('axios');

// Timeout de 5 segundos para chamadas aos microserviços
const AXIOS_CONFIG = { timeout: 5000 };

/**
 * Rota para registro de usuário.
 * Encaminha requisição para o microserviço de autenticação (ms-auth).
 * 
 * @route POST /auth/register
 * @param {Object} req.body - Dados de registro (login, senha, tipo)
 * @returns {Object} Confirmação de registro com senha temporária
 */
router.post('/register', async (req, res) => {
  try {
    const response = await axios.post(
      `${process.env.MS_AUTENTICACAO_URL}/auth/register`,
      req.body,
      AXIOS_CONFIG
    );
    res.status(response.status).json(response.data);
  } catch (error) {
    handleServiceError(error, res, 'Authentication Service');
  }
});

/**
 * Rota para login de usuário.
 * Encaminha requisição para o microserviço de autenticação (ms-auth).
 * 
 * @route POST /auth/login
 * @param {Object} req.body - Dados de login (login, senha)
 * @returns {Object} Token JWT e tipo de usuário
 */
router.post('/login', async (req, res) => {
  try {
    const response = await axios.post(
      `${process.env.MS_AUTENTICACAO_URL}/auth/login`,
      req.body,
      AXIOS_CONFIG
    );
    res.status(response.status).json(response.data);
  } catch (error) {
    handleServiceError(error, res, 'Authentication Service');
  }
});

/**
 * Função para tratamento padronizado de erros dos microserviços.
 * Converte erros de timeout e outros problemas de comunicação em respostas HTTP apropriadas.
 * 
 * @param {Error} error - Erro capturado da chamada ao microserviço
 * @param {Response} res - Objeto de resposta do Express
 * @param {string} serviceName - Nome do serviço que falhou
 */
function handleServiceError(error, res, serviceName) {
  // Erro de timeout (serviço não respondeu a tempo)
  if (error.code === 'ECONNABORTED') {
    return res.status(504).json({
      error: 'Gateway Timeout',
      message: `${serviceName} não respondeu a tempo`
    });
  }

  // Erro de resposta do microserviço
  const status = error.response?.status || 500;
  const data = error.response?.data || { error: error.message };
  
  res.status(status).json({
    ...data,
    service: serviceName
  });
}

module.exports = router;