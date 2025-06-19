#!/bin/bash

# Test script for SAGA Autocadastro Flow
# This script tests the complete SAGA flow from frontend to completion

echo "=== SAGA Autocadastro Flow Test ==="
echo ""

# Configuration
API_GATEWAY_URL="http://localhost:8080"
SAGA_SERVICE_URL="http://localhost:8084"  # Assuming ms-sagas runs on 8084

# Test data
TEST_EMAIL="teste.saga@email.com"
TEST_NAME="Teste SAGA"
TEST_CPF="12345678901"
TEST_CEP="80000-000"
TEST_RUA="Rua Teste"
TEST_NUMERO="123"
TEST_COMPLEMENTO="Apto 1"
TEST_CIDADE="Curitiba"
TEST_ESTADO="PR"

echo "1. Testing SAGA Service Health..."
curl -s "$SAGA_SERVICE_URL/saga/health" || echo "SAGA service not responding"

echo ""
echo "2. Initiating Autocadastro SAGA..."
echo "POST $API_GATEWAY_URL/clientes/autocadastro"

# Create test payload
PAYLOAD=$(cat <<EOF
{
  "cpf": "$TEST_CPF",
  "email": "$TEST_EMAIL",
  "name": "$TEST_NAME",
  "cep": "$TEST_CEP",
  "rua": "$TEST_RUA",
  "numero": "$TEST_NUMERO",
  "complemento": "$TEST_COMPLEMENTO",
  "cidade": "$TEST_CIDADE",
  "estado": "$TEST_ESTADO"
}
EOF
)

# Send request
RESPONSE=$(curl -s -X POST \
  -H "Content-Type: application/json" \
  -d "$PAYLOAD" \
  "$API_GATEWAY_URL/clientes/autocadastro")

echo "Response: $RESPONSE"

# Extract correlation ID from response
CORRELATION_ID=$(echo $RESPONSE | grep -o '"correlationId":"[^"]*"' | cut -d'"' -f4)

if [ -n "$CORRELATION_ID" ]; then
    echo "Correlation ID: $CORRELATION_ID"
    
    echo ""
    echo "3. Checking SAGA Status..."
    sleep 2  # Wait a bit for processing
    
    SAGA_STATUS=$(curl -s "$SAGA_SERVICE_URL/saga/status/$CORRELATION_ID")
    echo "SAGA Status: $SAGA_STATUS"
    
    echo ""
    echo "4. Monitoring SAGA completion..."
    for i in {1..10}; do
        echo "Check $i/10..."
        STATUS=$(curl -s "$SAGA_SERVICE_URL/saga/status/$CORRELATION_ID")
        if echo "$STATUS" | grep -q '"status":"COMPLETADA"'; then
            echo "✅ SAGA completed successfully!"
            break
        elif echo "$STATUS" | grep -q '"status":"FALHOU"'; then
            echo "❌ SAGA failed!"
            break
        fi
        sleep 2
    done
    
    echo ""
    echo "5. Final SAGA Status:"
    curl -s "$SAGA_SERVICE_URL/saga/status/$CORRELATION_ID" | jq '.' 2>/dev/null || curl -s "$SAGA_SERVICE_URL/saga/status/$CORRELATION_ID"
    
else
    echo "❌ Failed to get correlation ID from response"
fi

echo ""
echo "=== Test Complete ==="
echo ""
echo "Check the logs of all services to see the complete SAGA flow:"
echo "- ms-cliente logs"
echo "- ms-sagas logs" 
echo "- ms-auth logs"
echo ""
echo "Look for email output in ms-auth logs (mock email service)" 