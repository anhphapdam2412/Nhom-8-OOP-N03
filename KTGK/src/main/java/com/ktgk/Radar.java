package com.ktgk;

public class Radar {
    // 1. Phương thức tính toán tín hiệu rời rạc theo công thức [2]
    public double discreteSignal(int n) {
        if (n >= 0 && n <= 15) {
            return 1 - (double) n / 15;  // Công thức: 1 - n/15 với 0 <= n <= 15
        } else {
            return 0;  // Giá trị 0 khi n != 0 và không thuộc khoảng [0, 15]
        }
    }

    // 2. Phương thức phân tích tín hiệu với giá trị n và xuất ra kết quả
    public void analyzeSignal(int n) {
        double result = discreteSignal(n);  // Tính toán giá trị tín hiệu rời rạc
        System.out.println("Output of discrete signal y(" + n + ") = " + result);  // Hiển thị kết quả
    }
}