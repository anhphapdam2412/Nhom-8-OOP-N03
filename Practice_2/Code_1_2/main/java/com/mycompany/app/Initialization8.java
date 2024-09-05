public class Initialization8 {

    public void method1() {
        method2();
        this.method2();
    }

    public void method2() {
        System.out.println("method 2");
    }
    public static void main(String[] args) {
        Initializatione8 obj = new Initialization8();
        obj.method1();
    }
}
