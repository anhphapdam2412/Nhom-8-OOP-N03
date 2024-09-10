package com.ktgk;

public class Main {

    public static void main(String[] args) {
        int[] inputSignal = {1, 2, 3, 4, 5};
        int n = 2;

        DiscreteSignal discreteSignal = new DiscreteSignal(inputSignal, n);
        discreteSignal.displaySignalType();
        discreteSignal.displaySignal(); 

        Radar radar = new Radar();  // Tạo đối tượng Radar
        n = 4;  // Giá trị n theo yêu cầu
        radar.analyzeSignal(n);  // Phân tích tín hiệu và đưa ra kết quả
    }
}