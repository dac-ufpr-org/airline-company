require('dotenv').config();

// Adicione no início:
console.log('Configurações JWT:', {
  secret: process.env.JWT_SECRET,
  env: process.env.NODE_ENV
});

const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');

const app = express();

// Middlewares básicos
app.use(cors());
app.use(bodyParser.json());

// Health Check simplificado
app.get('/health', (req, res) => {
  res.status(200).json({ 
    status: 'API Gateway operacional',
    timestamp: new Date().toISOString()
  });
});

// Rotas
app.use('/api', require('./routes'));

// Error Handling básico
app.use((err, req, res, next) => {
  console.error('Erro não tratado:', err);
  res.status(500).json({ error: 'Erro interno no servidor' });
});

const PORT = process.env.PORT || 8000;
const server = app.listen(PORT, () => {
  console.log(`Servidor rodando na porta ${PORT}`);
  console.log('Ambiente:', process.env.NODE_ENV);
});

// Captura erros críticos
process.on('uncaughtException', (err) => {
  console.error('Erro não capturado:', err);
  server.close(() => process.exit(1));
});