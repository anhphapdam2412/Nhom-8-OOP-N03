package com.qlhs.qlhs.Controller;

public class KiemTraDuLieuNhap {
    public static boolean isValidSoDienThoai(String phoneNumber) {
        return phoneNumber.substring(0, 1).matches("0") && phoneNumber.matches("\\d{10}");
    }

    public static boolean isValidMaDinhDanh(String maDinhDanh) {
        return maDinhDanh.matches("\\d{12}");
    }

    public static boolean isValidTen(String ten) {
        return ten.matches("[a-zA-Z\\s]+");
    }

    public static boolean isValidDiem(String diem) {
        try {
            // Chuyển đổi chuỗi thành số thực
            double value = Double.parseDouble(diem);

            // Kiểm tra xem giá trị có nằm trong khoảng từ 0.0 đến 10.0 không
            return value >= 0.0 && value <= 10.0;
        } catch (NumberFormatException e) {
            // Trường hợp chuỗi không phải là một số hợp lệ
            return false;
        }
    }
}
