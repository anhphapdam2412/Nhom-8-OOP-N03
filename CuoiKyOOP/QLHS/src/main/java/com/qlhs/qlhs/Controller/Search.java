package com.qlhs.qlhs.Controller;

import com.qlhs.qlhs.Database.GradeReportDAO;
import com.qlhs.qlhs.Database.StudentDAO;
import com.qlhs.qlhs.Model.Grade;
import com.qlhs.qlhs.Model.Student;
import javafx.collections.ObservableList;

import java.util.Objects;

public class Search {

    static ObservableList<Student> filter(String input) {
        ObservableList<Student> filteredStudents;
        ObservableList<Student> allStudents = StudentDAO.getStudentList().filtered(student -> Objects.equals(student.getStatus(), "1"));

        // Split search terms by comma
        String[] searchTerms = input.split(",");

        filteredStudents = allStudents.filtered(student -> {
            // Default to true; will check each condition
            for (String term : searchTerms) {
                String trimmedTerm = term.trim();

                // Check for operator in the condition
                String operator = null;
                if (trimmedTerm.contains(">=")) {
                    operator = ">=";
                } else if (trimmedTerm.contains("<=")) {
                    operator = "<=";
                } else if (trimmedTerm.contains(">")) {
                    operator = ">";
                } else if (trimmedTerm.contains("<")) {
                    operator = "<";
                } else if (trimmedTerm.contains("=")) {
                    operator = "=";
                }

                // Split field and value
                String[] parts = trimmedTerm.split("[><=]+");
                if (parts.length == 2) {
                    String fieldName = parts[0].trim().toLowerCase(); // Field to search
                    String searchValue = parts[1].trim(); // Search value

                    // Check condition based on operator
                    boolean matches = false;

                    switch (fieldName) {
                        case "tên":
                        case "ten":
                        case "name":
                        case "t":
                        case "n":
                        case "last name":
                        case "lname":
                        case "ln":
                            matches = compareString(student.getLastName(), searchValue, operator);
                            break;
                        case "họ đệm":
                        case "ho dem":
                        case "hodem":
                        case "hd":
                        case "surname":
                        case "sname":
                        case "sn":
                        case "middle name":
                        case "mname":
                        case "mn":
                        case "first name":
                        case "fname":
                        case "fn":
                            matches = compareString(student.getFirstName(), searchValue, operator);
                            break;
                        case "mã học sinh":
                        case "ma hoc sinh":
                        case "mahocsinh":
                        case "mhs":
                        case "mahs":
                        case "student id":
                        case "studendid":
                        case "sid":
                            matches = compareString(student.getStudentID(), searchValue, operator);
                            break;

                        case "số điện thoại":
                        case "sodienthoai":
                        case "so dien thoai":
                        case "sdt":
                        case "phonenumber":
                        case "pn":
                            matches = compareString(student.getPhoneNumber(), searchValue, operator);
                            break;
                        case "email":
                        case "em":
                            if (operator.equals("=") && searchValue.isEmpty()) {
                                // Check if email field is empty
                                matches = (student.getEmail() == null || student.getEmail().isEmpty());
                            } else {
                                // Normal comparison if searchValue is not empty
                                matches = compareString(student.getEmail(), searchValue, operator);
                            }
                            break;
                        case "lớp":
                        case "lop":
                        case "l":
                        case "class":
                        case "cl":
                            matches = compareString(student.getLastName(), searchValue, operator);
                            break;
                        case "địa chỉ":
                        case "dia chi":
                        case "diachi":
                        case "dc":
                        case "address":
                        case "add":
                            matches = compareString(student.getAddress(), searchValue, operator);
                            break;
                        case "ghi chú":
                        case "ghi chu":
                        case "ghichu":
                        case "gc":
                        case "note":
                            matches = compareString(student.getNotes(), searchValue, operator);
                            break;
                        case "ngày sinh":
                        case "ngay sinh":
                        case "ngaysinh":
                        case "ns":
                        case "birth":
                        case "birth day":
                        case "bd":
                            matches = compareString(student.getDateOfBirth(), searchValue, operator);
                            break;
                        case "giới tính":
                        case "gioi tinh":
                        case "gioitinh":
                        case "gt":
                        case "sex":
                            matches = compareString(student.getGender(),
                                    (searchValue.equals("1") || searchValue.equals("nam")) ? "1" :
                                            (searchValue.equals("0") || searchValue.equals("nu")) ? "0" : null,
                                    operator);
                            break;
                        case "mã định danh":
                        case "ma dinh danh":
                        case "madinhdanh":
                        case "mdd":
                        case "id":
                            matches = compareString(student.getID(), searchValue, operator);
                            break;
                        default:
                            matches = false;
                            break;
                    }

                    if (!matches) {
                        return false; // If one condition does not match, exclude this student
                    }
                } else {
                    // Free search if no operator
                    boolean matches = (student.getStudentID() != null && student.getStudentID().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (student.getFirstName() != null && student.getFirstName().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (student.getLastName() != null && student.getLastName().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (student.getPhoneNumber() != null && student.getPhoneNumber().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (student.getEmail() != null && student.getEmail().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (student.getLastName() != null && student.getLastName().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (student.getAddress() != null && student.getAddress().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (student.getNotes() != null && student.getNotes().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (student.getDateOfBirth() != null && String.valueOf(student.getDateOfBirth()).contains(trimmedTerm)) ||
                            (student.getGender() != null && student.getGender().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (student.getID() != null && student.getID().toLowerCase().contains(trimmedTerm.toLowerCase()));

                    if (!matches) {
                        return false; // If one condition does not match, exclude this student
                    }
                }
            }

            return true; // If all conditions match
        });

        return filteredStudents;
    }

    // Helper function to compare string values with operator
    private static boolean compareString(String actualValue, String searchValue, String operator) {
        if (actualValue == null || searchValue == null) return false;

        // Get the length of the string to compare
        int searchLength = searchValue.length();

        // Truncate actualValue based on the length of searchValue for comparison
        String truncatedActualValue = actualValue.length() >= searchLength
                ? actualValue.substring(0, searchLength)  // Truncate if length is sufficient
                : actualValue;  // If actualValue is shorter, compare the whole string

        // For '=', perform relative search (compare by corresponding characters)
        if ("=".equals(operator)) {
            return truncatedActualValue.toLowerCase().contains(searchValue.toLowerCase());
        }

        // Compare corresponding characters
        int compare = truncatedActualValue.compareToIgnoreCase(searchValue);
        return checkCondition(operator, compare);
    }

    // Check operator and comparison value
    private static boolean checkCondition(String operator, int compare) {
        switch (operator) {
            case ">":
                return compare > 0;
            case "<":
                return compare < 0;
            case ">=":  // Add condition for '>='
                return compare >= 0;
            case "<=":  // Add condition for '<='
                return compare <= 0;
            default:
                return false;
        }
    }
}
