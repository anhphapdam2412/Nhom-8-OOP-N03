package bai2;

class Brass extends Wind {
    @Override
    void play() {
        System.out.println("Chơi nhạc cụ đồng");
    }
    
    @Override
    String what() {
        return "Nhạc cụ đồng";
    }
}

