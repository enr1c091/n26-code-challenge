# N26 Backend Challenge

This repository contains the completed Backend Challenge for N26.

# Start Application

1. Build application.
```sh
$ mvn clean install
```
2. Run Spring Boot application.
```sh
$ mvn spring-boot:run
```

# Application Endpoints

| Resource | HTTP Method | Endpoint |
| ------ | ------ | ------ |
| Create a new transaction | POST | http://localhost:8080/transaction
| Retrieve transaction statistics | GET | http://localhost:8080/statistics
| Swagger Doc | GET | http://localhost:8080/swagger-ui.html/

> **Considerations:**
> - Project Lombok was considered to decrease getters/setters/constructors creation but didn't use it since it requires a client installation and didn't want to give trouble to install.
