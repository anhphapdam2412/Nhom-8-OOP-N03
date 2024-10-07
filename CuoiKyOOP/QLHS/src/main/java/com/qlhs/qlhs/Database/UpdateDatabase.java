package com.qlhs.qlhs.Database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import static com.qlhs.qlhs.Database.DatabaseConnection.connect;

public class UpdateDatabase {

    // Update student information
    public static void updateStudentInfo(String studentID, String lastName, String firstName, String dateOfBirth,
                                         Boolean gender, String ID, String phoneNumber,
                                         String email, String className, String address, String notes, String status,
                                         String query, String query2) {
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;

        try {
            // Connect to the database
            connection = connect();
            connection.setAutoCommit(false);  // Begin transaction

            // Update student information
            preparedStatement1 = connection.prepareStatement(query);
            preparedStatement1.setString(1, lastName);
            preparedStatement1.setString(2, firstName);
            preparedStatement1.setDate(3, Date.valueOf(dateOfBirth));
            preparedStatement1.setBoolean(4, gender);
            System.out.println(gender);
            preparedStatement1.setString(5, ID);
            preparedStatement1.setString(6, phoneNumber);
            preparedStatement1.setString(7, email);
            preparedStatement1.setString(8, className);
            preparedStatement1.setString(9, address);
            preparedStatement1.setString(10, notes);
            preparedStatement1.setString(12, studentID);
            preparedStatement1.setBoolean(11, Boolean.parseBoolean(status));

            int rowsAffected1 = preparedStatement1.executeUpdate();
            System.out.println(rowsAffected1 + " record(s) updated in table 1.");

            // Execute script2
            if (!Objects.equals(query2, "")) {
                preparedStatement2 = connection.prepareStatement(query2);
                preparedStatement2.setString(1, studentID);
                int rowsAffected2 = preparedStatement2.executeUpdate();
                System.out.println(rowsAffected2 + " record(s) updated in table 1.");
            }
            // Commit if both succeed
            connection.commit();
        } catch (SQLException e) {
            // Rollback if there is an error
            try {
                connection.rollback();
                System.err.println("Transaction rolled back due to error: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                System.err.println("Error during rollback: " + rollbackEx.getMessage());
            }
        }
    }

    // Delete student
    public static void deleteStudentInfo(String studentID, String status, String query) {
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;

        try {
            // Connect to the database
            connection = connect();
            connection.setAutoCommit(false);  // Begin transaction

            // Delete student
            preparedStatement1 = connection.prepareStatement(query);
            preparedStatement1.setBoolean(1, Boolean.parseBoolean(status));

            preparedStatement1.setString(2, studentID);

            int rowsAffected1 = preparedStatement1.executeUpdate();
            System.out.println(rowsAffected1 + " record(s) updated in table 2.");
            // Commit
            connection.commit();
        } catch (SQLException e) {
            // Rollback if there is an error
            try {
                connection.rollback();
                System.err.println("Transaction rolled back due to error: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                System.err.println("Error during rollback: " + rollbackEx.getMessage());
            }
        }
    }

    // Update grades
    public static void updateGrades(String studentID, String literature, String math, String physics, String chemistry,
                                    String biology, String history, String geography, String citizenship,
                                    String technology, String informatics, String physicalEducation, String foreignLanguage,
                                    String foreignLanguageCode, String conduct, String gradeNote, String query) {
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;

        try {
            // Connect to the database
            connection = connect();
            connection.setAutoCommit(false);  // Begin transaction

            // Update student grades
            preparedStatement1 = connection.prepareStatement(query);
            preparedStatement1.setString(1, literature);
            preparedStatement1.setString(2, math);
            preparedStatement1.setString(3, physics);
            preparedStatement1.setString(4, chemistry);
            preparedStatement1.setString(5, biology);
            preparedStatement1.setString(6, history);
            preparedStatement1.setString(7, geography);
            preparedStatement1.setString(8, citizenship);
            preparedStatement1.setString(9, technology);
            preparedStatement1.setString(10, informatics);
            preparedStatement1.setString(11, physicalEducation);
            preparedStatement1.setString(12, foreignLanguage);
            preparedStatement1.setString(13, foreignLanguageCode);
            preparedStatement1.setString(14, conduct);
            preparedStatement1.setString(15, gradeNote);
            preparedStatement1.setString(16, studentID);

            int rowsAffected1 = preparedStatement1.executeUpdate();
            System.out.println(rowsAffected1 + " record(s) updated in table 2.");
            // Commit
            connection.commit();
        } catch (SQLException e) {
            // Rollback if there is an error
            try {
                connection.rollback();
                System.err.println("Transaction rolled back due to error: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                System.err.println("Error during rollback: " + rollbackEx.getMessage());
            }
        }
    }
}
