function errorHandler(err, req, res, next) {
    console.error('[Gateway Error]', {
      error: err.stack,
      path: req.path,
      method: req.method
    });
  
    const status = err.status || 500;
    const message = status === 500 ? 'Internal Server Error' : err.message;
  
    res.status(status).json({
      error: message,
      path: req.path,
      timestamp: new Date().toISOString()
    });
  }
  
  module.exports = { errorHandler };