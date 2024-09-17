package bai2;
abstract class Instrument {
    // Phương thức trừu tượng
    abstract void play();
    abstract void adjust();
    
    // Phương thức không trừu tượng
    String what() {
        return "Nhạc cụ không xác định";
    }
}
