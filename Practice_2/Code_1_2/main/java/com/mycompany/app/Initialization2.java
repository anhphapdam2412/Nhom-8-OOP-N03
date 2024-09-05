public class Initialization2 {
    String s1 = "Khoi tao tai dinh nghia";
    String s2;
    public void Constructor(String value) {
        this.s2 = value;
    }
    public static void main(String[] args) {
        Initialization2 obj = new Initialization2();
        System.out.println(obj.s1); 
        obj.s2 = "Khoi tao tai constructor";
        System.out.println(obj.s2); 
    }
}
