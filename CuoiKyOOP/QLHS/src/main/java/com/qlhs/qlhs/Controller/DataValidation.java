package com.qlhs.qlhs.Controller;

import javafx.scene.control.Label;

public class DataValidation {
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.substring(0, 1).matches("0") && phoneNumber.matches("\\d{10}");
    }

    public static boolean isValidID(String identifier) {
        return identifier.matches("\\d{12}");
    }

    public static boolean isValidName(String name) {
        return name.matches("[\\p{L}\\s]+");
    }

    public static boolean isValidGrade(String grade) {
        try {
            // Convert string to double
            double value = Double.parseDouble(grade);

            // Check if the value is within the range from 0.0 to 10.0
            return (value >= 0.0 && value <= 10.0);
        } catch (NumberFormatException e) {
            // In case the string is not a valid number
            return false;
        }
    }

    public static boolean isValidStudentID(String studentID) {
        return !studentID.equals("23xxxxxx");
    }

    public static boolean isValidComboBox(String string) {
        return string != null;
    }

    public static boolean isValidClass(String className) {
        return className != null;
    }

    public static boolean isValidBirthOfDate(String birthDate) {
        return !birthDate.equals("null");
    }

    public static boolean validateField(String text, Label label, Validator validator) {
        if (text != null && !text.isEmpty()) {
            if (validator.isValid(text)) {
                label.setStyle("-fx-text-fill: #ffffff;");
                return true;
            } else {
                label.setStyle("-fx-text-fill: #ff6363;");
                return false;
            }
        } else {
            label.setStyle("-fx-text-fill: #ff6363;");
            return false;
        }
    }

    public static boolean isValidSex(String sex) {
        return !sex.equals("falsefalse");
    }

    @FunctionalInterface
    public interface Validator {
        boolean isValid(String text);
    }
}
