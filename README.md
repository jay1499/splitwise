# Splitwise Application

This repository contains a Dockerized version of the Splitwise application. The application runs in Java Spring Boot framework using PostgreSQL as the database.

## Usage

### Prerequisites

- Docker Engine: Ensure Docker is installed on your system. You can download and install Docker from [here](https://docs.docker.com/get-docker/).

### Running the Application

1. Clone this repository to your local machine.

```bash
git clone https://github.com/jay1499/splitwise.git
```

2. Navigate to the cloned directory.
```bash
cd splitwise
```

3. Run the application using Docker Compose.
```bash
docker-compose up
```

4. Application can be accessed at http://localhost:8080

5. Database can be accessed using the following commands
```bash
docker ps
docker exec -it <postgresql container id> psql -U compose-postgres
```

6. Swagger link can be accessed at http://localhost:8080/swagger-ui/index.html

## POSTMAN COLLECTION

You can find Postman collection here [here](https://gist.github.com/jay1499/daabfdc6346eab3e74322cf4806e0312).

