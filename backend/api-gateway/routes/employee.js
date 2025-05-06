const { createProxyMiddleware } = require('http-proxy-middleware');
const express = require('express');
const router = express.Router();

const employeeServiceUrl = process.env.MS_FUNCIONARIO_URL;

router.get(
  "/api/employees",
  createProxyMiddleware({
    target: employeeServiceUrl,
    changeOrigin: true,
    pathRewrite: { "^/api/employees": "/ms-funcionario/list" }
  })
);

module.exports = router;
