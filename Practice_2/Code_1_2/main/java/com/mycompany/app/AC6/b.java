package AC6;

public class b {
    
    public void c() {
		a obj = new a();
		
		System.out.println("Before Manipulation: " + obj.protectedData);
        obj.protectedData = 20; // Thay đổi giá trị
        System.out.println("After Manipulation: " + obj.protectedData);
	}
	
	public static void main(String[] args) {
		b obj = new b();
		obj.c();//Dữ liệu đã bị thay đổi
	}
}
