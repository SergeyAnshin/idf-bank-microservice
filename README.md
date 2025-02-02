# Bank Transactions and Limit Management

## Description

The project involves developing a microservice for a banking system that processes transactions in various currencies
and manages client spending limits. The microservice receives information about transactions, converts amounts based on
current exchange rates, and stores the data in a database. The service allows clients to set spending limits and flags
transactions that exceed the limit.

## Technologies

- **Java 17**
- **Maven**
- **Spring Boot/Web/Data/Test/Cache**
- **PostgreSQL,H2**
- **Docker, Docker Compose**
- **Liquibase**
- **Lombok, Mapstruct**

## Features

- Receiving and storing information about expense transactions in the database;
- Retrieving a list of transactions that have exceeded the set limits;
- Flagging transactions that surpass the monthly transaction limit;
- Maintaining separate monthly spending limits for two categories: product and service;
- Setting new expense limits;
- Receiving daily exchange rate data for currency pairs and storing it in the database;
- Preparing the service for deployment in Docker;
- Implementing a CI pipeline.

## Installation and Setup

### Prerequisites

- **Java 17**
- **Maven**
- **Docker**

### Running with Docker

1. Clone the repository:
    ```bash
    git clone https://github.com/SergeyAnshin/idf-bank-microservice.git
    ```
2. Create a file with a password for the database at the path: `src/main/resources/secrets/password.txt`
3. Start the service using Docker Compose:
    ```bash
    docker-compose up
    ```
4. The microservice will be available at `http://localhost:8080`.

## API

### 1. Registering a New transaction

**POST /api/debit-transactions**

- Receives transaction data and saves it to the database.
- Example request body:
    ```json
    {
      "account_from": "1",
      "account_to": "2",
      "currency_shortname": "USD",
      "sum": 42,
      "expense_category": "product",
      "datetime": "2022-01-30T00:00:00+06"
    }
    ```
- Upon successful creation, the response includes:
    - Status Code: 201 Created
    - Location Header: The URI where the newly created transaction can be accessed.

### 2. Getting Transactions with Exceeded limit

**GET /api/transactions/exceeded-limit**

- Returns a list of transactions exceeding the set limit.
- Example request body:
    ```json
    {
      "account_from": "1"
    }
    ```
- Example response:
    ```json
    [
      {
        "account_from": "1",
        "account_to": "2",
        "currency_shortname": "KZT",
        "sum": 500.00,
        "expense_category": "product",
        "datetime": "2022-01-05T00:00:00+06",
        "limit_sum": 1000.00,
        "limit_datetime": "2022-01-01T00:00:00+06",
        "limit_currency_shortname": "USD"
      }
    ]
    ```

### 3. Setting a New Limit

**POST /api/expense-limits**

- Sets a new expense limit
- Example request body:
    ```json
    {
      "limit": 1000.00,
      "currency_shortname": "USD",
      "expense_category": "product",
      "account_to": 1
    }
    ```
- Upon successful creation, the response includes:
    - Status Code: 201 Created
    - Location Header: The URI where the newly created limit can be accessed.
