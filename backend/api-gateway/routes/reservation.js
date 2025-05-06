const { createProxyMiddleware } = require('http-proxy-middleware');
const express = require('express');
const router = express.Router();

const reservationServiceUrl = process.env.MS_RESERVA_URL;

router.post(
  "/api/reservations",
  createProxyMiddleware({
    target: reservationServiceUrl,
    changeOrigin: true,
    pathRewrite: { "^/api/reservations": "/ms-reserva/new" }
  })
);

router.get(
  "/api/reservations/:id",
  createProxyMiddleware({
    target: reservationServiceUrl,
    changeOrigin: true,
    pathRewrite: { "^/api/reservations": "/ms-reserva" }
  })
);

module.exports = router;
