const express = require('express');
const router = express.Router();
const axios = require('axios');
const { authenticate } = require('../middleware/jwtAuth');

// Timeout de 5 segundos para chamadas aos microserviços
const AXIOS_CONFIG = { timeout: 5000 };

router.post('/register', async (req, res) => {
  try {
    const response = await axios.post(
      `${process.env.MS_AUTENTICACAO_URL}/auth/register`,
      req.body,
      AXIOS_CONFIG
    );
    res.status(response.status).json(response.data);
  } catch (error) {
    handleServiceError(error, res, 'Authentication Service');
  }
});

router.post('/login', async (req, res) => {
  try {
    const response = await axios.post(
      `${process.env.MS_AUTENTICACAO_URL}/auth/login`,
      req.body,
      AXIOS_CONFIG
    );
    res.status(response.status).json(response.data);
  } catch (error) {
    handleServiceError(error, res, 'Authentication Service');
  }
});

function handleServiceError(error, res, serviceName) {
  if (error.code === 'ECONNABORTED') {
    return res.status(504).json({
      error: 'Gateway Timeout',
      message: `${serviceName} não respondeu a tempo`
    });
  }

  const status = error.response?.status || 500;
  const data = error.response?.data || { error: error.message };
  
  res.status(status).json({
    ...data,
    service: serviceName
  });
}

module.exports = router;