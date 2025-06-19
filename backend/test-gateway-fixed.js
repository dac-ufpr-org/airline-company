const fetch = require('node-fetch');

const gateway = 'http://localhost:8000/api';

async function testGateway() {
    console.log('=== TESTE API GATEWAY - ENDPOINTS CORRIGIDOS ===\n');

    try {
        // Teste 1: Criar reserva via API Gateway
        console.log('1. Testando POST /api/reservations');
        let res = await fetch(`${gateway}/reservations`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                clientId: 1,
                flightCode: 'AA123'
            })
        });

        console.log(`Status: ${res.status}`);
        if (res.ok) {
            const data = await res.json();
            console.log('Resposta:', data);
            
            const reservaId = data.id;
            
            // Teste 2: Consultar reserva criada
            console.log('\n2. Testando GET /api/reservations/' + reservaId);
            res = await fetch(`${gateway}/reservations/${reservaId}`);
            console.log(`Status: ${res.status}`);
            if (res.ok) {
                try {
                    const data = await res.json();
                    console.log('Resposta:', data);
                } catch (jsonError) {
                    const textResponse = await res.text();
                    console.log('Resposta (texto):', textResponse);
                    console.log('Erro JSON:', jsonError.message);
                }
            } else {
                const error = await res.text();
                console.log('Erro:', error);
            }
            
            // Teste 3: Alterar status da reserva
            console.log('\n3. Testando PUT /api/reservations/' + reservaId + '/status');
            res = await fetch(`${gateway}/reservations/${reservaId}/status`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    newStatusCode: 'CHECK_IN',
                    reason: 'Teste via gateway'
                })
            });
            console.log(`Status: ${res.status}`);
            if (res.ok) {
                const data = await res.json();
                console.log('Resposta:', data);
            } else {
                const error = await res.text();
                console.log('Erro:', error);
            }
            
        } else {
            const error = await res.text();
            console.log('Erro:', error);
        }

        // Teste 4: Listar reservas por cliente
        console.log('\n4. Testando GET /api/reservations/client/1');
        res = await fetch(`${gateway}/reservations/client/1`);
        console.log(`Status: ${res.status}`);
        if (res.ok) {
            const data = await res.json();
            console.log('Resposta:', data);
        } else {
            const error = await res.text();
            console.log('Erro:', error);
        }

    } catch (error) {
        console.error('Erro no teste:', error.message);
    }
}

testGateway(); 