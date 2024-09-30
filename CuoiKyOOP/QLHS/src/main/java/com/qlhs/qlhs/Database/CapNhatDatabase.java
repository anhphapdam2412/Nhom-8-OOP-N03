package com.qlhs.qlhs.Database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import static com.qlhs.qlhs.Database.KetNoiCSDL.connect;

public class CapNhatDatabase {

    // Cập nhật thông tin học sinh
    public static void capNhatTT(String maHS, String hoDem, String ten, String ngaySinh,
                                 String gioiTinh, String maDinhDanh, String sdt,
                                 String email, String lop, String diaChi, String ghiChuTT, String trangThai,
                                 String script, String script2) {
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;

        try {
            // Kết nối cơ sở dữ liệu
            connection = connect();
            connection.setAutoCommit(false);  // Bắt đầu transaction

            // Cập nhật thông tin học sinh
            preparedStatement1 = connection.prepareStatement(script);
            preparedStatement1.setString(1, hoDem);
            preparedStatement1.setString(2, ten);
            preparedStatement1.setDate(3, Date.valueOf(ngaySinh));
            preparedStatement1.setBoolean(4, Boolean.parseBoolean(gioiTinh));
            preparedStatement1.setString(5, maDinhDanh);
            preparedStatement1.setString(6, sdt);
            preparedStatement1.setString(7, email);
            preparedStatement1.setString(8, lop);
            preparedStatement1.setString(9, diaChi);
            preparedStatement1.setString(10, ghiChuTT);
            preparedStatement1.setString(12, maHS);
            preparedStatement1.setBoolean(11, Boolean.parseBoolean(trangThai));

            int rowsAffected1 = preparedStatement1.executeUpdate();
            System.out.println(rowsAffected1 + " record(s) updated in table 1.");

            // Thực hiện script2 (có thể là một câu lệnh khác)
            if (!Objects.equals(script2, "")){

                preparedStatement2 = connection.prepareStatement(script2);
                preparedStatement2.setString(1, maHS);
                int rowsAffected2 = preparedStatement2.executeUpdate();
                System.out.println(rowsAffected2 + " record(s) updated in table 2.");
            }


            // Commit nếu cả hai thành công
            connection.commit();
        } catch (SQLException e) {
            // Rollback nếu có lỗi
            try {
                connection.rollback();
                System.err.println("Transaction rolled back due to error: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                System.err.println("Error during rollback: " + rollbackEx.getMessage());
            }
        }
    }
}
