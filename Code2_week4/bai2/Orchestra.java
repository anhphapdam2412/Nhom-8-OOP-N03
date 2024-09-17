package bai2;

public class Orchestra {
    public static void main(String[] args) {
        Instrument[] orchestra = {
            new Wind(),
            new Percussion(),
            new Stringed(),
            new Woodwind(),
            new Brass()
        };
        
        for (Instrument i : orchestra) {
            i.play();
            System.out.println(i.what());
            i.adjust();
            System.out.println();
        }
    }
}

