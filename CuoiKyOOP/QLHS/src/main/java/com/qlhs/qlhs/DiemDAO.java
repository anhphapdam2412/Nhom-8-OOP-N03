package com.qlhs.qlhs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DiemDAO {

    public static ObservableList<Diem> getDiem() {
        ObservableList<Diem> danhSachDiem = FXCollections.observableArrayList();

        String query = "SELECT * FROM bangDiem";

        try (Connection conn = DatabaseConnector.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String maHS = rs.getString("maHS");
                String nguVan = rs.getString("nguVan");
                String toan = rs.getString("toan");
                String vatLi = rs.getString("vatLi");
                String hoaHoc = rs.getString("hoaHoc");
                String sinhHoc = rs.getString("sinhHoc");
                String lichSu = rs.getString("lichSu");
                String diaLy = rs.getString("diaLy");
                String GDCD = rs.getString("GDCD");
                String congNghe = rs.getString("congNghe");
                String tinHoc = rs.getString("tinHoc");
                String theDuc = rs.getString("theDuc");
                String ngoaiNgu = rs.getString("ngoaiNgu");
                String maNN = rs.getString("maNN");
                String hocLuc = rs.getString("hocLuc");
                String hanhKiem = rs.getString("hanhKiem");
                String ghiChuDiem = rs.getString("ghiChuDiem");

                Diem diem = new Diem(maHS, nguVan, toan, vatLi, hoaHoc, sinhHoc, lichSu, diaLy, GDCD, congNghe, tinHoc, theDuc, ngoaiNgu, maNN, hocLuc, hanhKiem, ghiChuDiem);

                danhSachDiem.add(diem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachDiem;
    }
}
