package bai2;
class Wind extends Instrument {
    void play() {
        System.out.println("Chơi nhạc cụ gió");
    }
    
    void adjust() {
        System.out.println("Điều chỉnh nhạc cụ gió");
    }
    
    @Override
    String what() {
        return "Nhạc cụ gió";
    }
}
