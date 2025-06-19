const { createProxyMiddleware } = require('http-proxy-middleware');
const express = require('express');
const router = express.Router();

const flightServiceUrl = process.env.MS_VOO_URL;

// Proxy para todos os métodos e subpaths de /flights
router.use(
  '/flights',
  createProxyMiddleware({
    target: flightServiceUrl,
    changeOrigin: true,
    pathRewrite: { '^/api': '' },
    timeout: 60000, // 60 segundos
    proxyTimeout: 60000, // 60 segundos
    onProxyReq: (proxyReq, req, res) => {
      console.log(`[PROXY] ${req.method} ${req.path} -> ${flightServiceUrl}${proxyReq.path}`);
      console.log(`[PROXY] Headers:`, req.headers);
      console.log(`[PROXY] Body:`, req.body);
      
      if (req.body) {
        const bodyData = JSON.stringify(req.body);
        proxyReq.setHeader('Content-Type', 'application/json');
        proxyReq.setHeader('Content-Length', Buffer.byteLength(bodyData));
        proxyReq.write(bodyData);
      }
    },
    onProxyRes: (proxyRes, req, res) => {
      console.log(`[PROXY] Response: ${proxyRes.statusCode} for ${req.method} ${req.path}`);
    },
    onError: (err, req, res) => {
      console.error('Proxy error for flights:', err.message);
      console.error('Error details:', err);
      if (!res.headersSent) {
        res.status(500).json({ 
          error: 'Gateway Error', 
          message: 'Flight service unavailable',
          details: err.message 
        });
      }
    }
  })
);

module.exports = router;
