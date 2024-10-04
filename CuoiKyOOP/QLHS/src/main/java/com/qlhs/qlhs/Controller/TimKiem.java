package com.qlhs.qlhs.Controller;

import com.qlhs.qlhs.Database.HocSinhDAO;
import com.qlhs.qlhs.Model.HocSinh;
import javafx.collections.ObservableList;

import java.util.Objects;

public class TimKiem {

    static ObservableList<HocSinh> boLoc(String input) {
        ObservableList<HocSinh> dsHocSinhDaLoc;

        // Lấy danh sách tất cả học sinh có trạng thái "1"
        ObservableList<HocSinh> allStudents = HocSinhDAO.getDSHocSinh().filtered(hocSinh -> Objects.equals(hocSinh.getTrangThai(), "1"));

        // Tách các điều kiện tìm kiếm bằng dấu phẩy
        String[] searchTerms = input.split(",");

        dsHocSinhDaLoc = allStudents.filtered(hocSinh -> {
            boolean matches = true; // Mặc định là true

            // Kiểm tra từng điều kiện tìm kiếm
            for (String term : searchTerms) {
                String trimmedTerm = term.trim(); // loại bỏ khoảng trắng

                // Kiểm tra điều kiện có định dạng "trường:giá trị"
                String[] parts = trimmedTerm.split(":");
                if (parts.length == 2) {
                    String fieldName = parts[0].trim().toLowerCase(); // Trường tìm kiếm (chuyển thành chữ thường)
                    String searchValue = parts[1].trim(); // Giá trị tìm kiếm

                    // Kiểm tra xem giá trị tìm kiếm có khớp với trường tương ứng không
                    switch (fieldName) {
                        case "tên":
                        case "ten":
                            matches = hocSinh.getTen() != null && hocSinh.getTen().toLowerCase().contains(searchValue.toLowerCase());
                            break;
                        case "họ đệm":
                        case "ho dem":
                            matches = hocSinh.getHoDem() != null && hocSinh.getHoDem().toLowerCase().contains(searchValue.toLowerCase());
                            break;
                        case "mã hs":
                        case "ma hs":
                            matches = hocSinh.getMaHS() != null && hocSinh.getMaHS().toLowerCase().contains(searchValue.toLowerCase());
                            break;
                        case "sdt":
                        case "số điện thoại":
                        case "so dien thoai":
                            matches = hocSinh.getSdt() != null && hocSinh.getSdt().contains(searchValue);
                            break;
                        case "email":
                            matches = hocSinh.getEmail() != null && hocSinh.getEmail().toLowerCase().contains(searchValue.toLowerCase());
                            break;
                        case "lớp":
                        case "lop":
                            matches = hocSinh.getLop() != null && hocSinh.getLop().toLowerCase().contains(searchValue.toLowerCase());
                            break;
                        case "địa chỉ":
                        case "dia chi":
                            matches = hocSinh.getDiaChi() != null && hocSinh.getDiaChi().toLowerCase().contains(searchValue.toLowerCase());
                            break;
                        case "ghi chú":
                        case "ghi chu":
                            matches = hocSinh.getGhiChuTT() != null && hocSinh.getGhiChuTT().toLowerCase().contains(searchValue.toLowerCase());
                            break;
                        case "ngày sinh":
                        case "ngay sinh":
                            matches = hocSinh.getNgaySinh() != null && String.valueOf(hocSinh.getNgaySinh()).contains(searchValue);
                            break;
                        case "giới tính":
                        case "gioi tinh":
                            matches = hocSinh.getGioiTinh() != null && String.valueOf(hocSinh.getGioiTinh()).contains(searchValue);
                            break;
                        case "mã định danh":
                        case "ma dinh danh":
                            matches = hocSinh.getMaDinhDanh() != null && hocSinh.getMaDinhDanh().toLowerCase().contains(searchValue.toLowerCase());
                            break;
                        default:
                            matches = false; // Nếu không phải trường hợp nào trong danh sách
                            break;
                    }
                } else {
                    // Nếu không có định dạng trường:giá trị, tìm kiếm trong tất cả các trường
                    matches = (hocSinh.getMaHS() != null && hocSinh.getMaHS().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (hocSinh.getHoDem() != null && hocSinh.getHoDem().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (hocSinh.getTen() != null && hocSinh.getTen().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (hocSinh.getSdt() != null && hocSinh.getSdt().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (hocSinh.getEmail() != null && hocSinh.getEmail().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (hocSinh.getLop() != null && hocSinh.getLop().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (hocSinh.getDiaChi() != null && hocSinh.getDiaChi().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (hocSinh.getGhiChuTT() != null && hocSinh.getGhiChuTT().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (hocSinh.getNgaySinh() != null && String.valueOf(hocSinh.getNgaySinh()).contains(trimmedTerm)) ||
                            (hocSinh.getGioiTinh() != null && String.valueOf(hocSinh.getGioiTinh()).contains(trimmedTerm)) ||
                            (hocSinh.getMaDinhDanh() != null && hocSinh.getMaDinhDanh().toLowerCase().contains(trimmedTerm.toLowerCase()));
                }

                // Nếu không khớp, thoát khỏi vòng lặp
                if (!matches) {
                    break;
                }
            }

            return matches; // Trả về true nếu học sinh khớp với tất cả các điều kiện
        });

        return dsHocSinhDaLoc;
    }

}
