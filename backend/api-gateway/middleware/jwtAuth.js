const jwt = require('jsonwebtoken');

const authenticate = (req, res, next) => {
  const authHeader = req.headers.authorization;
  
  if (!authHeader?.startsWith('Bearer ')) {
    return res.status(401).json({ 
      error: 'Unauthorized',
      details: 'No token provided or malformed'
    });
  }

  const token = authHeader.split(' ')[1];
  
  try {
    // Verificação compatível com Spring Security JWT
    const decoded = jwt.verify(token, process.env.JWT_SECRET || '123456', {
      algorithms: ['HS256'],
      ignoreExpiration: false
    });
    
    // Ajuste para a estrutura do token gerado pelo ms-auth
    req.user = {
      login: decoded.sub, // Spring usa 'sub' para username
      tipo: decoded.role  // Claim personalizado
    };
    
    next();
  } catch (error) {
    console.error('JWT Verification Error:', error);
    
    const response = {
      error: 'Forbidden',
      details: 'Invalid token'
    };

    if (error.name === 'TokenExpiredError') {
      response.details = 'Token expired';
    } else if (error.message.includes('invalid signature')) {
      response.debug = 'Signature verification failed (check JWT_SECRET)';
    }

    res.status(403).json(response);
  }
};

module.exports = { authenticate };