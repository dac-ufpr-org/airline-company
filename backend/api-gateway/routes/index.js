const express = require('express');
const router = express.Router();
const { authenticate } = require('../middleware/jwtAuth');

// Importação correta dos routers
const authRoutes = require('./auth');
const clientRoutes = require('./client');
const employeeRoutes = require('./employee');
const reservationRoutes = require('./reservation');

// Rotas públicas
router.use('/auth', authRoutes);
router.use('/clientes', clientRoutes.publicRoutes); // /api/clientes/autocadastro

// Rotas autenticadas
router.use('/clientes', clientRoutes.authenticatedRoutes); // /api/clientes (GET autenticado)
router.use('/api', employeeRoutes); // Ativa todas as rotas de funcionário (/api/employees, etc.)



// Não implementadas
//router.get('/clientes/:id', authenticate, clientRoutes.authenticatedRoutes);
//router.get('/employee', authenticate, employeeRoutes);
//router.get('/flight', authenticate, flightRoutes);
router.get('/reservation', authenticate, reservationRoutes);

// Rota de fallback para 404
router.use('*', (req, res) => {
  res.status(404).json({
    error: 'Not Found',
    message: 'Endpoint não encontrado'
  });
});

module.exports = router;