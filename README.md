# Expense Tracker App

A simple expense tracker project built with Spring Boot, Spring Data JPA, and Spring Security.

## Setup

To run the application:
- Clone the repository from [https://github.com/tunadag/ExpenseTrackerApp](https://github.com/tunadag/ExpenseTrackerApp).
- Open a terminal in the cloned directory.
- Start PostgreSQL by running the command `docker-compose up`.
- Run the application from the directory `src/main/java/com/tunadag/ExpenseTrackerApp.java`.
- Access the API endpoints using Swagger UI at [http://localhost:8090/api/v1/swagger-ui/index.html#/](http://localhost:8090/api/v1/swagger-ui/index.html#/).

## Preloaded Data

The application comes with preloaded data for testing purposes. The data can be loaded using the `DataImpl.java` class located in `src/main/java/com/tunadag/utility/`.

## Test Scenarios

To test the application, you can follow the steps below:

1. Register a new user by sending a POST request to `/auth/register` with the email and password.
2. Log in to the application by sending a POST request to `/auth/login` with the registered email and password. Use the returned JWT token to authenticate future requests by clicking the "Authorize" button in Swagger UI.
3. After logging in, you can try the available endpoints listed below:

### Account Controller Endpoints

- `PUT /accounts/update`: Update the name of an existing account for the user.
- `POST /accounts/addAccount`: Add a new account for the user.
- `GET /accounts/{accountId}/transactions`: Get all transactions for a specific account of the user.
- `GET /accounts/getAccounts`: Get all accounts for the user.
- `DELETE /accounts/deleteAccount`: Delete an account for the user.

### Transaction Controller Endpoints

- `POST /transactions/add`: Add a new transaction to a specific account for the user.
- `GET /transactions/search`: Search transactions by amount or category within a specific account and a specified period (month/year) for the user.
- `DELETE /transactions/delete`: Delete a transaction from a specific account for the user.

### Auth Controller Endpoints

- `POST /auth/register`: Register a new user.
- `POST /auth/login`: Log in an existing user.

