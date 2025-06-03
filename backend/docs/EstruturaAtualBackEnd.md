# 🧱 Estrutura Atual do Backend - Airline Company

## 🔧 RAIZ - `backend/`

Diretório principal do backend, agrupando todos os serviços necessários para a aplicação funcionar, incluindo o gateway, microserviços e arquivos auxiliares.

### 📄 `build.sh`

Script (provavelmente Bash) utilizado para compilar ou preparar os serviços, possivelmente realizando:

- Build dos projetos Java com Maven (`./mvnw clean install`)
- Build das imagens Docker
- Limpeza de artefatos

### 📄 `docker-compose.yml`

Orquestração de todos os serviços (`api-gateway`, `ms-auth`, `ms-cliente`, etc.).  
Define:

- Serviços e suas imagens Docker
- Redes internas entre os serviços
- Volumes, portas, variáveis de ambiente
- Dependências entre serviços (ex: `ms-cliente` depende do `ms-auth`)

---

## 🌐 `api-gateway/`

Responsável por centralizar e gerenciar o tráfego HTTP para os microserviços. Implementado com Node.js e Express.

### 🔍 Função:

- Autenticar tokens JWT (via middleware)
- Redirecionar requisições para os microserviços adequados (auth, client, voo etc.)
- Tratar erros e padronizar respostas

### 📁 `routes/` - Arquivos de definição das rotas públicas:

- `auth.js` → encaminha login e registro para `ms-auth`
- `client.js` → envia dados de cliente para `ms-cliente`
- `employee.js`, `flight.js`, `reservation.js` → futuras integrações com outros microserviços

### 📁 `middleware/` - Middlewares do Express:

- `jwtAuth.js`: verifica e decodifica JWTs, protegendo rotas privadas
- `errorHandler.js`: captura e formata erros de forma padronizada

### 🔄 Comunicação:

- Redireciona via HTTP para os serviços (ex: `ms-auth`, `ms-cliente`) com Axios ou fetch
- Valida os JWTs gerados pelo `ms-auth`
- Interface pública do backend

---

## 🔐 `ms-auth/`

Microserviço de autenticação e segurança, desenvolvido em Java com Spring Boot.

### 🔍 Função:

- Gerar tokens JWT no login
- Registrar novos usuários
- Armazenar e validar usuários

### 📁 `controller/AuthController.java`

- Expõe endpoints de login e cadastro (`/login`, `/register`)
- Produz/consome DTOs

### 📁 `util/JwtUtil.java`

- Geração e validação de JWTs
- Chave secreta compartilhada (com API Gateway via `.env` ou `application.properties`)

### 📁 `repository/UserRepository.java`

- Interface para salvar e consultar usuários

### 📁 `config/SecurityConfig.java`

- Define políticas de segurança e autenticação

### 📁 `model/User.java`

- Representa o usuário na base de dados

### 🔄 Comunicação:

- Recebe requisições do API Gateway
- Retorna JWTs no login
- Armazena usuários no próprio banco
- Pode enviar eventos para outros serviços no cadastro (ex: `ms-cliente`)

---

## 👤 `ms-cliente/`

Microserviço responsável pelo gerenciamento de clientes e acúmulo de milhas.

### 🔍 Função:

- Cadastro e atualização de clientes
- Consulta de informações
- Integração com ViaCEP (busca de endereço por CEP)
- Envio de eventos RabbitMQ para controle de usuários

### 📁 `controller/ClientController.java`

- Expõe os endpoints REST relacionados a clientes

### 📁 `dto/`

- `ClientRequestDto.java`, `ClientResponseDto.java`: entrada e saída de dados de cliente
- `CriarUsuarioEvent.java`: estrutura de evento para RabbitMQ
- `AddressDto.java`: estrutura para dados de endereço

### 📁 `model/`

- `Client`, `Address`: dados principais do cliente
- `Transaction`, `Mile`: controle de milhas e movimentações
- Cada cliente pode ter transações de acúmulo e resgate

### 📁 `service/`

- `ClientService`: lógica de negócio do cliente
- `CadastroClienteSagaService`: provavelmente relacionado a uma saga transacional (coordenação com `ms-auth`, rollback em caso de erro)
- `ViaCepService`: faz chamada à API pública ViaCEP

### 📁 `config/RabbitMQConfig.java`

- Configura conexão e tópicos do RabbitMQ para comunicação assíncrona entre os serviços

### 🔄 Comunicação:

- Requisições HTTP vindas do API Gateway
- Reage a eventos RabbitMQ (ex: novo usuário criado no `ms-auth`)
- Pode emitir eventos para orquestração (milhas, registros)

---

## 🧑‍✈️ `ms-funcionario/` (ainda não detalhado)

Microserviço previsto para controle de funcionários da companhia:

- Cadastro, permissões, papéis (ex: agente, supervisor)
- Comunicação com `ms-auth` (para autenticação e associação)

---

## 🛫 `ms-voo/` (ainda não detalhado)

Microserviço responsável pela gestão de voos:

- Rotas, horários, status dos voos
- Comunicação com `ms-reserva` para verificar disponibilidade

---

## 📅 `ms-reserva/` (ainda não detalhado)

Microserviço para gerenciar reservas de passagens:

- Criação, cancelamento, alteração de reservas
- Verificação de disponibilidade junto ao `ms-voo`
- Associação com clientes (`ms-cliente`)

---

## 📁 `docs/`

Diretório para documentação técnica ou Swagger/OpenAPI.  
Pode conter:

- Diagramas de arquitetura
- JSON/YAML de Swagger
- Requisitos de endpoints

---

## 🔗 Comunicação Entre Componentes

| De → Para                | Tipo                  | Descrição                                              |
|-------------------------|-----------------------|--------------------------------------------------------|
| Frontend → API Gateway  | HTTP                  | Todas as requisições do front-end passam pelo gateway |
| API Gateway → ms-auth   | HTTP                  | Para login/cadastro/autenticação                      |
| API Gateway → ms-cliente| HTTP                  | Para ações sobre cliente                              |
| ms-auth → ms-cliente    | RabbitMQ (eventual)   | Envia evento "usuário criado"                         |
| ms-cliente → ViaCEP     | HTTP externo          | Busca de endereço por CEP                             |
| ms-cliente → RabbitMQ   | Async                 | Publica eventos de cadastro                           |
| API Gateway → ms-voo / ms-reserva / ms-funcionario | HTTP | Comunicação com demais serviços (a ser implementada) |

---

📌 *Essa documentação descreve a arquitetura atual do backend, o papel de cada componente, e como eles se integram para atender os requisitos da aplicação de gestão de passagens aéreas.*
