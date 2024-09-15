package com.example;
import java.util.Arrays;

public class DiscreteSignal extends Signal{

    private int[] x; // Tín hiệu đầu vào
    private int[] y; // Tín hiệu đầu ra
    private int n;   


    public DiscreteSignal(int[] x, int n) {
        this.x = x;
        this.n = n;
        this.y = new int[x.length];
        calculateY();
    }

    private int delta(int n) {
        return (n == 0) ? 1 : 0;
    }

    private void calculateY() {
        for (int k = 0; k < x.length; k++) {
            y[k] = x[k] * delta(n - k); 
        }
    }

    
    public void displaySignal() {
        System.out.println("Tín hiệu đầu vào x: " + Arrays.toString(x));
        System.out.println("Tín hiệu đầu ra y: " + Arrays.toString(y));
    }

    @Override
    public void displaySignalType() {
        System.out.println("Tín hiệu rời rạc.");
    }
}
