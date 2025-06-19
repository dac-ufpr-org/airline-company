const http = require('http');

const data = JSON.stringify('CANCELADO');

const options = {
  hostname: 'localhost',
  port: 8000,
  path: '/api/flights/20/status',
  method: 'PUT',
  headers: {
    'Content-Type': 'application/json',
    'Content-Length': data.length
  }
};

const req = http.request(options, (res) => {
  console.log(`Status: ${res.statusCode}`);
  console.log(`Headers:`, res.headers);

  let responseData = '';
  res.on('data', (chunk) => {
    responseData += chunk;
  });

  res.on('end', () => {
    console.log('Response:', responseData);
  });
});

req.on('error', (err) => {
  console.error('Error:', err.message);
});

req.write(data);
req.end(); 