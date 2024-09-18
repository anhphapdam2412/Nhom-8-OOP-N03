package com.qlhs.qlhs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentDAO {

    static List<String> tempStudentList = new ArrayList<>();
    public static ObservableList<Student> getStudents() {
        ObservableList<Student> studentList = FXCollections.observableArrayList();

        String query = "SELECT * FROM thongTinHocSinh";

        try (Connection conn = DatabaseConnector.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            int stt = 0;
            while (rs.next()) {
                stt = stt + 1;
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


                Student student = new Student(Integer.toString(stt), maHS, hoDem, ten, ngaySinh, gioiTinh, maDinhDanh, sdt, email, lop, diaChi, ghiChu);

                List<String> tempStudent = new ArrayList<>(Arrays.asList(Integer.toString(stt), maHS, hoDem, ten, ngaySinh, gioiTinh, maDinhDanh, sdt, email, lop, diaChi, ghiChu));

                tempStudentList.add(String.valueOf(tempStudent));
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }
    public static List<String> getID(){
        return tempStudentList;
    }
}
