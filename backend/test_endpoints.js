const fetch = require('node-fetch');

async function testEndpoints() {
  // ms-voo endpoints
  const vooBase = 'http://localhost:8083';
  const reservaBase = 'http://localhost:8084/api';

  // 1. Criar voo
  let flightId = null;
  const flightPayload = {
    code: 'JJ1234',
    originCodigo: 'GRU',
    destinationCodigo: 'GIG',
    departureTime: '2025-07-01T10:00:00',
    price: 1000.00,
    seatCount: 180
  };
  console.log('POST /flights');
  let res = await fetch(`${vooBase}/flights`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(flightPayload)
  });
  let data = await res.json().catch(() => ({}));
  console.log(res.status, data);
  flightId = data.id || 1;

  // 2. Listar voos
  console.log('GET /flights');
  res = await fetch(`${vooBase}/flights`);
  data = await res.json().catch(() => ({}));
  console.log(res.status, data);

  // 3. Buscar voo por ID
  console.log('GET /flights/' + flightId);
  res = await fetch(`${vooBase}/flights/${flightId}`);
  data = await res.json().catch(() => ({}));
  console.log(res.status, data);

  // 4. Atualizar voo
  console.log('PUT /flights/' + flightId);
  res = await fetch(`${vooBase}/flights/${flightId}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ ...flightPayload, code: 'JJ5678' })
  });
  data = await res.json().catch(() => ({}));
  console.log(res.status, data);

  // 5. Deletar voo
  console.log('DELETE /flights/' + flightId);
  res = await fetch(`${vooBase}/flights/${flightId}`, { method: 'DELETE' });
  console.log(res.status);

  // ms-reserva endpoints
  // 1. Criar reserva
  const reservaPayload = {
    clientId: 1,
    flightCode: 'JJ1234',
    reservationDate: '2025-07-01T09:00:00'
  };
  console.log('POST /api/reservations');
  res = await fetch(`${reservaBase}/reservations`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(reservaPayload)
  });
  data = await res.json().catch(() => ({}));
  console.log(res.status, data);
  const reservaId = data.id || 1;

  // 2. Listar reservas por cliente
  console.log('GET /api/reservations/client/1');
  res = await fetch(`${reservaBase}/reservations/client/1`);
  data = await res.json().catch(() => ({}));
  console.log(res.status, data);

  // 3. Buscar reserva por ID
  console.log('GET /api/reservations/' + reservaId);
  res = await fetch(`${reservaBase}/reservations/${reservaId}`);
  data = await res.json().catch(() => ({}));
  console.log(res.status, data);

  // 4. Cancelar reserva
  console.log('PUT /api/reservations/' + reservaId + '/cancel');
  res = await fetch(`${reservaBase}/reservations/${reservaId}/cancel`, { method: 'PUT' });
  data = await res.json().catch(() => ({}));
  console.log(res.status, data);

  // 5. Check-in reserva
  console.log('PUT /api/reservations/' + reservaId + '/check-in');
  res = await fetch(`${reservaBase}/reservations/${reservaId}/check-in`, { method: 'PUT' });
  data = await res.json().catch(() => ({}));
  console.log(res.status, data);

  // 6. Receber evento de status de voo
  console.log('POST /api/reservations/flight-status-event');
  res = await fetch(`${reservaBase}/reservations/flight-status-event`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ flightId: 1, status: 'ATRASADO' })
  });
  console.log(res.status);

  // 7. Alterar status da reserva
  console.log('POST /api/reservations/' + reservaId + '/status');
  res = await fetch(`${reservaBase}/reservations/${reservaId}/status`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ newStatus: 'CONFIRMADA' })
  });
  data = await res.json().catch(() => ({}));
  console.log(res.status, data);

  // 8. Histórico de status da reserva
  console.log('GET /api/reservations/' + reservaId + '/history');
  res = await fetch(`${reservaBase}/reservations/${reservaId}/history`);
  data = await res.json().catch(() => ({}));
  console.log(res.status, data);
}

testEndpoints().catch(console.error); 