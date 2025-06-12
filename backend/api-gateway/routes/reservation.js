// api-gateway/routes/reservation.js
const { createProxyMiddleware } = require('http-proxy-middleware');
const express = require('express');
const router = express.Router();

const reservationServiceUrl = process.env.MS_RESERVA_URL; // Ex: http://localhost:808Y (URL do seu MS de Reserva)

// Configuração de proxy para o microserviço de reservas
// Todas as requisições que chegarem aqui (ex: /api/reservations/123)
// serão proxied para ${MS_RESERVA_URL}/reservas/123
const reservationProxy = createProxyMiddleware({
    target: reservationServiceUrl,
    changeOrigin: true,
    // O pathRewrite agora apenas remove o prefixo '/api/reservations'
    // e adiciona '/reservas' na frente do resto do caminho.
    // Ex: /api/reservations/ -> /reservas/
    // Ex: /api/reservations/123 -> /reservas/123
    pathRewrite: { '^/api/reservations': '/reservas' }, // Este é o base path do seu Controller Java
    timeout: 5000
});

// Aplica este proxy para todas as requisições que começam com o caminho base do router (que é '/reservations' no index.js)
router.use('/', reservationProxy);

module.exports = router;