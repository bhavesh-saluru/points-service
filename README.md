# KudosPoints - Points Service

This is the `points-service` microservice for the KudosPoints system. It is a stateful service that acts as the "bank" or "immutable ledger" for all member points.

## Role in the System

This service's sole responsibility is to manage member data and their transaction history. It provides a secure REST API for creating members and adding/retrieving point transactions. It is the single source of truth for a member's balance.

### Core Technologies
* **Java 21 & Spring Boot:** Core application framework.
* **Spring Data JPA:** For communication with the database.
* **PostgreSQL:** The database for storing member and ledger data.
* **Flyway:** For version-controlled database migrations.
* **Docker:** Fully containerized for production and development.

## API Endpoints

This service exposes a REST API (default port: `8080`):

* `POST /api/v1/members`: Creates a new member.
* `GET /api/v1/members`: Retrieves all members list.
* `GET /api/v1/members/{id}`: Retrieves a specific member.
* `POST /api/v1/members/{memberId}/transactions`: Adds a new transaction (earn/redeem) to a member's ledger.
* `GET /api/v1/members/{memberId}/transactions`: Retrieves a member's complete transaction history.
* `GET /api/v1/members/{memberId}/balance`: Calculates and returns a member's current points balance.

---

### Full System Architecture

This service is a component of a larger microservices application. For a complete system overview and instructions on how to run the entire stack, please see the main [kudospoints "hub" repository](https://github.com/bhavesh-saluru/kudospoints).