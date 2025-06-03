# Endpoints da API — Visão Geral por Microsserviço

✅ **Implementado** | 🔧 **Em andamento** | ⏳ **Pendente** | ❌ **A avaliar/planejar**

---

## 🔐 1. Microsserviço de Autenticação (`ms-auth`)

| Método | Endpoint         | Descrição                                         | Status |
|--------|------------------|---------------------------------------------------|--------|
| POST   | /auth/login      | Autentica cliente/funcionário e retorna JWT       | ✅     |
| POST   | /auth/register   | Registra usuário com e-mail e tipo                | ✅     |

---

## 👤 2. Microsserviço de Cliente (`ms-cliente`)

| Método | Endpoint                                 | Descrição                                                        | Status |
|--------|------------------------------------------|------------------------------------------------------------------|--------|
| POST   | /clientes/autocadastro                   | Cadastro de cliente com CEP → ViaCEP → senha aleatória           | ✅ (exceto envio de e-mail) |
| GET    | /clientes                                | Lista todos os clientes                                          | ✅     |
| GET    | /clientes/:id                            | Retorna dados de um cliente (exceto senha)                       | ⏳     |
| GET    | /clientes/:id/milhas/saldo               | Retorna saldo de milhas (R03)                                    | ⏳     |
| GET    | /clientes/:id/milhas/extrato             | Extrato de milhas (R06)                                          | ⏳     |
| POST   | /clientes/:id/milhas/comprar             | Compra de milhas (R05)                                           | ⏳     |
| GET    | /clientes/:id/reservas                   | Lista reservas do cliente (R03)                                  | ⏳ (via agregação ou redirect para `ms-reserva`) |
| DELETE | /clientes/:id                            | *(Novo)* Inativa/Remove cliente                                  | ❌ Avaliar necessidade (RGPD) |

---

## 🛫 3. Microsserviço de Voos (`ms-voo`)

| Método | Endpoint                                | Descrição                                | Status |
|--------|-----------------------------------------|------------------------------------------|--------|
| GET    | /voos/aeroportos                        | Lista aeroportos para dropdown           | ⏳     |
| GET    | /voos                                   | Busca voos (R07) com filtros             | ⏳     |
| POST   | /voos                                   | Cadastro de voo (R15)                    | ⏳     |
| GET    | /voos/:codigoVoo                        | Detalhes de um voo                       | ⏳     |
| PUT    | /voos/:codigoVoo/cancelar               | Cancela voo (R13)                        | ⏳     |
| PUT    | /voos/:codigoVoo/realizar               | Marca voo como realizado (R14)           | ⏳     |
| DELETE | /voos/:codigoVoo                        | *(Novo)* Exclusão lógica de voo          | ❌ A avaliar conforme política de dados |

---

## 📅 4. Microsserviço de Reservas (`ms-reserva`)

### 🔁 Comando

| Método | Endpoint                                   | Descrição                                | Status |
|--------|--------------------------------------------|------------------------------------------|--------|
| POST   | /reservas                                  | Criação de reserva (R07)                 | ⏳     |
| PUT    | /reservas/:codigoReserva/checkin           | Check-in do cliente (R10)                | ⏳     |
| PUT    | /reservas/:codigoReserva/cancelar          | Cancela reserva (R08, R09)               | ⏳     |

### 🔍 Consulta (CQRS)

| Método | Endpoint                                         | Descrição                                       | Status |
|--------|--------------------------------------------------|-------------------------------------------------|--------|
| GET    | /reservas/consultas/:codigoReserva              | Detalhes da reserva (R04, R09)                 | ⏳     |
| GET    | /reservas/consultas/cliente/:id                 | Lista reservas de um cliente (R03)             | ⏳     |
| GET    | /reservas/consultas/voos-proximos               | Lista voos futuros para check-in (R10, R11)    | ⏳     |

---

## 👨‍✈️ 5. Microsserviço de Funcionários (`ms-funcionario`)

| Método | Endpoint                        | Descrição                                     | Status |
|--------|----------------------------------|-----------------------------------------------|--------|
| POST   | /funcionarios                   | Cadastro de funcionário (R17)                 | ⏳     |
| GET    | /funcionarios                   | Lista todos os funcionários (R16)            | ⏳     |
| PUT    | /funcionarios/:id              | Atualiza dados do funcionário (R18)          | ⏳     |
| DELETE | /funcionarios/:id              | Inativa funcionário (R19)                    | ⏳     |
| GET    | /funcionarios/:id              | *(Novo)* Retorna dados de um funcionário      | ❌ Sugerido para frontend detalhado |

---

## 🌐 6. API Gateway (`api-gateway`)

### 🔁 Agregação / Redirecionamento

| Método | Endpoint                          | Descrição                                       | Status         |
|--------|-----------------------------------|-------------------------------------------------|----------------|
| GET    | /clientes/:id/dashboard           | Agrega saldo + reservas (R03)                   | ⏳             |
| GET    | /funcionarios/voos-proximos       | Agrega voos futuros (R11)                       | ⏳             |
| ALL    | /auth/*, /clientes/* etc          | Proxy para os microsserviços (validação JWT)    | ✅ *em parte*  |
| GET    | /health                           | *(Novo)* Healthcheck dos microsserviços         | ❌ Recomendado |

---

## 📡 Comunicação Assíncrona

| Canal/Evento        | Origem       | Destino      | Descrição                                         | Status         |
|---------------------|--------------|--------------|---------------------------------------------------|----------------|
| `usuario.criado`    | `ms-auth`    | `ms-cliente` | Evento após cadastro de novo usuário              | ⏳             |
| `reserva.criada`    | `ms-reserva` | `ms-cliente` | Atualiza saldo de milhas após reserva             | ❌ A definir   |
| `milhas.compradas`  | `ms-cliente` | `ms-reserva` | *(Opcional)* Confirma crédito para reserva        | ❌ A avaliar   |

---

## ✅ Observações e Boas Práticas

- DTOs sempre nos endpoints (entrada e saída).
- Token JWT obrigatório em todas as rotas, **exceto** `/auth/login` e `/clientes/autocadastro`.
- **CQRS** ativo em `ms-reserva` — comandos atualizam bancos desnormalizados para leitura rápida.
- **SAGA** planejado para orquestração de transações (ex: cadastro cliente + usuário).

