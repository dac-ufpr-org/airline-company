const { createProxyMiddleware } = require('http-proxy-middleware');
const express = require('express');
const router = express.Router();

const reservationServiceUrl = process.env.MS_RESERVA_URL;

// Proxy para todos os métodos e subpaths de /reservations
router.use(
  '/reservations',
  createProxyMiddleware({
    target: reservationServiceUrl,
    changeOrigin: true,
    pathRewrite: { '^/api': '' },
  })
);

module.exports = router;
