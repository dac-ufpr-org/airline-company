const { createProxyMiddleware } = require('http-proxy-middleware');
const express = require('express');
const router = express.Router();

const employeeServiceUrl = process.env.MS_FUNCIONARIO_URL;

// Lista todos
router.get(
  "/api/employees",
  createProxyMiddleware({
    target: employeeServiceUrl,
    changeOrigin: true,
    pathRewrite: { "^/api/employees": "/ms-funcionario/listar-funcionario" }
  })
);

// Busca por ID
router.get(
  "/api/employees/:id",
  createProxyMiddleware({
    target: employeeServiceUrl,
    changeOrigin: true,
    pathRewrite: (path, req) =>
      path.replace(/^\/api\/employees/, "/ms-funcionario/listar-funcionario")
  })
);

// Busca por email
router.get(
  "/api/employees/email/:email",
  createProxyMiddleware({
    target: employeeServiceUrl,
    changeOrigin: true,
    pathRewrite: (path, req) =>
      path.replace(/^\/api\/employees\/email/, "/ms-funcionario/consultar-email")
  })
);

// Cadastro de funcionário
router.post(
  "/api/employees",
  createProxyMiddleware({
    target: employeeServiceUrl,
    changeOrigin: true,
    pathRewrite: { "^/api/employees": "/ms-funcionario" }
  })
);


module.exports = router;
