require("dotenv").config();
const express = require("express");
const cors = require("cors");
const app = express();

// Middlewares
app.use(cors({
    origin: 'http://localhost:4200',
    credentials: true,
}));
app.use(express.json()); // Importante para POST/PUT com body JSON

// Rotas
const clientRoutes = require('./routes/client');
const employeeRoutes = require('./routes/employee');
const flightRoutes = require('./routes/flight');
const reservationRoutes = require('./routes/reservation');
const authRoutes = require('./routes/auth');

app.use('/api/client', clientRoutes);
app.use('/api/employee', employeeRoutes);
app.use('/api/flight', flightRoutes);
app.use('/api/reservation', reservationRoutes);
app.use('/api/auth', authRoutes);

// Start
const PORT = process.env.PORT || 8000;
app.listen(PORT, () => console.log(`API Gateway rodando na porta ${PORT}`));
