package com.qlhs.qlhs.Controller;

import javafx.scene.control.Label;

public class DataValidation {
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d+");
    }

    public static boolean isValidID(String ID) {
        return ID.matches("\\d{12}");
    }

    public static boolean isValidName(String name) {
        return name.matches("[\\p{L}\\s]+");
    }

    public static boolean isValidGrade(String grade) {
        try {
            double value = Float.parseFloat(grade);

            return (value >= 0.0 && value <= 10.0);
        } catch (NumberFormatException e) {
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
        return !sex.equals("false");
    }

    @FunctionalInterface
    public interface Validator {
        boolean isValid(String text);
    }
}
