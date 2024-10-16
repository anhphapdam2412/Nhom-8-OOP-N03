package com.qlhs.qlhs.Database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import static com.qlhs.qlhs.Database.DatabaseConnection.connect;

public class UpdateDatabase {

    static Connection connection = null;
    static PreparedStatement preparedStatement = null;

    // Cập nhật thông tin học sinh
    public static void updateStudentInfo(String studentId, String lastName, String firstName, String dateOfBirth,
                                         Boolean gender, String Id, String phoneNumber,
                                         String email, String className, String address, String notes, Boolean status,
                                         String query) {

        try {
            // Kết nối tới DB
            connection = connect();
            connection.setAutoCommit(false);

            // Chuẩn bị dữ liệu
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setDate(3, Date.valueOf(dateOfBirth));
            preparedStatement.setBoolean(4, gender);
            preparedStatement.setString(5, Id);
            preparedStatement.setString(6, phoneNumber);
            preparedStatement.setString(7, email);
            preparedStatement.setString(8, className);
            preparedStatement.setString(9, address);
            preparedStatement.setString(10, notes);
            preparedStatement.setString(12, studentId);
            preparedStatement.setBoolean(11, status);

            int rowsAffected1 = preparedStatement.executeUpdate();
            System.out.println(rowsAffected1 + " record(s) updated in table student.");

            connection.commit();
        } catch (SQLException e) {
            // Rollback nếu lỗi
            try {
                connection.rollback();
                System.err.println("Transaction rolled back due to error: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                System.err.println("Error during rollback: " + rollbackEx.getMessage());
            }
        }
    }

    // Thêm bảng điểm cho học sinh
    public static void insertGrade(String studentId, String query){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Kết nối tới DB
            connection = connect();
            connection.setAutoCommit(false);

            if (!Objects.equals(query, "")) {
                // Chuẩn bị dữ liệu
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, studentId);

                int rowsAffected2 = preparedStatement.executeUpdate();
                System.out.println(rowsAffected2 + " record(s) updated in table grade.");
            }

            connection.commit();
        } catch (SQLException e) {
            // Rollback nếu lỗi
            try {
                connection.rollback();
                System.err.println("Transaction rolled back due to error: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                System.err.println("Error during rollback: " + rollbackEx.getMessage());
            }
        }
    }

    // Xóa học sinh
    public static void deleteStudentInfo(String studentId, String status, String query) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Kết nối tới DB
            connection = connect();
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, Boolean.parseBoolean(status));
            preparedStatement.setString(2, studentId);

            int rowsAffected1 = preparedStatement.executeUpdate();
            System.out.println(rowsAffected1 + " record(s) updated in table student.");

            connection.commit();
        } catch (SQLException e) {
            // Rollback nếu lỗi
            try {
                connection.rollback();
                System.err.println("Transaction rolled back due to error: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                System.err.println("Error during rollback: " + rollbackEx.getMessage());
            }
        }
    }

    // Cập nhật bảng điểm
    public static void updateGrades(String studentId, Float literature, Float math, Float physics, Float chemistry,
                                    Float biology, Float history, Float geography, Float civicEdu,
                                    Float technology, Float informatics, String physicalEdu, Float foreignLanguage,
                                    String foreignLanguageCode, String conduct, String gradeNote, String query) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Kết nối tới DB
            connection = connect();
            connection.setAutoCommit(false);

            // Chuẩn bị dữ liệu
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFloat(1, literature);
            preparedStatement.setFloat(2, math);
            preparedStatement.setFloat(3, physics);
            preparedStatement.setFloat(4, chemistry);
            preparedStatement.setFloat(5, biology);
            preparedStatement.setFloat(6, history);
            preparedStatement.setFloat(7, geography);
            preparedStatement.setFloat(8, civicEdu);
            preparedStatement.setFloat(9, technology);
            preparedStatement.setFloat(10, informatics);
            preparedStatement.setString(11, physicalEdu);
            preparedStatement.setFloat(12, foreignLanguage);
            preparedStatement.setString(13, foreignLanguageCode);
            preparedStatement.setString(14, conduct);
            preparedStatement.setString(15, gradeNote);
            preparedStatement.setString(16, studentId);

            int rowsAffected1 = preparedStatement.executeUpdate();
            System.out.println(rowsAffected1 + " record(s) updated in table grade.");

            connection.commit();
        } catch (SQLException e) {
            // Rollback nếu lỗi
            try {
                connection.rollback();
                System.err.println("Transaction rolled back due to error: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                System.err.println("Error during rollback: " + rollbackEx.getMessage());
            }
        }
    }
}
