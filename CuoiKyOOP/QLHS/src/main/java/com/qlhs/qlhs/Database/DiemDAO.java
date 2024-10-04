package com.qlhs.qlhs.Database;

import com.qlhs.qlhs.Model.Diem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DiemDAO {

    public static ObservableList<Diem> getDiem() {
        ObservableList<Diem> danhSachDiem = FXCollections.observableArrayList();

        String query = "SELECT * FROM Diem";

        try (Connection conn = KetNoiCSDL.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
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
                String diemTb = rs.getString("diemTb");

                Diem diem = new Diem(maHS, nguVan, toan, vatLi, hoaHoc, sinhHoc, lichSu, diaLy, GDCD, congNghe, tinHoc, theDuc, ngoaiNgu, maNN, hocLuc, hanhKiem, ghiChuDiem, diemTb);

                danhSachDiem.add(diem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachDiem;
    }
}
