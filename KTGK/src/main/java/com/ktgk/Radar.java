package com.ktgk;

public class Radar {
    // Phương thức tính toán theo công thức [2]
    public double discreteSignal(int n) {
        if (n >= 0 && n <= 15) {
            return 1 - (double) n / 15;
        } else {
            return 0;
        }
    }

    // Thực hiện phép tính và đưa ra kết quả
    public void analyzeSignal(int n) {
        double result = discreteSignal(n);
        System.out.println("Đầu ra của tín hiệu rời rạc y(" + n + ") = " + result);
    }
}