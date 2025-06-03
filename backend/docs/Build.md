# ✅ Requisitos para Subir o Backend

## 🔧 1. Dependências Instaladas (Pré-requisitos)

- Java 17
- Docker
- Git

---

## ⚙️ 2. Execução Inicial

Na raiz do backend (via WSL ou PowerShell), execute:

```bash
./build.sh
```
Caso ocorra erro de fim de linha (no Windows), execute:
```bash
sed -i -e 's/\r$//' build.sh
```
E tente novamente.


### Esse processo irá:

Compilar os microsserviços;

Gerar os JARs;

Criar as imagens Docker;

Subir os serviços e dependências (PostgreSQL, RabbitMQ, etc.).

## 🧱 3. Estrutura dos Microsserviços

| Microsserviço         | Porta               | Descrição                                      |
|------------------------|---------------------|------------------------------------------------|
| `ms-auth`             | 8081                | Spring Boot com autenticação JWT              |
| `ms-cliente`          | 8082                | Cadastro e gerenciamento de milhas            |
| `ms-voo`              | 8083                | Gestão de voos                                |
| `ms-reserva`          | 8084                | CQRS, reservas e check-in                     |
| `ms-funcionario`      | 8085                | Cadastro e controle de funcionários           |
| `api-gateway`         | 8080                | Roteamento e agregações                       |
| Rabbit, PG, mongo    | Interno via Compose | Comunicação assíncrona e persistência         |

---

## 🔎 4. Verificação de Saúde

Após subir os serviços, verifique se estão em execução com:

```bash
docker compose ps
```

---

## 🐳 5. Sobre o `docker-compose.yml`

O arquivo `docker-compose.yml` é o responsável por definir e orquestrar todos os serviços que compõem o backend do sistema. Ele especifica:

- Quais containers devem ser criados (como os microsserviços, banco de dados, RabbitMQ, etc.)
- As redes, volumes e variáveis de ambiente necessárias
- As portas expostas para comunicação entre serviços

Quando você executa o script `./build.sh`, o Docker Compose utiliza esse arquivo para subir automaticamente todos os serviços definidos, garantindo que o ecossistema da aplicação esteja funcionando de forma integrada.

> ✅ Manter esse arquivo bem configurado é essencial para garantir o correto funcionamento dos ambientes de desenvolvimento, testes e produção.
