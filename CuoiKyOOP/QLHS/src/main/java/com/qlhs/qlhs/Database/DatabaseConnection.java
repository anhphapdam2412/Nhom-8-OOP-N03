package com.qlhs.qlhs.Database;

import com.qlhs.qlhs.Controller.ConfigReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    static ConfigReader configReader = new ConfigReader();
    private static final String URL = configReader.getURL();
    private static final String USER = configReader.getUSER();
    private static final String PASSWORD = configReader.getPASSWORD();

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the Database successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to connect to the Database: " + e.getMessage());
        }
        return connection;
    }
}
