package com.qlhs.qlhs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MarkDAO {
//    // This method retrieves students from StudentDAO without printing
//    public static List<String> getStudents() {
//        // Call the getStudents() method from StudentDAO to retrieve the student list
//        List<String> students = StudentDAO.getID();
//
//        // Return the student list to the calling class
//        return students;
//    }
//
//    // If you want to print the students in MarkDAO, you can create a separate method for that
////    public static void printStudents() {
////        List<String> students = getStudents();
////
////    }

    public static ObservableList<Mark> getMark() {
        ObservableList<Mark> markList = FXCollections.observableArrayList();

        String query = "SELECT * FROM bangDiem";

        try (Connection conn = DatabaseConnector.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String maHS = rs.getString("maHocSinh");
                String nguVan = rs.getString("nguVan");
                String toan = rs.getString("toan");
                String vatLi = rs.getString("vatLi");
                String hoaHoc = rs.getString("hoaHoc");
                String sinhHoc = rs.getString("sinhHoc");
                String lichSu = rs.getString("lichSu");
                String diaLy = rs.getString("diaLy");
                String GDCD = rs.getString("GDCD");
                String ngoaiNgu = rs.getString("ngoaiNgu");
                String congNghe = rs.getString("congNghe");
                String tinHoc = rs.getString("tinHoc");
                String theDuc = rs.getString("theDuc");
                String ghiChuDiem = rs.getString("ghiChuDiem");

                Mark mark = new Mark(maHS,nguVan,toan,vatLi,hoaHoc,sinhHoc,lichSu,diaLy,GDCD,ngoaiNgu,congNghe,tinHoc,theDuc,ghiChuDiem);

                markList.add(mark);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return markList;
    }
}
