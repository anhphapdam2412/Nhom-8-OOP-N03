package bai2;

class Stringed extends Instrument {
    void play() {
        System.out.println("Chơi nhạc cụ dây");
    }
    
    void adjust() {
        System.out.println("Điều chỉnh nhạc cụ dây");
    }
    
    @Override
    String what() {
        return "Nhạc cụ dây";
    }
}

