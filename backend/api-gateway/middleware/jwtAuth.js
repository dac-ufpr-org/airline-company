const jwt = require('jsonwebtoken');

const authenticate = (req, res, next) => {
  const authHeader = req.headers.authorization;
  
  if (!authHeader?.startsWith('Bearer ')) {
    return next(); // Permite que rotas públicas continuem
  }

  const token = authHeader.split(' ')[1];
  
  try {
    // Decodificação BASE64 do secret para compatibilidade com Spring
    const secret = Buffer.from(process.env.JWT_SECRET || '123456', 'utf8');
    
    const decoded = jwt.verify(token, secret, {
      algorithms: ['HS256'],
      ignoreExpiration: false,
    });

    req.user = {
      login: decoded.sub,
      tipo: decoded.role
    };
    
    next();
  } catch (error) {
    console.error('JWT Error:', {
      error: error.message,
      token: token,
      envSecret: process.env.JWT_SECRET
    });
    res.status(403).json({ 
      error: 'Forbidden',
      details: 'Invalid token',
      debug: process.env.NODE_ENV === 'development' ? error.message : undefined
    });
  }
};

module.exports = { authenticate };