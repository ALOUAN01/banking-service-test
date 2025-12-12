# Banking Service – Technical Test

## Description
This project implements the core functionalities of a simple banking system as part of a technical test.

The application allows a client to:
- Deposit money
- Withdraw money
- Print a bank statement showing all transactions

The implementation follows the provided specifications, with a focus on simplicity, correctness, and clean design.

---

## Technologies Used
- Java
- Maven
- JUnit 5

---

## Functional Requirements
- Deposit a positive amount of money
- Withdraw a positive amount of money (with balance validation)
- Prevent withdrawals when funds are insufficient
- Print the bank statement in **reverse chronological order**
- Handle invalid inputs using exceptions

---

## Design Choices
- Transactions are stored in memory using an `ArrayList`
- Money amounts are represented as integers, as required
- A dedicated `Transaction` class is used to store transaction data
- Business logic is covered by unit tests using **JUnit 5**
- Output formatting is kept simple, as specified in the instructions

---

## Tests
Automated unit tests are implemented to validate:
- Deposits and withdrawals
- Balance consistency
- Exception handling for invalid inputs
- Reverse chronological order of printed statements
- Edge cases (empty account, full withdrawal, large amounts)

### Run tests
```bash
mvn test
