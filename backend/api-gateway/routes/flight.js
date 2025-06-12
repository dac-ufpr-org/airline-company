// api-gateway/routes/flight.js
const { createProxyMiddleware } = require('http-proxy-middleware');
const express = require('express');
const router = express.Router();

const flightServiceUrl = process.env.MS_VOO_URL; // Ex: http://localhost:808Z (URL do seu MS de Voo)

// Configuração de proxy para o microserviço de voos (flights)
// Todas as requisições que chegarem aqui (ex: /api/flights/123)
// serão proxied para ${MS_VOO_URL}/flights/123
const flightProxy = createProxyMiddleware({
    target: flightServiceUrl,
    changeOrigin: true,
    // O pathRewrite agora apenas remove o prefixo '/api/flights'
    // e adiciona '/flights' na frente do resto do caminho.
    // Ex: /api/flights/ -> /flights/
    // Ex: /api/flights/123 -> /flights/123
    pathRewrite: { '^/api/flights': '/flights' }, // Este é o base path do seu FlightController Java
    timeout: 5000
});

// Aplica este proxy para todas as requisições que começam com o caminho base do router (que é '/flights' no index.js)
router.use('/', flightProxy);

module.exports = router;