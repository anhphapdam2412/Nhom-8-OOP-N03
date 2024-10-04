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
                                 Boolean gioiTinh, String maDinhDanh, String sdt,
                                 String email, String lop, String diaChi, String ghiChuTT, String trangThai,
                                 String query, String query2) {
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;

        try {
            // Kết nối cơ sở dữ liệu
            connection = connect();
            connection.setAutoCommit(false);  // Bắt đầu transaction

            // Cập nhật thông tin học sinh
            preparedStatement1 = connection.prepareStatement(query);
            preparedStatement1.setString(1, hoDem);
            preparedStatement1.setString(2, ten);
            preparedStatement1.setDate(3, Date.valueOf(ngaySinh));
            preparedStatement1.setBoolean(4, gioiTinh);
            System.out.println(gioiTinh);
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

            // Thực hiện script2
            if (!Objects.equals(query2, "")){

                preparedStatement2 = connection.prepareStatement(query2);
                preparedStatement2.setString(1, maHS);
                int rowsAffected2 = preparedStatement2.executeUpdate();
                System.out.println(rowsAffected2 + " record(s) updated in table 1.");
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
    // Xóa học sinh
    public static void xoaTT(String maHS, String trangThai, String query)
    {
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;

        try {
            // Kết nối cơ sở dữ liệu
            connection = connect();
            connection.setAutoCommit(false);  // Bắt đầu transaction

            // Xoa học sinh
            preparedStatement1 = connection.prepareStatement(query);
            preparedStatement1.setBoolean(1, Boolean.parseBoolean(trangThai));

            preparedStatement1.setString(2, maHS);


            int rowsAffected1 = preparedStatement1.executeUpdate();
            System.out.println(rowsAffected1 + " record(s) updated in table 2.");
            // Commit
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
    // Cập nhật điểm
    public static void capNhatDiem(String maHS, String nguVan, String toan, String vatLi, String hoaHoc, String sinhHoc, String lichSu, String diaLy, String GDCD, String congNghe, String tinHoc, String theDuc, String ngoaiNgu, String maNN, String hanhKiem, String ghiChuDiem, String query)
         {
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;

        try {
            // Kết nối cơ sở dữ liệu
            connection = connect();
            connection.setAutoCommit(false);  // Bắt đầu transaction

            // Cập nhật thông tin học sinh
            preparedStatement1 = connection.prepareStatement(query);
            preparedStatement1.setString(1, nguVan);
            preparedStatement1.setString(2, toan);
            preparedStatement1.setString(3, vatLi);
            preparedStatement1.setString(4, hoaHoc);
            preparedStatement1.setString(5, sinhHoc);
            preparedStatement1.setString(6, lichSu);
            preparedStatement1.setString(7, diaLy);
            preparedStatement1.setString(8, GDCD);

            preparedStatement1.setString(9, congNghe);
            preparedStatement1.setString(10, tinHoc);
            preparedStatement1.setString(11, theDuc);
            preparedStatement1.setString(12, ngoaiNgu);
            preparedStatement1.setString(13, maNN);
            preparedStatement1.setString(14, hanhKiem);
            preparedStatement1.setString(15, ghiChuDiem);
            preparedStatement1.setString(16, maHS);


            int rowsAffected1 = preparedStatement1.executeUpdate();
            System.out.println(rowsAffected1 + " record(s) updated in table 2.");
            // Commit
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
