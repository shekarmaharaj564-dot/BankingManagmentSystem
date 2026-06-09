package com.bankingsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/banking_system";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "root1234";

    public static Connection getConnection() {

        Connection connection = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}