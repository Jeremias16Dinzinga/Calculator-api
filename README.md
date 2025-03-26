# Calculator API

## Overview
Calculator API is a RESTful microservice built with **Spring Boot** and **Kafka** that provides basic arithmetic operations with **arbitrary precision decimal numbers**.

## Technologies Used
The project is built using the following technologies:
* Java 21 – Core programming language
* Spring Boot – Framework for REST API and modularization
* Apache Kafka – Message broker for inter-module communication
* Maven – Dependency management and project build tool
* Docker & Docker Compose – Containerization and service orchestration
* JUnit & Mockito – Unit testing framework
* SLF4J Logging
* MDC Propagation
* Swagger Documentation

## Features
- Supports addition, subtraction, multiplication, and division
- Handles **two operands only (`a` and `b`)
- Uses Apache Kafka for asynchronous communication between modules
- Configurable via `application.properties`
- Built with Spring Boot and modularized using Maven
- Provides a Docker setup for easy deployment
- Includes unit tests for reliability
- Structured logs with SLF4J and Logback for each request.
- Propagation of unique identifier via MDC between modules and Kafka.

## Getting Started
### **1 Clone the Repository**
```sh
git clone https://github.com/Jeremias16Dinzinga/calculator-api.git
cd calculator-api
```

### **2 Running with Docker**
Ensure you have Docker and Docker Compose installed, then run:
```sh
docker-compose up --build
```

### **3 Running Locally with Maven**
Make sure you have **Java 21** and **Maven** installed, then execute:
```sh
mvn clean install
mvn spring-boot:run
```

## API Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET`  | `/sum?a=1&b=2` | Returns `1 + 2` |
| `GET`  | `/subtract?a=5&b=3` | Returns `5 - 3` |
| `GET`  | `/multiply?a=4&b=6` | Returns `4 * 6` |
| `GET`  | `/divide?a=8&b=2` | Returns `8 / 2` |

## Example Response
```json
{
  "requestId": "0241bf6c-97f7-4730-bfec-6221466de7e",
  "result": 3
}
```

## API Documentation (Swagger)
Once the application is running, access Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```

## Author
**[Jeremias Dinzinga](https://www.linkedin.com/in/jeremias-dinzinga-a9867b221/)** - Backend Developer

