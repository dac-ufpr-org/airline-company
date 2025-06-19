# Sistema de Companhia Aérea - Backend

## 📋 Visão Geral

Este é o backend de um sistema de gerenciamento de companhia aérea construído com arquitetura de microsserviços. O sistema utiliza Spring Boot, Docker, RabbitMQ, PostgreSQL e MongoDB para fornecer funcionalidades de autenticação, gerenciamento de clientes e orquestração de processos complexos.

## 🏗️ Arquitetura do Sistema

### Microsserviços Principais

#### 🔐 **ms-auth** (Serviço de Autenticação)
**O que faz:** Gerencia usuários e autenticação do sistema.

**Banco de dados:** MongoDB

**Funcionalidades principais:**
- Criação de usuários com senha temporária
- Autenticação via login/senha
- Geração de tokens JWT
- Envio de emails com senhas temporárias
- Compensação de usuários (para SAGA)

**Endpoints implementados:**
- `POST /auth/login` - Autenticação de usuário
- `POST /auth/register` - Registro de usuário (com senha temporária)

**Como se comunica:**
- Recebe eventos via RabbitMQ para criação de usuários (SAGA)
- Envia eventos de confirmação para o orchestrator
- Processa eventos de compensação

---

#### 👥 **ms-cliente** (Serviço de Clientes)
**O que faz:** Gerencia dados dos clientes da companhia aérea.

**Banco de dados:** PostgreSQL

**Funcionalidades principais:**
- Cadastro de clientes com endereço completo
- Consulta de clientes por CPF
- Listagem de todos os clientes
- Atualização de dados de clientes
- Integração com SAGA para autocadastro

**Endpoints implementados:**
- `POST /clientes` - Cadastro normal de cliente
- `POST /clientes/autocadastro` - Autocadastro via SAGA
- `GET /clientes/{cpf}` - Busca cliente por CPF
- `GET /clientes` - Lista todos os clientes
- `PUT /clientes/{cpf}` - Atualiza dados do cliente

**Como se comunica:**
- Recebe eventos via RabbitMQ para criação de clientes (SAGA)
- Envia eventos de confirmação para o orchestrator
- Inicia processos SAGA de autocadastro

---

#### 🎭 **ms-sagas** (Orquestrador SAGA)
**O que faz:** Coordena processos complexos que envolvem múltiplos microsserviços.

**Banco de dados:** Memória (estado das SAGAs)

**Funcionalidades principais:**
- Orquestração de SAGAs de autocadastro
- Gerenciamento de estado de transações distribuídas
- Compensação automática em caso de falhas
- Rastreamento de processos via correlationId

**Endpoints implementados:**
- `GET /sagas/{correlationId}` - Consulta estado da SAGA

**Como se comunica:**
- Recebe eventos de todos os microsserviços
- Envia comandos para execução de passos
- Gerencia compensação automática

---

#### 📦 **ms-contracts** (Contratos Compartilhados)
**O que faz:** Contém DTOs, eventos e enums compartilhados entre microsserviços.

**Banco de dados:** Nenhum (apenas código compartilhado)

**Funcionalidades principais:**
- DTOs para requisições e respostas
- Eventos para comunicação via RabbitMQ
- Enums para status de SAGAs
- Definições de filas RabbitMQ

**Como se comunica:**
- Não se comunica diretamente
- Fornece classes compartilhadas para outros microsserviços

---

#### 🌐 **api-gateway** (Gateway de API)
**O que faz:** Ponto de entrada único para todas as requisições externas.

**Tecnologia:** Node.js + Express

**Funcionalidades principais:**
- Roteamento de requisições para microsserviços
- Autenticação JWT
- Tratamento de erros centralizado
- Timeout e retry de requisições

**Endpoints implementados:**
- `POST /auth/login` - Encaminha para ms-auth
- `POST /auth/register` - Encaminha para ms-auth
- `POST /autocadastro` - Encaminha para ms-cliente (SAGA)
- `GET /clientes/*` - Encaminha para ms-cliente (com autenticação)

**Como se comunica:**
- Recebe requisições HTTP do frontend
- Encaminha para microsserviços apropriados
- Retorna respostas unificadas

---

### 🗄️ Serviços de Infraestrutura

#### 🐰 **RabbitMQ** (Sistema de Mensageria)
**O que faz:** Gerencia comunicação assíncrona entre microsserviços.

**Funcionalidades:**
- Filas para eventos de SAGA
- Roteamento de mensagens
- Garantia de entrega de mensagens
- Suporte a padrões pub/sub

**Filas utilizadas:**
- `orchestrator.queue` - Fila principal do orchestrator
- `auth.queue` - Fila do serviço de autenticação
- `cliente.queue` - Fila do serviço de clientes
- `compensacao.queue` - Fila para eventos de compensação

---

#### 🐘 **PostgreSQL** (Banco de Dados Principal)
**O que faz:** Armazena dados dos clientes e endereços.

