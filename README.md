 BankingSystem

A simple Banking Management System developed using Java, JDBC, and MySQL.

 Features
- Create Account
- View Account Details
- Deposit Money
- Withdraw Money
- Transfer Money
- Check Balance
- Delete Account
- Auto Generated Account ID
- Duplicate Account Prevention (Phone & Email)

 Technologies Used
- Java
- JDBC
- MySQL
- Eclipse IDE

 Database Table

CREATE TABLE customers (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(100) NOT NULL,
    phone VARCHAR(15) UNIQUE,
    email VARCHAR(100) UNIQUE,
    balance DOUBLE DEFAULT 0.0
);

 Project Structure

- BankingApplication.java
- CustomerDAO.java
- Customer.java
- DBConnection.java
- InsufficientBalanceException.java
