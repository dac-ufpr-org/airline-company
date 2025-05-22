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
    pathRewrite: { "^/clientes": "" } // Remove /clientes do path ao encaminhar
  })
)

router.get(
  "/clientes/email/:id",
  createProxyMiddleware({
    target: clientServiceUrl,
    changeOrigin: true,
    pathRewrite: { "^/clientes": "" } // Remove /clientes do path ao encaminhar
  })
)

router.get(
  "/clientes/endereco/:id",
  createProxyMiddleware({
    target: clientServiceUrl,
    changeOrigin: true,
    pathRewrite: { "^/clientes": "" } // Remove /clientes do path ao encaminhar
  })
)

// Adicione esta linha no final para exportar o router
module.exports = router;