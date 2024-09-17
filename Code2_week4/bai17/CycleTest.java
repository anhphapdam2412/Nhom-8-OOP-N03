public class CycleTest {
    public static void main(String[] args) {
        // Tạo mảng chứa các đối tượng kiểu Cycle
        Cycle[] cycles = {
            new Unicycle(),
            new Bicycle(),
            new Tricycle()
        };

        // Thử gọi phương thức balance() trên từng đối tượng
        for (Cycle c : cycles) {
            c.ride(); // Tất cả các lớp đều có thể gọi ride()

            // Thử gọi balance() sẽ không thành công vì không có trong lớp Cycle
            // c.balance(); // Lỗi biên dịch vì balance() không tồn tại trong lớp Cycle
        }

        System.out.println("\nDowncasting and calling balance():");
        // Downcast từng đối tượng và thử gọi balance()
        ((Unicycle) cycles[0]).balance(); // Thành công vì cycles[0] là Unicycle
        ((Bicycle) cycles[1]).balance();  // Thành công vì cycles[1] là Bicycle

        // ((Bicycle) cycles[2]).balance(); // Lỗi tại runtime vì cycles[2] là Tricycle
    }
}

