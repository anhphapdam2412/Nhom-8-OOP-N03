package com.qlhs.qlhs.Database;

import com.qlhs.qlhs.Model.Grade;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GradeDAO {

    public static ArrayList<Grade> getGrades() {
        ArrayList<Grade> grades = new ArrayList<>();

        String query = "SELECT * FROM grade";

        try (Connection conn = DatabaseConnection.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String studentID = rs.getString("studentID");
                float literature = rs.getFloat("literature");
                float math = rs.getFloat("math");
                float physics = rs.getFloat("physics");
                float chemistry = rs.getFloat("chemistry");
                float biology = rs.getFloat("biology");
                float history = rs.getFloat("history");
                float geography = rs.getFloat("geography");
                float civicEdu = rs.getFloat("civicEdu");
                float technology = rs.getFloat("technology");
                float it = rs.getFloat("it");
                String physicalEdu = rs.getString("physicalEdu");
                float foreignLang = rs.getFloat("foreignLang");
                String languageCode = rs.getString("languageCode");
                String academicPerformance = rs.getString("academicPerformance");
                String conduct = rs.getString("conduct");
                String gradeNotes = rs.getString("gradeNotes");
                float avgGrade = rs.getFloat("avgGrade");
                String award = rs.getString("award");

                Grade diem = new Grade(studentID, literature, math, physics, chemistry, biology, history, geography, civicEdu, technology, it, physicalEdu, foreignLang, languageCode, academicPerformance, conduct, gradeNotes, avgGrade, award);

                grades.add(diem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }
}
