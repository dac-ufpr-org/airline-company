const http = require('http');

const data = JSON.stringify({
  clientId: 2,
  flightCode: "JJ9999"
});

const options = {
  hostname: 'localhost',
  port: 8000,
  path: '/api/reservations',
  method: 'POST',
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