# Companhia aérea - Arquitetura de Microsserviços

## Visão Geral 

Companhia aerea é um sistema moderno de gestão de companhias aéreas desenvolvido com uma arquitetura de **microsserviços**. O projeto foi desenvolvido para gerenciar as operações de companhias aéreas com eficiência, garantindo escalabilidade, manutenibilidade e confiabilidade. Ele integra diversas tecnologias para fornecer uma infraestrutura robusta para gerenciar voos, reservas, interações com clientes e pagamentos.

## Tecnologias usadas

- Frontend: Vue.js
- Backend: Spring Boot (Java)
- API Gateway: Node.js
- Orquestração e transações: SAGA Pattern
- Conteinerização: Docker
- Comunicação de serviços: REST APIs
- Banco de Dados: MySQL, MongoDB
- mensageria: RabbitMQ
- Autenticação e segurança: JWT

## Arquitetura de microsserviços

  **O sistema contem os seguintes microsserviços:**

- Serviço de Usuário: Gerencia a autenticação de usuários e perfis.
- Serviço de Voos: Lida com os horários dos voos, disponibilidade e atualizações de status.
- Serviço de Reservas: Gerencia as reservas de passagens e pagamentos.
- Serviço de Pagamentos: Processa transações de forma segura.
- Serviço de Notificações: Envia alertas e notificações para os clientes.
- Gateway de API: Ponto de entrada centralizado para roteamento de requisições.

## Principais funcionalidades

- Sistema distribuído com microsserviços independentes
- Comunicação orientada a eventos utilizando brokers de mensagens
- Padrão SAGA para gerenciamento de transações distribuídas
- Implantação escalável e conteinerizada usando Docker
- Gerenciamento seguro de APIs com autenticação JWT
- Monitoramento e registro em tempo real

## Instalação e configurações

## Pré-requisitos 

Antes de rodar o projeto, tenha certeza de que tenha instalado:

- Docker
- Docker Compose
- Node.js (para rodar o API Gateway)
- Java 17+ (para serviços Spring Boot )
- Maven (para construir serviços em Java)
- Vue CLI (para desenvolvimento frontend)

## Passos para rodar

1. Clone o repositório

   ```sh
   git clone https://github.com/your-username/airline-company.git
   ```
2. No terminal, certifique-se de que você está no diretório raiz do projeto. Caso não esteja, navegue até ele usando o comando cd. Por exemplo:
    ```sh
   cd airline-company
   ```
3. Build Microsseviços Java 
     ```sh
   cd backend/services
   mvn clean install
   ```

4. Comece todos os serviços com  Docker Compose

   ```sh
   docker-compose up --build
   ```
5. Rode o API Gateway
   ```sh
   cd api-gateway
   npm install
   npm run start
   ```
   
6. Rode o Frontend
   ```sh
    cd frontend
   npm install
   npm run serve
   ```
  
### Acesso a Aplicação

- Frontend:  http://localhost:5173/
- API Gateway: http://localhost:3000
- RabbitMQ Dashboard: http://localhost:15672 (default: guest/guest)

  


