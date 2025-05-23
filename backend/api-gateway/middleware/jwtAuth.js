const jwt = require('express-jwt');
const { expressjwt: jwt } = require("express-jwt");

const jwtMiddleware = jwt({
  secret: process.env.JWT_SECRET || '123456', // Use a mesma chave do ms-auth
  algorithms: ['HS256'],
  requestProperty: 'auth' // Adiciona o payload decodificado em req.auth
}).unless({
  path: [
    '/auth/login',
    '/auth/register',
    '/clientes/autocadastro' // Libera apenas endpoints públicos
  ]
});

// Middleware de erro personalizado
const authErrorHandler = (err, req, res, next) => {
  if (err.name === 'UnauthorizedError') {
    return res.status(401).json({ error: 'Token inválido ou expirado' });
  }
  next();
};

module.exports = { jwtMiddleware, authErrorHandler };