const fetch = require('node-fetch');

async function testGatewayEndpoints() {
  const gateway = 'http://localhost:8000/api';
  let flightId = null;
  let reservaId = null;

  // 1. Criar voo
  const flightPayload = {
    code: 'JJ9999',
    originCodigo: 'GRU',
    destinationCodigo: 'GIG',
    departureTime: '2025-08-01T10:00:00',
    price: 1500.00,
    seatCount: 200
  };
  console.log('POST /api/flights');
  let res = await fetch(`${gateway}/flights`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(flightPayload)
  });
  let data = await res.json().catch(() => ({}));
  console.log(res.status, data);
  // Buscar o ID do voo criado
  res = await fetch(`${gateway}/flights`);
  let flights = await res.json().catch(() => []);
  flightId = flights.find(f => f.code === 'JJ9999')?.id || flights[0]?.id;

  // 2. Listar voos
  console.log('GET /api/flights');
  res = await fetch(`${gateway}/flights`);
  data = await res.json().catch(() => ({}));
  console.log(res.status, data);

  // 3. Atualizar status do voo
  if (flightId) {
    console.log('PUT /api/flights/' + flightId + '/status');
    res = await fetch(`${gateway}/flights/${flightId}/status`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify("CANCELADO")
    });
    data = await res.json().catch(() => ({}));
    console.log(res.status, data);
  }

  // 4. Criar reserva
  const reservaPayload = {
    clientId: 1,
    flightCode: 'JJ9999'
  };
  console.log('POST /api/reservations');
  res = await fetch(`${gateway}/reservations`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(reservaPayload)
  });
  data = await res.json().catch(() => ({}));
  console.log(res.status, data);
  reservaId = data.id || 1;

  // 5. Buscar reserva por ID
  if (reservaId) {
    console.log('GET /api/reservations/' + reservaId);
    res = await fetch(`${gateway}/reservations/${reservaId}`);
    data = await res.json().catch(() => ({}));
    console.log(res.status, data);
  }

  // 6. Alterar status da reserva
  if (reservaId) {
    console.log('POST /api/reservations/' + reservaId + '/status');
    res = await fetch(`${gateway}/reservations/${reservaId}/status`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ newStatusCode: 'CHECK_IN' })
    });
    data = await res.json().catch(() => ({}));
    console.log(res.status, data);
  }
}

testGatewayEndpoints().catch(console.error); 