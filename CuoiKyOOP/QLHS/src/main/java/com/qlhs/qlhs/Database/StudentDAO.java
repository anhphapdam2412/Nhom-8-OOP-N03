package com.qlhs.qlhs.Database;

import com.qlhs.qlhs.Model.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentDAO {

    public static ArrayList<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<>();

        String query = "SELECT * FROM student";

        try (Connection conn = DatabaseConnection.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {

                String studentID = rs.getString("studentID");
                String lastName = rs.getString("lastName");
                String firstName = rs.getString("firstName");
                String dateOfBirth = rs.getString("dateOfBirth");
                Boolean gender = rs.getBoolean("gender");
                String ID = rs.getString("ID");
                String phoneNumber = rs.getString("phoneNumber");
                String email = rs.getString("email");
                String className = rs.getString("className");
                String address = rs.getString("address");
                String note = rs.getString("notes");
                Boolean status = rs.getBoolean("status");

                Student student = new Student(studentID, lastName, firstName, dateOfBirth, gender, ID, phoneNumber, email, className, address, note, status);

                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }
}
