package com.qlhs.qlhs;

public class kiemTraDuLieuNhap {
    static boolean isValidSoDienThoai(String phoneNumber) {
        return phoneNumber.substring(0, 1).matches("0") && phoneNumber.matches("\\d{10}");
    }

    static boolean isValidMaDinhDanh(String maDinhDanh) {
        return maDinhDanh.matches("\\d{12}");
    }

    public static boolean isValidTen(String ten) {
        return ten.matches("[a-zA-Z\\s]+");
    }
}
