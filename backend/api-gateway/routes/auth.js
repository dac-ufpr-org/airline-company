const { createProxyMiddleware } = require('http-proxy-middleware');
const express = require('express');
const router = express.Router();

const authServiceUrl = process.env.MS_AUTENTICACAO_URL;

router.post(
  "/api/auth/login",
  createProxyMiddleware({
    target: authServiceUrl,
    changeOrigin: true,
    pathRewrite: { "^/api/auth/login": "/ms-auth/login" }
  })
);

module.exports = router;
