const express = require('express');
const { createProxyMiddleware } = require('http-proxy-middleware');
const { authenticate } = require('../middleware/jwtAuth');

// Crie um router separado para rotas públicas
const publicRouter = express.Router();
const authRouter = express.Router();

// Configuração do proxy
const clientProxy = createProxyMiddleware({
  target: process.env.MS_CLIENTE_URL,
  changeOrigin: true,
  pathRewrite: { '^/api/clientes': '/clientes' },
  timeout: 5000
});

// Configuração do proxy SAGA
const clientSagaProxy = createProxyMiddleware({
  target: process.env.MS_CLIENTE_URL,
  changeOrigin: true,
  pathRewrite: {
    '^/api/clientes/autocadastro': '/clientes/autocadastro' // Path exato
  },
  onProxyReq: (proxyReq) => {
    console.log('Redirecionando para:', proxyReq.path); // Log para debug
  }
});

// Rota pública
publicRouter.post('/autocadastro', clientSagaProxy);

// Rotas autenticadas
authRouter.use(authenticate);
authRouter.get('/', clientProxy);
authRouter.get('/:id', clientProxy);

// Exporte os routers separadamente
module.exports = {
  publicRoutes: publicRouter,
  authenticatedRoutes: authRouter
};