public class InitializationAndCleanupExercise2 {
    // Trường được khởi tạo tại định nghĩa
    String s1 = "Khoi tao tai dinh nghia";

    String s2;

    public void Constructor(String value) {
        this.s2 = value;
    }

    public static void main(String[] args) {
        InitializationAndCleanupExercise2 obj = new InitializationAndCleanupExercise2();
        System.out.println(obj.s1); // Khoi tao tai dinh nghia
        obj.s2 = "Khoi tao tai constructor";
        System.out.println(obj.s2); // Gia tri duoc truyen vao constructor
    }
}
