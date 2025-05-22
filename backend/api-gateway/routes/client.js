const { createProxyMiddleware } = require('http-proxy-middleware')
const express = require('express')
const router = express.Router()

const clientServiceUrl = process.env.MS_CLIENTE_URL
const clientServiceSagaUrl = process.env.MS_CLIENTE_SAGA_URL

router.post(
  "/clientes/autocadastro", // Rota ajustada
  createProxyMiddleware({
    target: clientServiceSagaUrl,
    changeOrigin: true,
    pathRewrite: { "^/api/clientes/autocadastro": "/clientes/autocadastro" }
  })
)

router.get(
  "/clientes/email/:id",
  createProxyMiddleware({
    target: clientServiceUrl,
    changeOrigin: true,
    pathRewrite: (path, req) =>
      path.replace("/api/clientes/email", "/ms-cliente/check-email")
  })
)

router.get(
  "/clientes/endereco/:id",
  createProxyMiddleware({
    target: clientServiceUrl,
    changeOrigin: true,
    pathRewrite: (path, req) =>
      path.replace(
        "/api/clientes/endereco",
        "/ms-cliente/check-endereco"
      ),
  })
)

// Adicione esta linha no final para exportar o router
module.exports = router;