const { createProxyMiddleware } = require('http-proxy-middleware')
const express = require('express')
const router = express.Router()

const clientServiceUrl = process.env.MS_CLIENTE_URL
const clientServiceSagaUrl = process.env.MS_CLIENTE_SAGA_URL

// Aplica JWT em todas as rotas de clientes
router.use(jwtMiddleware);
router.use(authErrorHandler);

router.post(
  "/clientes/autocadastro", // Rota ajustada
  createProxyMiddleware({
    target: clientServiceSagaUrl,
    changeOrigin: true,
    pathRewrite: { '^/clientes': '/clientes' }
  })
)

router.get(
  '/clientes',
  createProxyMiddleware({
    target: process.env.MS_CLIENTE_URL,
    changeOrigin: true,
    pathRewrite: { '^/clientes': '/clientes' }
  })
)

router.get(
  "/clientes/email/:id",
  createProxyMiddleware({
    target: clientServiceUrl,
    changeOrigin: true,
    pathRewrite: { '^/clientes': '/clientes' }
  })
)

router.get(
  "/clientes/endereco/:id",
  createProxyMiddleware({
    target: clientServiceUrl,
    changeOrigin: true,
    pathRewrite: { '^/clientes': '/clientes' }
  })
)

// Adicione esta linha no final para exportar o router
module.exports = router;