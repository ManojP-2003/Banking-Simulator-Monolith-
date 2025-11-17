# Banking System Simulator – Spring Boot Monolith (MongoDB)

A complete Spring Boot–based **Banking Backend Application** that manages accounts and financial transactions using **REST APIs**, **MongoDB**, **Layered Architecture**, **Logging**, and **Unit Tests with Coverage**.

This project is developed as part of the **Java FSD Program – Virtusa (Mini Project Phase-1)**.

---



# Project Overview

This system simulates essential banking operations such as:

* Creating new bank accounts
* Depositing and withdrawing money
* Transferring funds between accounts
* Storing and retrieving transaction history

It is built as a **Spring Boot Monolithic Application**, following standard layered architecture with **MongoDB** for persistence.

---

# Features

### Account Management

* Auto-generated account number (e.g., **MAN1234**)
* Initial balance = 0
* Holder name, status, created date stored
* CRUD operations

### Financial Transactions

* Deposit
* Withdraw
* Transfer
* Retrieve transaction history

### Exception Handling

* Custom exceptions for:

  * Invalid amount
  * Account not found
  * Insufficient balance
* Global exception handler returning clean JSON responses

### Logging (SLF4J)

* Logs added in controllers, services, and exception handlers
* Helps track API calls and system behavior

### Unit Testing

* JUnit 5 + Mockito
* Service tests
* Controller tests (MockMvc)
* Exception handler tests

### Test Coverage

* 70%+ overall coverage

---

#  Architecture

### Layered Architecture:

```
Controller → Service → Repository → MongoDB
```

### UML-style Overview:

```
+---------------------+
|   AccountController |
+---------------------+
          ↓
+---------------------+
|   AccountService    |
+---------------------+
          ↓
+---------------------+
|  AccountRepository  |
+---------------------+
          ↓
+---------------------+
|      MongoDB        |
+---------------------+
```

---

# Project Structure

```
src/
├── main/java/com/bank/banking_monolith
│   ├── controller
│   ├── service
│   ├── repository
│   ├── model
│   ├── exception
│   └── BankingMonolithApplication.java
│
└── test/java/com/bank/banking_monolith
    ├── controller
    ├── service
    └── exception
```

---

# Database Structure (MongoDB)

### **accounts collection**

Example:

```json
{
  "_id": "6751d95...",
  "accountNumber": "MAN8234",
  "holderName": "Manoj",
  "balance": 5000,
  "status": "ACTIVE",
  "createdAt": "2025-11-15T10:30:00Z"
}
```

### **transactions collection**

Example:

```json
{
  "_id": "6751d97...",
  "transactionId": "TXN-00121",
  "type": "TRANSFER",
  "amount": 500,
  "timestamp": "2025-11-16T12:01:00Z",
  "status": "SUCCESS",
  "sourceAccount": "MAN8234",
  "destinationAccount": "KIR9921"
}
```

---

# REST API Endpoints

## Account APIs

| Method | Endpoint                                 | Description          |
| ------ | ---------------------------------------- | -------------------- |
| POST   | `/api/accounts`                          | Create new account   |
| GET    | `/api/accounts/{accNo}`                  | Get specific account |
| GET    | `/api/accounts`                          | Fetch all accounts   |
| PUT    | `/api/accounts/{accNo}/deposit?amount=`  | Deposit amount       |
| PUT    | `/api/accounts/{accNo}/withdraw?amount=` | Withdraw amount      |
| DELETE | `/api/accounts/{accNo}`                  | Delete account       |

---

## Transaction APIs

| Method | Endpoint                             | Description                      |
| ------ | ------------------------------------ | -------------------------------- |
| POST   | `/api/accounts/transfer`             | Transfer amount                  |
| GET    | `/api/accounts/{accNo}/transactions` | Get all transactions for account |

---

#  Exception Handling

Custom exceptions:

* **AccountNotFoundException**
* **InvalidAmountException**
* **InsufficientBalanceException**

Handled with a **GlobalExceptionHandler**, returning:

```json
{
  "message": "Account not found",
  "timestamp": "2025-11-16T12:00:00",
  "status": 404
}
```

---

# Logging (SLF4J)

Logging enabled for:

* Controllers (`info`)
* Services (`debug`, `warn`)
* Exceptions (`error`)

Examples:

```
INFO  - API: Create account request
DEBUG - Generated account number: MAN1234
WARN  - Insufficient balance
ERROR - AccountNotFoundException handled
```

---

#  Unit Testing

JUnit + Mockito test coverage includes:

* Account service tests
* Transaction service tests
* Controller tests 
* Exception handler tests
* JaCoCo HTML report

---

#  Coverage Report (JaCoCo)

Command to generate:

```
mvn clean test
```

Find report at:

```
target/site/jacoco/index.html
```

# How to Run

### Step 1 — Start MongoDB

```
mongod
```

### Step 2 — Run the Spring Boot App

Using IntelliJ:

```
Run → BankingMonolithApplication
```

Server will start on:

```
http://localhost:9090
```

---



