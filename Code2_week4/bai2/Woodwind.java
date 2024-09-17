package bai2;

class Woodwind extends Wind {
    @Override
    void play() {
        System.out.println("Chơi nhạc cụ gió gỗ");
    }
    
    @Override
    String what() {
        return "Nhạc cụ gió gỗ";
    }
}

