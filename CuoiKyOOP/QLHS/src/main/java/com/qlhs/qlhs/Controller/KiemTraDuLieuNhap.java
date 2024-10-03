package com.qlhs.qlhs.Controller;

import javafx.scene.control.Label;

public class KiemTraDuLieuNhap {
    public static boolean isValidSoDienThoai(String phoneNumber) {
        return phoneNumber.substring(0, 1).matches("0") && phoneNumber.matches("\\d{10}");
    }

    public static boolean isValidMaDinhDanh(String maDinhDanh) {
        return maDinhDanh.matches("\\d{12}");
    }

    public static boolean isValidTen(String ten) {
        return ten.matches("[\\p{L}\\s]+");
    }

    public static boolean isValidDiem(String diem) {
        try {
            // Chuyển đổi chuỗi thành số thực
            double value = Double.parseDouble(diem);

            // Kiểm tra xem giá trị có nằm trong khoảng từ 0.0 đến 10.0 không
            return (value >= 0.0 && value <= 10.0);
        } catch (NumberFormatException e) {
            // Trường hợp chuỗi không phải là một số hợp lệ
            return false;
        }
    }
    public static boolean isValidMaHS(String maHS) {
        return !maHS.equals("23xxxxxx");
    }
    public static boolean isValidComboBox(String string) {
        return string != null;
    }
    public static boolean isValidLop(String lop) {
        return lop != null;
    }
    public static boolean isValidNgaySinh(String ngaySinh) {
        return ngaySinh != "null";
    }

    public static boolean validateField(String text, Label label, Validator validator) {
        if (text!=null && !text.isEmpty()) {
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

    @FunctionalInterface
    public interface Validator {
        boolean isValid(String text);
    }
}
