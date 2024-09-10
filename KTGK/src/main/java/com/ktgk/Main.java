package com.ktgk;

public class Main {

    public static void main(String[] args) {
        int[] inputSignal = {1, 2, 3, 4, 5};
        int n = 2;

        // Test câu 1
        DiscreteSignal discreteSignal = new DiscreteSignal(inputSignal, n);
        discreteSignal.displaySignalType();
        discreteSignal.displaySignal(); 

        // Test câu 2
        Radar radar = new Radar();
        n = 4;
        radar.analyzeSignal(n);
    }
}