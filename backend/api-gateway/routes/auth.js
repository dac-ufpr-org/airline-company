const { createProxyMiddleware } = require('http-proxy-middleware');
const express = require('express');
const router = express.Router();

const authServiceUrl = process.env.MS_AUTENTICACAO_URL;

router.post(
  "/auth/register",  
  createProxyMiddleware({
    target: authServiceUrl,
    changeOrigin: true,
    pathRewrite: { "^/auth": "" } // Remove /auth do path ao encaminhar
  })
);

router.post(
  "/auth/login",  
  createProxyMiddleware({
    target: authServiceUrl,
    changeOrigin: true,
    pathRewrite: { "^/auth": "" } // Remove /auth do path ao encaminhar
  })
);

module.exports = router;