**Tabelas principais:**
- `client` - Dados dos clientes
- `address` - Endereços dos clientes
- `mile` - Histórico de milhas
- `transaction` - Transações de milhas

---

#### 🍃 **MongoDB** (Banco de Dados de Autenticação)
**O que faz:** Armazena dados de usuários e autenticação.

**Coleções principais:**
- `users` - Usuários do sistema

---

## 🔄 Fluxo de SAGA de Autocadastro

### Visão Geral
O autocadastro é um processo complexo que envolve criação de usuário + cliente + envio de email. Para garantir consistência, usamos o padrão SAGA (Saga Orchestration).

### Passo a Passo

#### 1. **Início do Processo**
```
Frontend → API Gateway → ms-cliente → ms-sagas
```
- Usuário preenche formulário de autocadastro
- API Gateway recebe requisição
- ms-cliente inicia SAGA enviando evento para ms-sagas
- ms-sagas gera correlationId único

#### 2. **Criação de Usuário**
```
ms-sagas → ms-auth → ms-sagas
```
- ms-sagas envia `CriarUsuarioEvent` para ms-auth
- ms-auth cria usuário no MongoDB
- ms-auth gera senha temporária de 4 dígitos
- ms-auth envia email com senha
- ms-auth envia `UsuarioCriadoEvent` para ms-sagas

#### 3. **Criação de Cliente**
```
ms-sagas → ms-cliente → ms-sagas
```
- ms-sagas envia `ClienteCriadoEvent` para ms-cliente
- ms-cliente cria cliente no PostgreSQL
- ms-cliente envia confirmação para ms-sagas
- ms-sagas marca SAGA como completa

#### 4. **Compensação (se algo falhar)**
```
ms-sagas → ms-auth (compensação)
```
- Se qualquer passo falhar, ms-sagas inicia compensação
- ms-auth remove usuário criado (se existir)
- SAGA é marcada como falhou

### Estados da SAGA
- **INICIADA** - SAGA iniciada, aguardando criação de usuário
- **USUARIO_CRIADO** - Usuário criado, aguardando criação de cliente
- **CLIENTE_CRIADO** - Cliente criado, SAGA quase completa
- **COMPLETADA** - SAGA finalizada com sucesso
- **COMPENSANDO** - Processo de compensação em andamento
- **FALHOU** - SAGA falhou e compensação foi executada

---

## 🚀 Como Executar o Sistema

### Pré-requisitos
- Docker e Docker Compose instalados
- Maven instalado (para desenvolvimento)

### Execução Completa
```bash
cd backend
./build.sh
```

Este comando:
1. Para containers existentes
2. Remove imagens antigas
3. Compila todos os módulos Maven
4. Constrói imagens Docker
5. Inicia todos os serviços

### Serviços Disponíveis
- **API Gateway:** http://localhost:8000
- **ms-auth:** http://localhost:8081
- **ms-cliente:** http://localhost:8082
- **RabbitMQ Management:** http://localhost:15672
- **PostgreSQL:** localhost:5432
- **MongoDB:** localhost:27017

---

## 📊 Monitoramento e Logs

### Logs com Emojis
O sistema utiliza emojis nos logs para facilitar o acompanhamento:

- 🚀 - Início de processo
- 📥 - Recebimento de evento
- 📤 - Envio de evento
- ✅ - Sucesso
- ❌ - Erro
- 🔄 - Compensação
- 🔐 - Autenticação
- 💾 - Operação de banco
- 📧 - Envio de email
- 🗑️ - Remoção/exclusão

### Acompanhamento de SAGA
```bash
# Ver logs do orchestrator
docker logs airline-ms-sagas

# Ver logs de autenticação
docker logs airline-ms-auth

# Ver logs de clientes
docker logs airline-ms-cliente
```

---

## 🔧 Desenvolvimento

### Estrutura de Módulos
```
backend/
├── ms-contracts/     # DTOs e eventos compartilhados
├── ms-sagas/         # Orquestrador SAGA
├── ms-auth/          # Autenticação
├── ms-cliente/       # Gerenciamento de clientes
├── api-gateway/      # Gateway de API
├── docker-compose.yml
└── build.sh
```

### Tecnologias Utilizadas
- **Spring Boot 3.x** - Framework Java
- **Maven** - Gerenciamento de dependências
- **Docker** - Containerização
- **RabbitMQ** - Mensageria
- **PostgreSQL** - Banco relacional
- **MongoDB** - Banco NoSQL
- **Node.js** - API Gateway
- **JWT** - Autenticação

---

## 🎯 Próximos Passos

### Funcionalidades Planejadas
- Sistema de milhas
- Reservas de voos
- Gestão de funcionários
- Relatórios e analytics
- Notificações push
- Integração com sistemas externos

### Melhorias Técnicas
- Persistência de estado SAGA em Redis
- Métricas e monitoramento
- Testes automatizados
- CI/CD pipeline
- Documentação OpenAPI
- Rate limiting
- Circuit breaker 