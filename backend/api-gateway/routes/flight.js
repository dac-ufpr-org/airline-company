const { createProxyMiddleware } = require('http-proxy-middleware');
const express = require('express');
const router = express.Router();

const flightServiceUrl = process.env.MS_VOO_URL;

router.get(
  "/api/flights",
  createProxyMiddleware({
    target: flightServiceUrl,
    changeOrigin: true,
    pathRewrite: { "^/api/flights": "/ms-voo/list" }
  })
);

module.exports = router;
