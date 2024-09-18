package com.qlhs.qlhs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/csdl_qlhs";
    private static final String USER = "root";
    private static final String PASSWORD = "123321";

    // Establish a connection to the database
    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection to the database established successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
            // You can log the exception to a file or use a logging framework instead
        }
        return connection;
    }

}
