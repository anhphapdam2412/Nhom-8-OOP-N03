package com.qlhs.qlhs.Database;

import com.qlhs.qlhs.Model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentDAO {

    public static ObservableList<Student> getStudentList() {
        ObservableList<Student> studentList = FXCollections.observableArrayList();

        String query = "SELECT * FROM student";

        try (Connection conn = DatabaseConnection.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String studentID = rs.getString("studentID");
                String lastName = rs.getString("lastName");
                String firstName = rs.getString("firstName");
                String dateOfBirth = rs.getString("dateOfBirth");
                String gender = rs.getString("gender");
                String ID = rs.getString("ID");
                String phoneNumber = rs.getString("phoneNumber");
                String email = rs.getString("email");
                String className = rs.getString("className");
                String address = rs.getString("address");
                String note = rs.getString("notes");
                String status = rs.getString("status");

                Student student = new Student(studentID, lastName, firstName, dateOfBirth, gender, ID, phoneNumber, email, className, address, note, status);

                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }
}
