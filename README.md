Banking System Simulator – Spring Boot Monolith (MongoDB)
A complete Spring Boot–based Banking Backend Application that manages accounts and financial transactions using REST APIs, MongoDB, Layered Architecture, Logging, and Unit Tests with Coverage.
This project is developed as part of the Java FSD Program – Virtusa (Mini Project Phase-1).

Project Overview
This system simulates essential banking operations such as:
Creating new bank accounts
Depositing and withdrawing money
Transferring funds between accounts
Storing and retrieving transaction history
It is built as a Spring Boot Monolithic Application, following standard layered architecture with MongoDB for persistence.

Features:
✅ Account Management
Auto-generated account number (e.g., MAN1234)
Initial balance = 0
Holder name, status, created date stored
CRUD operations

✅ Financial Transactions
Deposit
Withdraw
Transfer
Retrieve transaction history

✅ Exception Handling
Custom exceptions for:
Invalid amount
Account not found
Insufficient balance
Global exception handler returning clean JSON responses

✅ Logging (SLF4J)
Logs added in controllers, services, and exception handlers
Helps track API calls and system behavior

✅ Unit Testing
JUnit 5 + Mockito
Service tests
Controller tests (MockMvc)
Exception handler tests

✅ Test Coverage
JaCoCo integrated
70%+ overall coverage
Coverage HTML report included

Architecture
Layered Architecture:
Controller → Service → Repository → MongoDB

Project Structure:
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

REST API Endpoints
✔ Account APIs:
| Method | Endpoint                                 | Description          |
| ------ | ---------------------------------------- | -------------------- |
| POST   | `/api/accounts`                          | Create new account   |
| GET    | `/api/accounts/{accNo}`                  | Get specific account |
| GET    | `/api/accounts`                          | Fetch all accounts   |
| PUT    | `/api/accounts/{accNo}/deposit?amount=`  | Deposit amount       |
| PUT    | `/api/accounts/{accNo}/withdraw?amount=` | Withdraw amount      |
| DELETE | `/api/accounts/{accNo}`                  | Delete account       |

✔ Transaction APIs:
| Method | Endpoint                             | Description                      |
| ------ | ------------------------------------ | -------------------------------- |
| POST   | `/api/accounts/transfer`             | Transfer amount                  |
| GET    | `/api/accounts/{accNo}/transactions` | Get all transactions for account |

