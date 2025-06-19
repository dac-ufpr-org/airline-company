#!/bin/bash
set -e
set -o pipefail

GATEWAY="http://localhost:8000/api"
LOGFILE="test_gateway_endpoints.log"

# Detecta se jq está instalado
if command -v jq >/dev/null 2>&1; then
  JQ="jq"
else
  echo "[AVISO] jq não encontrado. A saída dos endpoints será mostrada sem formatação. Para instalar: sudo apt install jq"
  JQ="cat"
fi

function log() {
  echo -e "\n===== $1 =====" | tee -a "$LOGFILE"
}

function curl_log() {
  log "$1"
  shift
  echo "> curl $@" | tee -a "$LOGFILE"
  eval "$@" 2>&1 | tee -a "$LOGFILE"
}

rm -f "$LOGFILE"

print_section() {
  echo -e "\n==== $1 ===="
}

# 1. Criar voo
print_section "POST /api/flights"
flight_resp=$(curl -s -w "\n%{http_code}" -X POST "$GATEWAY/flights" \
  -H "Content-Type: application/json" \
  -d '{
    "code": "JJ9999",
    "originCodigo": "GRU",
    "destinationCodigo": "GIG",
    "departureTime": "2025-08-01T10:00:00",
    "price": 1500.00,
    "seatCount": 200
  }')
flight_body=$(echo "$flight_resp" | head -n-1)
flight_status=$(echo "$flight_resp" | tail -n1)
echo "Status: $flight_status"
echo "$flight_body" | $JQ

# Extrair ID do voo
flight_id=$(echo "$flight_body" | grep -o '"id":[0-9]*' | head -1 | grep -o '[0-9]*')

# 2. Listar voos
print_section "GET /api/flights"
flights_json=$(curl -s "$GATEWAY/flights")
echo "$flights_json" | $JQ

# 3. Atualizar status do voo
if [ -n "$flight_id" ]; then
  print_section "PUT /api/flights/$flight_id/status"
  status_resp=$(curl -s -w "\n%{http_code}" -X PUT "$GATEWAY/flights/$flight_id/status" \
    -H "Content-Type: application/json" \
    -d '"CANCELADO"')
  status_body=$(echo "$status_resp" | head -n-1)
  status_code=$(echo "$status_resp" | tail -n1)
  echo "Status: $status_code"
  echo "$status_body" | $JQ
else
  echo "Voo não criado, pulando update de status."
fi

# 4. Criar reserva
print_section "POST /api/reservations"
reserva_resp=$(curl -s -w "\n%{http_code}" -X POST "$GATEWAY/reservations" \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": 1,
    "flightCode": "JJ9999"
  }')
reserva_body=$(echo "$reserva_resp" | head -n-1)
reserva_status=$(echo "$reserva_resp" | tail -n1)
echo "Status: $reserva_status"
echo "$reserva_body" | $JQ

# Extrair ID da reserva
reserva_id=$(echo "$reserva_body" | grep -o '"id":[0-9]*' | head -1 | grep -o '[0-9]*')

# 5. Buscar reserva por ID
if [ -n "$reserva_id" ]; then
  print_section "GET /api/reservations/$reserva_id"
  reserva_json=$(curl -s "$GATEWAY/reservations/$reserva_id")
  echo "$reserva_json" | $JQ
else
  echo "Reserva não criada, pulando busca por ID."
fi

# 6. Alterar status da reserva
if [ -n "$reserva_id" ]; then
  print_section "POST /api/reservations/$reserva_id/status"
  checkin_resp=$(curl -s -w "\n%{http_code}" -X POST "$GATEWAY/reservations/$reserva_id/status" \
    -H "Content-Type: application/json" \
    -d '{"newStatusCode": "CHECK_IN"}')
  checkin_body=$(echo "$checkin_resp" | head -n-1)
  checkin_status=$(echo "$checkin_resp" | tail -n1)
  echo "Status: $checkin_status"
  echo "$checkin_body" | $JQ
else
  echo "Reserva não criada, pulando alteração de status."
fi

log "Testes finalizados. Veja $LOGFILE para detalhes." 