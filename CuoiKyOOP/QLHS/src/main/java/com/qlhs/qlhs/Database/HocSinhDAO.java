package com.qlhs.qlhs.Database;

import com.qlhs.qlhs.Model.HocSinh;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class HocSinhDAO {

    public static ObservableList<HocSinh> getDSHocSinh() {
        ObservableList<HocSinh> studentList = FXCollections.observableArrayList();

        String query = "SELECT * FROM HocSinh";

        try (Connection conn = KetNoiCSDL.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String maHS = rs.getString("maHS");
                String hoDem = rs.getString("hoDem");
                String ten = rs.getString("ten");
                String ngaySinh = rs.getString("ngaySinh");
                String gioiTinh = rs.getString("gioiTinh");
                String maDinhDanh = rs.getString("maDinhDanh");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");
                String lop = rs.getString("lop");
                String diaChi = rs.getString("diaChi");
                String ghiChu = rs.getString("ghiChuTT");
                String trangThai = rs.getString("trangThai");


                HocSinh student = new HocSinh(maHS, hoDem, ten, ngaySinh, gioiTinh, maDinhDanh, sdt, email, lop, diaChi, ghiChu, trangThai);

                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }
}
