public class Initialization9 {
    //Hàm tạo 1
    public Initialization9() {
        this("Nguyen dep trai");
    } 
    
    //Hàm tạo 2
    public Initialization9(String message) {
        System.out.println("Ham tao 2");
        System.out.println(message);
    } 

    public static void main(String[] args) {
        Initialization9 obj = new Initialization9();
    }
}
