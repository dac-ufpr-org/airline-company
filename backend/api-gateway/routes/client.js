// routes/client.js
const express = require('express');
const axios = require('axios');
const { createProxyMiddleware } = require('http-proxy-middleware');
const { authenticate } = require('../middleware/jwtAuth');

const publicRouter = express.Router();
const authRouter = express.Router();

// ROTA: /autocadastro (pública)
publicRouter.post('/autocadastro', async (req, res) => {
  try {
    const response = await axios.post(
      `${process.env.MS_CLIENTE_URL}/clientes/autocadastro`,
      req.body,
      { timeout: 5000 }
    );
    res.status(response.status).json(response.data);
  } catch (error) {
    if (error.code === 'ECONNABORTED') {
      return res.status(504).json({
        error: 'Gateway Timeout',
        message: 'Cliente Service não respondeu a tempo'
      });
    }

    const status = error.response?.status || 500;
    const data = error.response?.data || { error: error.message };

    res.status(status).json({
      ...data,
      service: 'Cliente Service'
    });
  }
});

// ROTA: /clientes (autenticada)
const clientProxy = createProxyMiddleware({
  target: process.env.MS_CLIENTE_URL,
  changeOrigin: true,
  pathRewrite: { '^/api/clientes': '/clientes' },
  timeout: 5000
});

authRouter.use(authenticate);
authRouter.get('/', clientProxy);      // GET /api/clientes
authRouter.get('/:id', clientProxy);   // GET /api/clientes/:id

module.exports = {
  publicRoutes: publicRouter,
  authenticatedRoutes: authRouter
};
