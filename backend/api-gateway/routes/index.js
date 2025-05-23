const express = require('express');
const router = express.Router();
const { authenticate } = require('../middleware/jwtAuth');

// Importação correta dos routers
const authRoutes = require('./auth');
const clientRoutes = require('./client');

// Rotas públicas
router.use('/auth', authRoutes);
router.use('/clientes/autocadastro', clientRoutes.publicRoutes);

// Middleware de autenticação global
router.use(authenticate);

// Rotas autenticadas
router.use('/clientes', clientRoutes.authenticatedRoutes);

// Rota de fallback para 404
router.use('*', (req, res) => {
  res.status(404).json({
    error: 'Not Found',
    message: 'Endpoint não encontrado'
  });
});

module.exports = router;