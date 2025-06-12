require('dotenv').config();

// Adicione no início:
console.log('Configurações JWT:', {
  secret: process.env.JWT_SECRET,
  env: process.env.NODE_ENV
});

const express = require('express');
// const cors = require('cors'); // Já está aqui, mas vamos usar uma configuração mais detalhada
const bodyParser = require('body-parser');

const app = express();

// --- CONFIGURAÇÃO DO CORS: AGORA MAIS ESPECÍFICA E SEGURA ---
// IMPORT
// Em produção, liste os domínios reais do seu frontend.
const corsOptions = {
  origin: ' http://localhost:5173', 
  methods: ['GET', 'POST', 'PUT', 'PATCH', 'DELETE', 'OPTIONS'], // Métodos HTTP permitidos
  credentials: true, // Permite que o navegador inclua cookies e cabeçalhos de autenticação
  allowedHeaders: ['Content-Type', 'Authorization', 'Accept'], // Cabeçalhos permitidos nas requisições
  optionsSuccessStatus: 204 // Para requisições OPTIONS de pré-voo
};

app.use(require('cors')(corsOptions)); // Use o middleware CORS com as opções configuradas


// Middlewares básicos
app.use(bodyParser.json());

// Health Check simplificado
app.get('/health', (req, res) => {
  res.status(200).json({
    status: 'API Gateway operacional',
    timestamp: new Date().toISOString()
  });
});

// Rotas
// Presumo que 'routes' aqui se refere ao 'index.js' dentro da pasta 'routes'
// e que ele lida com a importação de auth, client, etc.
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