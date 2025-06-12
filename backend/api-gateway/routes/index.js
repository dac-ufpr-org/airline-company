const express = require('express');
const router = express.Router();
const { authenticate } = require('../middleware/jwtAuth');

// Importação correta dos routers
const authRoutes = require('./auth');
const clientRoutes = require('./client');
const employeeRoutes = require('./employee'); // Descomente/adicione
const flightRoutes = require('./flight');     // Descomente/adicione
const reservationRoutes = require('./reservation'); // Descomente/adicione


// Rotas públicas (não precisam de JWT)
router.use('/auth', authRoutes); // /api/auth/login, /api/auth/register
router.use('/clientes', clientRoutes.publicRoutes); // /api/clientes/autocadastro

// Rotas autenticadas (precisam de JWT)
router.use('/clientes', clientRoutes.authenticatedRoutes); // /api/clientes (GET autenticado, :id)

// Descomente e inclua as rotas dos outros microserviços AQUI
// É uma boa prática aplicar o middleware 'authenticate' para rotas protegidas
router.use('/employees', authenticate, employeeRoutes); // /api/employees
router.use('/flights', authenticate, flightRoutes);     // /api/flights
router.use('/reservations', authenticate, reservationRoutes); // /api/reservations


// Rota de fallback para 404
router.use('*', (req, res) => {
    res.status(404).json({
        error: 'Not Found',
        message: 'Endpoint não encontrado'
    });
});

module.exports = router;