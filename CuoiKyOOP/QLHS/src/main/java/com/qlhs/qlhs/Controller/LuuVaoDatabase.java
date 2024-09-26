package com.qlhs.qlhs.Controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.qlhs.qlhs.Controller.KetNoiCSDL.connect;

public class LuuVaoDatabase {
    // Insert a student record into the database
    public static void capNhatTT(String maHS, String hoDem, String ten, String ngaySinh,
                             String gioiTinh, String maDinhDanh, String sdt,
                             String email, String lop, String diaChi, String ghiChuTT, String script) {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(script)) {

            preparedStatement.setString(1, hoDem);
            preparedStatement.setString(2, ten);
            preparedStatement.setDate(3, Date.valueOf(ngaySinh));
            preparedStatement.setBoolean(4, Boolean.parseBoolean(gioiTinh));
            preparedStatement.setString(5, maDinhDanh);
            preparedStatement.setString(6, sdt);
            preparedStatement.setString(7, email);
            preparedStatement.setString(8, lop);
            preparedStatement.setString(9, diaChi);
            preparedStatement.setString(10, ghiChuTT);
            preparedStatement.setString(11, maHS);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " record(s) inserted.");
        } catch (SQLException e) {
            System.err.println("Error inserting record: " + e.getMessage());
        }
    }
}
