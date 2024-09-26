package com.qlhs.qlhs.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KetNoiCSDL {

    static ConfigReader configReader = new ConfigReader();
    private static final String URL = configReader.getURL();
    private static final String USER = configReader.getUSER();
    private static final String PASSWORD = configReader.getPASSWORD();
    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối tới Database thành công.");
        } catch (SQLException e) {
            System.err.println("Kết nối tới Database thất bại: " + e.getMessage());
        }
        return connection;
    }

}
