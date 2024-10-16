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

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String studentID = rs.getString("studentID");
                Float literature = getFloatOrNull(rs, "literature");
                Float math = getFloatOrNull(rs, "math");
                Float physics = getFloatOrNull(rs, "physics");
                Float chemistry = getFloatOrNull(rs, "chemistry");
                Float biology = getFloatOrNull(rs, "biology");
                Float history = getFloatOrNull(rs, "history");
                Float geography = getFloatOrNull(rs, "geography");
                Float civicEdu = getFloatOrNull(rs, "civicEdu");
                Float technology = getFloatOrNull(rs, "technology");
                Float it = getFloatOrNull(rs, "it");
                String physicalEdu = rs.getString("physicalEdu");
                Float foreignLang = getFloatOrNull(rs, "foreignLang");
                String languageCode = rs.getString("languageCode");
                String academicPerformance = rs.getString("academicPerformance");
                String conduct = rs.getString("conduct");
                String gradeNotes = rs.getString("gradeNotes");
                Float avgGrade = getFloatOrNull(rs, "avgGrade");
                String award = rs.getString("award");

                Grade grade = new Grade(studentID, literature, math, physics, chemistry, biology, history, geography,
                        civicEdu, technology, it, physicalEdu, foreignLang, languageCode,
                        academicPerformance, conduct, gradeNotes, avgGrade, award);

                grades.add(grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    private static Float getFloatOrNull(ResultSet rs, String columnName) throws SQLException {
        float value = rs.getFloat(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return value;
        }
    }
}
