
package bai21;
public class InterfaceTest {
    public static void main(String[] args) {
        // Tạo một instance của lớp triển khai
        MyImplementation myImpl = new MyImplementation();
        
        // Sử dụng lớp lồng ghép để gọi các phương thức
        MyInterface.NestedClass.callMethods(myImpl);
    }
}

    