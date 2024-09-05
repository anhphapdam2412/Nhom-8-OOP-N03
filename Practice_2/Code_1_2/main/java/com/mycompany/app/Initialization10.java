public class Initialization10 {    
    public void finalize() {
        System.out.println("them an com");
    }
    public static void main(String[] args) {
        Initialization10 obj = new Initialization10();
        obj.finalize();
    }
}
