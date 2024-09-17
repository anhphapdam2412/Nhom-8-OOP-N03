
interface MyInterface {
    void method1();
    void method2();
    
    // Lớp lồng ghép trong interface
    class NestedClass {
        public static void callMethods(MyInterface instance) {
            System.out.println("Calling method1:");
            instance.method1(); // Gọi method1() của instance
            
            System.out.println("Calling method2:");
            instance.method2(); // Gọi method2() của instance
        }
    }
}

