# Airline Company - Microservices Architecture

## Overview

Airline Company is a modern airline management system built using a **microservices** architecture. The project is designed to handle airline operations efficiently, ensuring scalability, maintainability, and reliability. It integrates various technologies to provide a robust infrastructure for managing flights, bookings, customer interactions, and payments.

## Technologies Used

- Frontend: Vue.js
- Backend: Spring Boot (Java)
- API Gateway: Node.js
- Orchestration & Transactions: SAGA Pattern
- Containerization: Docker
- Service Communication: REST APIs
- Database: MySQL, MongoDB
- Messaging: RabbitMQ
- Authentication & Security: JWT

## Microservices Architecture

  **The system consists of the following microservices:**

- User Service: Manages user authentication and profiles.
- Flight Service: Handles flight schedules, availability, and status updates.
- Booking Service: Manages ticket reservations and payments.
- Payment Service: Processes transactions securely.
- Notification Service: Sends alerts and notifications to customers.
- API Gateway: Centralized entry point for routing requests.

## Key Features

- Distributed system with independent microservices
- Event-driven communication using message brokers
- SAGA pattern for distributed transaction management
- Scalable containerized deployment using Docker
- Secure API management with JWT authentication
- Real-time monitoring and logging

## Installation & Setup

## Prerequisites

Before running the project, ensure you have the following installed:

- Docker
- Docker Compose
- Node.js (for running the API Gateway)
- Java 17+ (for Spring Boot services)
- Maven (for building Java services)
- Vue CLI (for frontend development)

## Steps to Run

1. Clone the repository 

   ```sh
   git clone https://github.com/your-username/airline-company.git
   ```
2. In the terminal, make sure you are in the root directory of the project. If not, navigate there using the cd command. For example:
    ```sh
   cd airline-company
   ```
3. Build Java Microservices
     ```sh
   cd backend/services
   mvn clean install
   ```

4. Start all services with Docker Compose

   ```sh
   docker-compose up --build
   ```
5. Run the API Gateway
   ```sh
   cd api-gateway
   npm install
   npm run start
   ```
   
6. Run the Frontend
   ```sh
    cd frontend
   npm install
   npm run serve
   ```
  
### Access the Application

- Frontend:  http://localhost:5173/
- API Gateway: http://localhost:3000
- RabbitMQ Dashboard: http://localhost:15672 (default: guest/guest)

  


