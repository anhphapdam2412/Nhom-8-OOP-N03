package com.qlhs.qlhs.Controller;
//

import com.qlhs.qlhs.Database.CapNhatDatabase;
import com.qlhs.qlhs.Database.HocSinhDAO;
import com.qlhs.qlhs.Model.HocSinh;
import com.qlhs.qlhs.View.HopThoai;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class StudentController {

    private static final Map<String, Set<String>> provinceDistrictMap = new TreeMap<>(); // Tỉnh -> Quận/huyện
    private static final Map<String, Set<String>> districtWardMap = new TreeMap<>();      // Quận/huyện -> Xã/phường
    public boolean choPhepCapNhat = false;
    public List<Integer> danhSachMaHS = new ArrayList<>();

    // Các TextField
    @FXML private TextField maHS_TF, hoDem_TF, ten_TF, SDT_TF, maDinhDanh_TF, email_TF, lop_TF, chiTiet_TF, ghiChuTT_TF, timKiem_TF;
    @FXML private RadioButton nam_Btn, luuTTP, luuQH, luuPX, luuLop, luuNgay, nu_Btn;

    // Ngày sinh và ComboBox
    @FXML private DatePicker ngaySinh_Date;
    @FXML private ComboBox<String> TTP_CB, QH_CB, PX_CB, bang_CB;

    // Các Button và Label
    @FXML private Button themMoi_Btn, xoa_Btn;
    @FXML private Label gioiTinh_Lb, sdt_Lb, maDinhDanh_Lb, hoDem_Lb, ten_Lb, maHS_Lb, lop_Lb, TTP_Lb, QH_Lb, PX_Lb, ngaySinh_Lb;

    // TableView và TableColumn
    @FXML private TableView<HocSinh> tableTTView;
    @FXML private TableColumn<HocSinh, String> sttColumn, maHSColumn, hoDemColumn, tenColumn, ngaySinhColumn, gioiTinhColumn, maDinhDanhColumn, sdtColumn, emailColumn, lopColumn, diaChiColumn, ghiChuTTColumn;


    private Timeline debounce;


    @FXML
    private void initialize() {
//

        tableTTView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("Dòng đã chọn: " + newSelection);
            }
        });
        // Thêm các lựa chọn vào ChoiceBox
        bang_CB.getItems().addAll("Thông tin học sinh", "Bảng điểm");
        bang_CB.setValue("Thông tin học sinh");

        // Lắng nghe sự thay đổi lựa chọn trong ChoiceBox
        bang_CB.getSelectionModel().selectedItemProperty().addListener((_, _, newVal) -> {
            try {
                loadFXML(newVal);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Tạo ToggleGroup để chỉ cho phép chọn một nút
        ToggleGroup group = new ToggleGroup();
        nam_Btn.setToggleGroup(group);
        nu_Btn.setToggleGroup(group);



        debounce = new Timeline(new KeyFrame(Duration.millis(300), event -> {
            if (Objects.equals(timKiem_TF.getText(), "")) {
                hienThiHSLenManHinh(TimKiem.boLoc(""));
            } else {
                hienThiHSLenManHinh(TimKiem.boLoc(timKiem_TF.getText()));
            }
        }));
        debounce.setCycleCount(1);

        layTTTinhTHanhTuCSV();
        hienThiHSLenManHinh(TimKiem.boLoc(""));
        chonHocSinh();

    }

    private void hienThiHSLenManHinh(ObservableList<HocSinh> query) {
        // Thiết lập các cột
        sttColumn.setCellValueFactory(cellData -> {
            // Lấy chỉ số của học sinh trong danh sách
            int index = tableTTView.getItems().indexOf(cellData.getValue()) + 1;
            return new SimpleStringProperty(String.valueOf(index));
        });
        maHSColumn.setCellValueFactory(new PropertyValueFactory<>("maHS"));
        hoDemColumn.setCellValueFactory(new PropertyValueFactory<>("hoDem"));
        tenColumn.setCellValueFactory(new PropertyValueFactory<>("ten"));
        ngaySinhColumn.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        gioiTinhColumn.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        maDinhDanhColumn.setCellValueFactory(new PropertyValueFactory<>("maDinhDanh"));
        sdtColumn.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        lopColumn.setCellValueFactory(new PropertyValueFactory<>("lop"));

        diaChiColumn.setCellValueFactory(cellData -> {
            String diaChi = cellData.getValue().getDiaChi();
            if (diaChi != null) {
                diaChi = diaChi.replace(", null", "");
            }
            return new SimpleStringProperty(diaChi);
        });

        ghiChuTTColumn.setCellValueFactory(new PropertyValueFactory<>("ghiChuTT"));
        // Đặt danh sách đã lọc vào bảng
        tableTTView.setItems(query);
    }

    // Phương thức để thiết lập sự kiện chuột cho bảng
    private void chonHocSinh() {
        tableTTView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Nhấp đúp chuột
                HocSinh hocSinhDuocChon = tableTTView.getSelectionModel().getSelectedItem();
                if (hocSinhDuocChon != null) {
                    hienTTHSChiTiet(hocSinhDuocChon);
                    danhSachKiemTra();
                    xoa_Btn.setDisable(false);
                    themMoi_Btn.setDisable(false);
                }
            }
        });
    }

    // Điền thông tin học sinh được chọn từ bảng lên các ô điền thông tin
    private void hienTTHSChiTiet(HocSinh hocSinh) {
        maHS_TF.setText(hocSinh.getMaHS());
        hoDem_TF.setText(hocSinh.getHoDem());
        ten_TF.setText(hocSinh.getTen());
        SDT_TF.setText(hocSinh.getSdt());
        email_TF.setText(hocSinh.getEmail());
        lop_TF.setText(hocSinh.getLop());
        if ("1".equals(hocSinh.getGioiTinh())) {
            nam_Btn.setSelected(true);
        } else {
            nu_Btn.setSelected(true);
        }
        String ngaySinhStr = hocSinh.getNgaySinh();
        if (ngaySinhStr != null && !ngaySinhStr.isEmpty()) {
            LocalDate ngaySinh = LocalDate.parse(ngaySinhStr);
            ngaySinh_Date.setValue(ngaySinh);
        } else {
            ngaySinh_Date.setValue(null); // Hoặc thiết lập một giá trị mặc định
        }
        String diaChi = hocSinh.getDiaChi();
        String[] diaChiParts = diaChi.split(",\\s*"); // Tách chuỗi theo dấu phẩy và khoảng trắng

        if (diaChiParts.length >= 4) {
            // Set giá trị cho ComboBox
            TTP_CB.setValue(diaChiParts[0]); // Tỉnh
            QH_CB.setValue(diaChiParts[1]);  // Quận/Huyện
            if (!diaChiParts[2].equals("null")) {
                PX_CB.setValue(diaChiParts[2]);   // Phường/Xã
            }
            // Set giá trị cho TextField
            if (!diaChiParts[3].equals("null")) {

                chiTiet_TF.setText(diaChiParts[3].trim()); // Khu, chi tiết
            }
        } else {
            // Xử lý trường hợp chuỗi không đủ phần
            System.out.println("Địa chỉ không hợp lệ.");
        }
        maDinhDanh_TF.setText(hocSinh.getMaDinhDanh());
        ghiChuTT_TF.setText(hocSinh.getGhiChuTT());
    }

    //
    public void layTTTinhTHanhTuCSV() {
        String filePath = "../CuoiKyOOP/QLHS/src/main/resources/provinces.csv";  // Đường dẫn tới file CSV

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");  // Giả định CSV sử dụng dấu phẩy
                if (values.length >= 5) {
                    String province = values[0];  // Cột tỉnh/thành phố
                    String district = values[2];  // Cột quận/huyện
                    String ward = values[4];      // Cột phường/xã

                    // Thêm quận/huyện vào tỉnh/thành phố
                    provinceDistrictMap.putIfAbsent(province, new TreeSet<>());
                    provinceDistrictMap.get(province).add(district);

                    // Thêm xã/phường vào quận/huyện
                    districtWardMap.putIfAbsent(district, new TreeSet<>());
                    districtWardMap.get(district).add(ward);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Thêm các tỉnh thành phố vào ChoiceBox
        TTP_CB.getItems().addAll(provinceDistrictMap.keySet());

        // Thêm sự kiện chọn tỉnh/thành phố để cập nhật Quận/Huyện
        TTP_CB.setOnAction(_ -> capNhatQH());


        // Thêm sự kiện chọn Quận/Huyện để cập nhật Xã/Phường
        QH_CB.setOnAction(_ -> capNhatPX());
    }

    // Cập nhật danh sách Quận/Huyện khi chọn Tỉnh/Thành Phố
    private void capNhatQH() {
        danhSachKiemTra();
        QH_CB.getItems().clear();  // Xóa các quận/huyện hiện tại
        PX_CB.getItems().clear();  // Xóa các xã/phường hiện tại

        String selectedProvince = TTP_CB.getValue();
        if (selectedProvince != null && provinceDistrictMap.containsKey(selectedProvince)) {
            Set<String> districts = provinceDistrictMap.get(selectedProvince);
            QH_CB.getItems().addAll(districts);  // Thêm các quận/huyện tương ứng
        }
    }

    // Cập nhật danh sách Xã/Phường khi chọn Quận/Huyện
    private void capNhatPX() {
        danhSachKiemTra();
        PX_CB.getItems().clear();  // Xóa các xã/phường hiện tại

        String selectedDistrict = QH_CB.getValue();
        if (selectedDistrict != null && districtWardMap.containsKey(selectedDistrict)) {
            Set<String> wards = districtWardMap.get(selectedDistrict);
            PX_CB.getItems().addAll(wards);  // Thêm các xã/phường tương ứng
        }
    }

    @FXML
    private void capNhatTT() {
        danhSachKiemTra();
        if (!choPhepCapNhat || maHS_TF.getText().isEmpty()) {
            HopThoai.baoLoi("Vui lòng điền đầy đủ thông tin hoặc nhập mã học sinh.");
            return;
        }

        ObservableList<HocSinh> dsHocSinh = HocSinhDAO.getDSHocSinh();
        dsHocSinh.stream().map(HocSinh::getMaHS).map(Integer::parseInt).forEach(danhSachMaHS::add);

        String maHS = maHS_TF.getText();
        String hoDem = hoDem_TF.getText();
        String ten = ten_TF.getText();
        String sdt = SDT_TF.getText();
        String email = email_TF.getText();
        String lop = lop_TF.getText();
        String diaChi = TTP_CB.getValue() + ", " + QH_CB.getValue() + ", " + PX_CB.getValue() + ", " + (!chiTiet_TF.getText().isEmpty() ? chiTiet_TF.getText() : null);
        String ghiChu = ghiChuTT_TF.getText();
        String ngaySinh = String.valueOf(ngaySinh_Date.getValue());
        Boolean gioiTinh = nam_Btn.isSelected();
        String maDinhDanh = maDinhDanh_TF.getText();
        String trangThai = "true";

        boolean isUpdate = danhSachMaHS.contains(Integer.parseInt(maHS_TF.getText()));
        String query = isUpdate ? "UPDATE hocsinh SET hoDem = ?, ten = ?, ngaySinh = ?, gioiTinh = ?, maDinhDanh = ?, sdt = ?, email = ?, lop = ?, diaChi = ?, ghiChuTT = ?, trangThai = ? WHERE maHS = ?;"
                : "INSERT INTO hocsinh (hoDem, ten, ngaySinh, gioiTinh, maDinhDanh, sdt, email, lop, diaChi, ghiChuTT, trangThai, maHS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String query2 = !isUpdate ? "INSERT INTO diem (maHS) VALUES (?);" : "";

        CapNhatDatabase.capNhatTT(maHS, hoDem, ten, ngaySinh, gioiTinh, maDinhDanh, sdt, email, lop, diaChi, ghiChu, trangThai, query, query2);
        hienThiHSLenManHinh(TimKiem.boLoc(""));
        timKiem_TF.setText(null);
        themMoi_Btn.setDisable(false);

        LuuLichSuHoatDong.logThongTin(maHS + hoDem + ten + ngaySinh + gioiTinh + maDinhDanh + sdt + email + lop + diaChi + ghiChu + trangThai);
    }

    @FXML
    private void lamMoiTT() {
        hienThiHSLenManHinh(TimKiem.boLoc(""));
        hoDem_TF.clear();
        ten_TF.clear();
        SDT_TF.clear();
        maDinhDanh_TF.clear();
        email_TF.clear();
        chiTiet_TF.clear();
        nam_Btn.setSelected(false);
        ghiChuTT_TF.clear();
        timKiem_TF.clear();
        nu_Btn.setSelected(false);

        boolean isCapNhat;

        isCapNhat = luuNgay.isSelected();
        if (!isCapNhat) {
            ngaySinh_Date.setValue(null);
        }

        isCapNhat = luuLop.isSelected();
        if (!isCapNhat) {
            lop_TF.clear();
        }

        isCapNhat = luuTTP.isSelected();
        if (!isCapNhat) {
            TTP_CB.setValue(null);
        }

        isCapNhat = luuQH.isSelected();
        if (!isCapNhat) {
            QH_CB.setValue(null);

        }
        isCapNhat = luuPX.isSelected();
        if (!isCapNhat) {
            PX_CB.setValue(null);
        }

    }

    @FXML
    private void xoaTT() {
        if (maHS_TF.getText().equals("23xxxxxx")) {
            HopThoai.baoLoi("Chưa có mã học sinh");
        } else {
            String maHS = maHS_TF.getText();
            String trangThai = "false";

            String query = "UPDATE hocsinh SET trangThai = ?  WHERE maHS = ?;";

            CapNhatDatabase.xoaTT(maHS, trangThai, query);
            hienThiHSLenManHinh(TimKiem.boLoc(""));
            lamMoiTT();
            xoa_Btn.setDisable(true);
            maHS_TF.setText("23xxxxxx");
        }

    }

    @FXML
    private void themMoi() {
        xoa_Btn.setDisable(true);
        lamMoiTT();
        ObservableList<HocSinh> dsHocSinh = HocSinhDAO.getDSHocSinh();

        LocalDate today = LocalDate.now();

        ConfigReader configReader = new ConfigReader();
        String endOfSchoolYear = configReader.getEndOfSchoolYear();

        int nam = Integer.parseInt(endOfSchoolYear.substring(2, 4));

        LocalDate compareDate = LocalDate.parse(endOfSchoolYear, DateTimeFormatter.ISO_LOCAL_DATE);

        // kiểm tra xem đã tồn tại học sinh nào chưa, nếu chưa thì tạo 1 học sinh giả
        for (HocSinh hocSinh : dsHocSinh) {
            if (hocSinh != null) {
                danhSachMaHS.add(Integer.parseInt(hocSinh.getMaHS()));
            }
        }
        if (dsHocSinh.isEmpty()) {
            danhSachMaHS.add(0);
        }
        // So sánh ngày hiện tại với ngày chuỗi
        if (today.isAfter(compareDate)) {
            if (danhSachMaHS.getLast() / 1000000 < nam) { // chia lấy nguyên cho 1000000 để lấy ra năm
                danhSachMaHS.add(nam * 1000000 - 1); // vd mã năm là 24 thì sẽ là 24*1000000-1 = 23999999 để xuống dưới sẽ cộng thêm 1 và ra mã 24000000
                danhSachMaHS.removeFirst();
            }
        }

        maHS_TF.setText(String.valueOf(danhSachMaHS.getLast() + 1));
        danhSachKiemTra();
        themMoi_Btn.setDisable(true);

    }

    private void loadFXML(String fxmlFile) throws IOException {
        String fxmlPath = switch (fxmlFile) {
            case "Thông tin học sinh" -> "/com/qlhs/qlhs/HocSinhView.fxml";
            case "Bảng điểm" -> "/com/qlhs/qlhs/bangDiemView.fxml";
            default -> throw new IllegalArgumentException("Unexpected value: " + fxmlFile);
        };

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            Stage stage = (Stage) bang_CB.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show(); // Ensure the stage is visible
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleKeyReleased() {
        danhSachKiemTra();
    }

    @FXML
    private void timKiemHS() {
        // Hủy timeline hiện tại nếu nó đang chạy
        debounce.stop();
        // Khởi động lại timeline, nó sẽ đợi 300ms trước khi thực hiện hành động tìm kiếm
        debounce.playFromStart();
    }

    private void danhSachKiemTra() {
        System.out.println(String.valueOf(nam_Btn.isSelected())+String.valueOf(nu_Btn.isSelected()));
        KiemTraDuLieuNhap.validateField(maHS_TF.getText(), maHS_Lb, KiemTraDuLieuNhap::isValidMaHS);
        KiemTraDuLieuNhap.validateField(lop_TF.getText(), lop_Lb, KiemTraDuLieuNhap::isValidLop);
        KiemTraDuLieuNhap.validateField(SDT_TF.getText(), sdt_Lb, KiemTraDuLieuNhap::isValidSoDienThoai);
        KiemTraDuLieuNhap.validateField(maDinhDanh_TF.getText(), maDinhDanh_Lb, KiemTraDuLieuNhap::isValidMaDinhDanh);
        KiemTraDuLieuNhap.validateField(hoDem_TF.getText(), hoDem_Lb, KiemTraDuLieuNhap::isValidTen);
        KiemTraDuLieuNhap.validateField(ten_TF.getText(), ten_Lb, KiemTraDuLieuNhap::isValidTen);
        KiemTraDuLieuNhap.validateField(TTP_CB.getValue(), TTP_Lb, KiemTraDuLieuNhap::isValidComboBox);
        KiemTraDuLieuNhap.validateField(QH_CB.getValue(), QH_Lb, KiemTraDuLieuNhap::isValidComboBox);
        KiemTraDuLieuNhap.validateField(String.valueOf(nam_Btn.isSelected())+String.valueOf(nu_Btn.isSelected()),gioiTinh_Lb, KiemTraDuLieuNhap::isValidSex);
//        validateField(PX_CB.getValue(),PX_Lb, KiemTraDuLieuNhap::isValidPX);
        String ngaySinh = String.valueOf(ngaySinh_Date.getValue());
        KiemTraDuLieuNhap.validateField(ngaySinh, ngaySinh_Lb, KiemTraDuLieuNhap::isValidNgaySinh);


        choPhepCapNhat = KiemTraDuLieuNhap.validateField(maHS_TF.getText(), maHS_Lb, KiemTraDuLieuNhap::isValidMaHS) &&
                KiemTraDuLieuNhap.validateField(SDT_TF.getText(), sdt_Lb, KiemTraDuLieuNhap::isValidSoDienThoai) &&
                KiemTraDuLieuNhap.validateField(maDinhDanh_TF.getText(), maDinhDanh_Lb, KiemTraDuLieuNhap::isValidMaDinhDanh) &&
                KiemTraDuLieuNhap.validateField(hoDem_TF.getText(), hoDem_Lb, KiemTraDuLieuNhap::isValidTen) &&
                KiemTraDuLieuNhap.validateField(ten_TF.getText(), ten_Lb, KiemTraDuLieuNhap::isValidTen) &&
                KiemTraDuLieuNhap.validateField(TTP_CB.getValue(), TTP_Lb, KiemTraDuLieuNhap::isValidComboBox) &&
                KiemTraDuLieuNhap.validateField(QH_CB.getValue(), QH_Lb, KiemTraDuLieuNhap::isValidComboBox) &&
//              !  validateField(PX_CB.getValue(),PX_Lb, KiemTraDuLieuNhap::isValidPX)||
                KiemTraDuLieuNhap.validateField(lop_TF.getText(), lop_Lb, KiemTraDuLieuNhap::isValidLop) &&
                KiemTraDuLieuNhap.validateField(String.valueOf(nam_Btn.isSelected())+String.valueOf(nu_Btn.isSelected()),gioiTinh_Lb, KiemTraDuLieuNhap::isValidSex)&&
                KiemTraDuLieuNhap.validateField(ngaySinh, ngaySinh_Lb, KiemTraDuLieuNhap::isValidNgaySinh);
    }
}
