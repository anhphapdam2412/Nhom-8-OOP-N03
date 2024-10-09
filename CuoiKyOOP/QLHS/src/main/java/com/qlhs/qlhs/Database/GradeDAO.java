package com.qlhs.qlhs.Database;

import com.qlhs.qlhs.Model.Grade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GradeDAO {

    public static ObservableList<Grade> getGrades() {
        ObservableList<Grade> gradeList = FXCollections.observableArrayList();

        String query = "SELECT * FROM Grade";

        try (Connection conn = DatabaseConnection.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String studentID = rs.getString("studentID");
                String literature = rs.getString("literature");
                String math = rs.getString("math");
                String physics = rs.getString("physics");
                String chemistry = rs.getString("chemistry");
                String biology = rs.getString("biology");
                String history = rs.getString("history");
                String geography = rs.getString("geography");
                String civicEdu = rs.getString("civicEdu");
                String technology = rs.getString("technology");
                String it = rs.getString("it");
                String physicalEdu = rs.getString("physicalEdu");
                String foreignLang = rs.getString("foreignLang");
                String languageCode = rs.getString("languageCode");
                String academicPerformance = rs.getString("academicPerformance");
                String conduct = rs.getString("conduct");
                String gradeNotes = rs.getString("gradeNotes");
                String avgGrade = rs.getString("avgGrade");
                String award = rs.getString("award");

                Grade diem = new Grade(studentID, literature, math, physics, chemistry, biology, history, geography, civicEdu, technology, it, physicalEdu, foreignLang, languageCode, academicPerformance, conduct, gradeNotes, avgGrade, award);

                gradeList.add(diem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gradeList;
    }
}
