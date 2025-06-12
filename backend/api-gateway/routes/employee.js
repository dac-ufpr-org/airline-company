// api-gateway/routes/employee.js
const { createProxyMiddleware } = require('http-proxy-middleware');
const express = require('express');
const router = express.Router();

const employeeServiceUrl = process.env.MS_FUNCIONARIO_URL; // Ex: http://localhost:808X (URL do seu MS de Funcionario)

// Configuração de proxy genérica para o microserviço de funcionários
// O pathRewrite { '^/api/employees': '/ms-funcionario' } vai remover o '/api/employees'
// e adicionar '/ms-funcionario' na frente do resto do caminho.
// Ex: /api/employees/listar-funcionario -> /ms-funcionario/listar-funcionario
const employeeProxy = createProxyMiddleware({
    target: employeeServiceUrl,
    changeOrigin: true,
    pathRewrite: { '^/api/employees': '/ms-funcionario' }, // Este é o base path do seu Controller Java
    timeout: 5000 // Mantém o timeout
});

// Aplica este proxy para todas as requisições que começam com o caminho base do router (que é '/employees' no index.js)
// Isso significa que:
// - GET /api/employees/listar-funcionario vai para ${MS_FUNCIONARIO_URL}/ms-funcionario/listar-funcionario
// - GET /api/employees/listar-funcionario/{id} vai para ${MS_FUNCIONARIO_URL}/ms-funcionario/listar-funcionario/{id}
// - GET /api/employees/consultar-email/{email} vai para ${MS_FUNCIONARIO_URL}/ms-funcionario/consultar-email/{email}
// - POST /api/employees/ vai para ${MS_FUNCIONARIO_URL}/ms-funcionario/
router.use('/', employeeProxy);

module.exports = router;