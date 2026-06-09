package com.bankingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.bankingsystem.exception.InsufficientBalanceException;
import com.bankingsystem.model.Customer;
import com.bankingsystem.util.DBConnection;

public class CustomerDAO {


public void createAccount(Customer customer) {

    try {

        Connection con = DBConnection.getConnection();

        String checkSql =
                "SELECT * FROM customers WHERE phone=? OR email=?";

        PreparedStatement checkPs =
                con.prepareStatement(checkSql);

        checkPs.setString(1, customer.getPhone());
        checkPs.setString(2, customer.getEmail());

        ResultSet checkRs = checkPs.executeQuery();

        if (checkRs.next()) {

            System.out.println(
                    "\nAccount already exists with same Phone or Email");

            con.close();
            return;
        }

        String sql =
                "INSERT INTO customers(customer_name,phone,email,balance) VALUES(?,?,?,?)";

        PreparedStatement ps =
                con.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, customer.getCustomerName());
        ps.setString(2, customer.getPhone());
        ps.setString(3, customer.getEmail());
        ps.setDouble(4, 0.0);

        int rows = ps.executeUpdate();

        if (rows > 0) {

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {

                System.out.println(
                        "\n===== ACCOUNT CREATED SUCCESSFULLY =====");

                System.out.println(
                        "Generated Account ID : " + rs.getInt(1));

                System.out.println(
                        "Customer Name        : "
                                + customer.getCustomerName());

                System.out.println(
                        "Phone Number         : "
                                + customer.getPhone());

                System.out.println(
                        "Email                : "
                                + customer.getEmail());

                System.out.println(
                        "Initial Balance      : 0.0");
            }
        }

        con.close();

    } catch (Exception e) {

        e.printStackTrace();
    }
}

public void viewAccount(int accountId) {

    try {

        Connection con = DBConnection.getConnection();

        String sql =
                "SELECT * FROM customers WHERE account_id=?";

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setInt(1, accountId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            System.out.println(
                    "\n===== ACCOUNT DETAILS =====");

            System.out.println(
                    "Account ID : "
                            + rs.getInt("account_id"));

            System.out.println(
                    "Name       : "
                            + rs.getString("customer_name"));

            System.out.println(
                    "Phone      : "
                            + rs.getString("phone"));

            System.out.println(
                    "Email      : "
                            + rs.getString("email"));

            System.out.println(
                    "Balance    : "
                            + rs.getDouble("balance"));

        } else {

            System.out.println("Account Not Found");
        }

        con.close();

    } catch (Exception e) {

        e.printStackTrace();
    }
}

public void deposit(int accountId, double amount) {

    try {

        if (amount <= 0) {

            System.out.println("Amount must be greater than zero");
            return;
        }

        Connection con = DBConnection.getConnection();

        String sql =
                "UPDATE customers SET balance=balance+? WHERE account_id=?";

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setDouble(1, amount);
        ps.setInt(2, accountId);

        int rows = ps.executeUpdate();

        if (rows > 0) {

            System.out.println(
                    "Amount Deposited Successfully");

        } else {

            System.out.println("Account Not Found");
        }

        con.close();

    } catch (Exception e) {

        e.printStackTrace();
    }
}

public void withdraw(int accountId, double amount)
        throws InsufficientBalanceException {

    try {

        if (amount <= 0) {

            System.out.println("Amount must be greater than zero");
            return;
        }

        Connection con = DBConnection.getConnection();

        String checkSql =
                "SELECT balance FROM customers WHERE account_id=?";

        PreparedStatement checkPs =
                con.prepareStatement(checkSql);

        checkPs.setInt(1, accountId);

        ResultSet rs = checkPs.executeQuery();

        if (rs.next()) {

            double balance =
                    rs.getDouble("balance");

            if (balance < amount) {

                throw new InsufficientBalanceException(
                        "Insufficient Balance");
            }

            String updateSql =
                    "UPDATE customers SET balance=balance-? WHERE account_id=?";

            PreparedStatement updatePs =
                    con.prepareStatement(updateSql);

            updatePs.setDouble(1, amount);
            updatePs.setInt(2, accountId);

            updatePs.executeUpdate();

            System.out.println(
                    "Withdrawal Successful");

        } else {

            System.out.println(
                    "Account Not Found");
        }

        con.close();

    } catch (InsufficientBalanceException e) {

        throw e;

    } catch (Exception e) {

        e.printStackTrace();
    }
}

public void deleteAccount(int accountId) {

    try {

        Connection con = DBConnection.getConnection();

        String sql =
                "DELETE FROM customers WHERE account_id=?";

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setInt(1, accountId);

        int rows = ps.executeUpdate();

        if (rows > 0) {

            System.out.println(
                    "Account Deleted Successfully");

        } else {

            System.out.println(
                    "Account Not Found");
        }

        con.close();

    } catch (Exception e) {

        e.printStackTrace();
    }
}

public void transferMoney(int senderId,
                          int receiverId,
                          double amount) {

    Connection con = null;

    try {

        if (amount <= 0) {

            System.out.println("Amount must be greater than zero");
            return;
        }

        con = DBConnection.getConnection();

        con.setAutoCommit(false);

        String checkSql =
                "SELECT balance FROM customers WHERE account_id=?";

        PreparedStatement checkPs =
                con.prepareStatement(checkSql);

        checkPs.setInt(1, senderId);

        ResultSet rs = checkPs.executeQuery();

        if (!rs.next()) {

            System.out.println("Sender Account Not Found");
            return;
        }

        double balance = rs.getDouble("balance");

        if (balance < amount) {

            System.out.println("Insufficient Balance");
            return;
        }

        String debitSql =
                "UPDATE customers SET balance=balance-? WHERE account_id=?";

        PreparedStatement debitPs =
                con.prepareStatement(debitSql);

        debitPs.setDouble(1, amount);
        debitPs.setInt(2, senderId);

        int debitRows =
                debitPs.executeUpdate();

        String creditSql =
                "UPDATE customers SET balance=balance+? WHERE account_id=?";

        PreparedStatement creditPs =
                con.prepareStatement(creditSql);

        creditPs.setDouble(1, amount);
        creditPs.setInt(2, receiverId);

        int creditRows =
                creditPs.executeUpdate();

        if (debitRows > 0 && creditRows > 0) {

            con.commit();

            System.out.println(
                    "Transfer Successful");

        } else {

            con.rollback();

            System.out.println(
                    "Transfer Failed");
        }

        con.close();

    } catch (Exception e) {

        try {

            if (con != null) {

                con.rollback();
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        e.printStackTrace();
    }
}

public void checkBalance(int accountId) {

    try {

        Connection con = DBConnection.getConnection();

        String sql =
                "SELECT balance FROM customers WHERE account_id=?";

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setInt(1, accountId);

        ResultSet rs =
                ps.executeQuery();

        if (rs.next()) {

            System.out.println(
                    "Current Balance : "
                            + rs.getDouble("balance"));

        } else {

            System.out.println(
                    "Account Not Found");
        }

        con.close();

    } catch (Exception e) {

        e.printStackTrace();
    }
}


}