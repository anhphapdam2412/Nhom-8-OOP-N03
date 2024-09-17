package bai2;
class Percussion extends Instrument {
    void play() {
        System.out.println("Chơi nhạc cụ gõ");
    }
    
    void adjust() {
        System.out.println("Điều chỉnh nhạc cụ gõ");
    }
    
    @Override
    String what() {
        return "Nhạc cụ gõ";
    }
}

