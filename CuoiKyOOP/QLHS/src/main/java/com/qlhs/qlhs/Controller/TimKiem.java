package com.qlhs.qlhs.Controller;

import com.qlhs.qlhs.Database.BangDiemDAO;
import com.qlhs.qlhs.Database.HocSinhDAO;
import com.qlhs.qlhs.Model.BangDiem;
import com.qlhs.qlhs.Model.HocSinh;
import javafx.collections.ObservableList;

import java.util.Objects;

public class TimKiem {

    static ObservableList<HocSinh> boLoc(String input) {
        ObservableList<HocSinh> dsHocSinhDaLoc;
        ObservableList<HocSinh> allStudents = HocSinhDAO.getDSHocSinh().filtered(hocSinh -> Objects.equals(hocSinh.getTrangThai(), "1"));

        // Tách các điều kiện tìm kiếm bằng dấu phẩy
        String[] searchTerms = input.split(",");

        dsHocSinhDaLoc = allStudents.filtered(hocSinh -> {
            // Mặc định là true, sẽ kiểm tra từng điều kiện
            for (String term : searchTerms) {
                String trimmedTerm = term.trim();

                // Kiểm tra điều kiện có toán tử
                String operator = null;
                if (trimmedTerm.contains(">=")) {
                    operator = ">=";
                } else if (trimmedTerm.contains("<=")) {
                    operator = "<=";
                } else if (trimmedTerm.contains(">")) {
                    operator = ">";
                } else if (trimmedTerm.contains("<")) {
                    operator = "<";
                } else if (trimmedTerm.contains("=")) {
                    operator = "=";
                }

                // Tách trường và giá trị
                String[] parts = trimmedTerm.split("[><=]+");
                if (parts.length == 2) {
                    String fieldName = parts[0].trim().toLowerCase(); // Trường tìm kiếm
                    String searchValue = parts[1].trim(); // Giá trị tìm kiếm

                    // Kiểm tra điều kiện dựa trên toán tử
                    boolean matches = false;

                    switch (fieldName) {
                        case "tên":
                        case "ten":
                        case "name":
                        case "t":
                        case "n":
                        case "last name":
                        case "lname":
                        case "ln":
                            matches = compareString(hocSinh.getTen(), searchValue, operator);
                            break;
                        case "họ đệm":
                        case "ho dem":
                        case "hodem":
                        case "hd":
                        case "surname":
                        case "sname":
                        case "sn":
                        case "middle name":
                        case "mname":
                        case "mn":
                        case "first name":
                        case "fname":
                        case "fn":
                            matches = compareString(hocSinh.getHoDem(), searchValue, operator);
                            break;
                        case "mã học sinh":
                        case "ma hoc sinh":
                        case "mahocsinh":
                        case "mhs":
                        case "mahs":
                        case "studend id":
                        case "studendid":
                        case "sid":
                            matches = compareString(hocSinh.getMaHS(), searchValue, operator);
                            break;

                        case "số điện thoại":
                        case "sodienthoai":
                        case "so dien thoai":
                        case "sdt":
                        case "phonenumber":
                        case "pn":
                            matches = compareString(hocSinh.getSdt(), searchValue, operator);
                            break;
                        case "email":
                        case "em":
                            if (operator.equals("=") && searchValue.isEmpty()) {
                                // Kiểm tra nếu trường email rỗng
                                matches = (hocSinh.getEmail() == null || hocSinh.getEmail().isEmpty());
                            } else {
                                // So sánh bình thường nếu searchValue không rỗng
                                matches = compareString(hocSinh.getEmail(), searchValue, operator);
                            }
                            break;
                        case "lớp":
                        case "lop":
                        case "l":
                        case "class":
                        case "cl":
                            matches = compareString(hocSinh.getLop(), searchValue, operator);
                            break;
                        case "địa chỉ":
                        case "dia chi":
                        case "diachi":
                        case "dc":
                        case "address":
                        case "add":
                            matches = compareString(hocSinh.getDiaChi(), searchValue, operator);
                            break;
                        case "ghi chú":
                        case "ghi chu":
                        case "ghichu":
                        case "gc":
                        case "note":
                            matches = compareString(hocSinh.getGhiChuTT(), searchValue, operator);
                            break;
                        case "ngày sinh":
                        case "ngay sinh":
                        case "ngaysinh":
                        case "ns":
                        case "birth":
                        case "birth day":
                        case "bd":
                            matches = compareString(hocSinh.getNgaySinh(), searchValue, operator);
                            break;
                        case "giới tính":
                        case "gioi tinh":
                        case "gioitinh":
                        case "gt":
                        case "sex":
                            matches = compareString(hocSinh.getGioiTinh(),
                                    (searchValue.equals("1") || searchValue.equals("nam")) ? "1" :
                                            (searchValue.equals("0") || searchValue.equals("nu")) ? "0" : null,
                                    operator);
                            break;
                        case "mã định danh":
                        case "ma dinh danh":
                        case "madinhdanh":
                        case "mdd":
                        case "id":
                            matches = compareString(hocSinh.getMaDinhDanh(), searchValue, operator);
                            break;
                        default:
                            matches = false;
                            break;
                    }

                    if (!matches) {
                        return false; // Nếu một điều kiện không khớp, loại bỏ học sinh này
                    }
                } else {
                    // Tìm kiếm tự do nếu không có toán tử
                    boolean matches = (hocSinh.getMaHS() != null && hocSinh.getMaHS().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (hocSinh.getHoDem() != null && hocSinh.getHoDem().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (hocSinh.getTen() != null && hocSinh.getTen().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (hocSinh.getSdt() != null && hocSinh.getSdt().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (hocSinh.getEmail() != null && hocSinh.getEmail().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (hocSinh.getLop() != null && hocSinh.getLop().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (hocSinh.getDiaChi() != null && hocSinh.getDiaChi().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (hocSinh.getGhiChuTT() != null && hocSinh.getGhiChuTT().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (hocSinh.getNgaySinh() != null && String.valueOf(hocSinh.getNgaySinh()).contains(trimmedTerm)) ||
                            (hocSinh.getGioiTinh() != null && hocSinh.getGioiTinh().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (hocSinh.getMaDinhDanh() != null && hocSinh.getMaDinhDanh().toLowerCase().contains(trimmedTerm.toLowerCase()));

                    if (!matches) {
                        return false; // Nếu một điều kiện không khớp, loại bỏ học sinh này
                    }
                }
            }

            return true; // Nếu tất cả các điều kiện đều khớp
        });

        return dsHocSinhDaLoc;
    }

    // Hàm hỗ trợ so sánh giá trị kiểu chuỗi với toán tử
    private static boolean compareString(String actualValue, String searchValue, String operator) {
        if (actualValue == null || searchValue == null) return false;

        // Lấy độ dài của chuỗi cần so sánh
        int searchLength = searchValue.length();

        // Cắt chuỗi actualValue theo độ dài của searchValue để so sánh
        String truncatedActualValue = actualValue.length() >= searchLength
                ? actualValue.substring(0, searchLength)  // Cắt chuỗi nếu độ dài đủ
                : actualValue;  // Nếu actualValue ngắn hơn thì so sánh toàn bộ

        // Với dấu '=', thực hiện tìm kiếm tương đối (so sánh theo số ký tự tương ứng)
        if ("=".equals(operator)) {
            return truncatedActualValue.toLowerCase().contains(searchValue.toLowerCase());
        }

        // So sánh số ký tự tương ứng
        int compare = truncatedActualValue.compareToIgnoreCase(searchValue);
        return checkCondition(operator, compare);
    }

    // Hàm kiểm tra toán tử và giá trị so sánh
    private static boolean checkCondition(String operator, int compare) {
        switch (operator) {
            case ">":
                return compare > 0;
            case "<":
                return compare < 0;
            case ">=":  // Thêm điều kiện cho '>='
                return compare >= 0;
            case "<=":  // Thêm điều kiện cho '<='
                return compare <= 0;
            default:
                return false;
        }
    }
}

