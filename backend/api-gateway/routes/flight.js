const { createProxyMiddleware } = require('http-proxy-middleware');
const express = require('express');
const router = express.Router();

const flightServiceUrl = process.env.MS_VOO_URL;

// Proxy para todos os métodos e subpaths de /flights
router.use(
  '/flights',
  createProxyMiddleware({
    target: flightServiceUrl,
    changeOrigin: true,
    pathRewrite: { '^/api': '' },
  })
);

module.exports = router;
