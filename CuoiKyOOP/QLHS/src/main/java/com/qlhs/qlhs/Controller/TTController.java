package com.qlhs.qlhs.Controller;
//
import com.qlhs.qlhs.Model.Student;
import com.qlhs.qlhs.Model.StudentDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.scene.control.DatePicker;

public class TTController {

    private static final Map<String, Set<String>> provinceDistrictMap = new TreeMap<>();  // Tỉnh -> Các quận/huyện
    private static final Map<String, Set<String>> districtWardMap = new TreeMap<>();      // Quận/huyện -> Các xã/phường
    @FXML
    private TextField maHS_TF;
    @FXML
    private TextField hoDem_TF;
    @FXML
    private TextField ten_TF;
    @FXML
    private TextField SDT_TF;
    @FXML
    private TextField maDinhDanh_TF;
    @FXML
    private TextField email_TF;
    @FXML
    private TextField lop_TF;
    @FXML
    private TextField chiTiet_TF;
    @FXML
    private RadioButton gioiTinh_Btn;
    @FXML
    private DatePicker ngaySinh_Date;
    @FXML
    private ComboBox<String> TTP_CB; // ComboBox for Tỉnh Thành Phố
    @FXML
    private ComboBox<String> QH_CB;  // ComboBox for Quận Huyện
    @FXML
    private ComboBox<String> PX_CB;  // ComboBox for Phường Xã
    @FXML
    private TextField ghiChuTT_TF;
    @FXML
    private RadioButton luuTTP;
    @FXML
    private RadioButton luuQH;
    @FXML
    private RadioButton luuPX;
    @FXML
    private RadioButton luuLop;

    @FXML
    private Label sdt_Lb;
    @FXML
    private Label maDinhDanh_Lb;
    @FXML
    private Label hoDem_Lb;
    @FXML
    private Label ten_Lb;
    @FXML
    private Label maHS_Lb;

    @FXML
    private ComboBox<String> bang_CB;


    @FXML
    private TableView<Student> tableTTView;
    @FXML
    private TableColumn<Student, String> sttColumn;
    @FXML
    private TableColumn<Student, String> maHSColumn;
    @FXML
    private TableColumn<Student, String> hoDemColumn;
    @FXML
    private TableColumn<Student, String> tenColumn;
    @FXML
    private TableColumn<Student, String> ngaySinhColumn;
    @FXML
    private TableColumn<Student, String> gioiTinhColumn;
    @FXML
    private TableColumn<Student, String> maDinhDanhColumn;
    @FXML
    private TableColumn<Student, String> sdtColumn;
    @FXML
    private TableColumn<Student, String> emailColumn;
    @FXML
    private TableColumn<Student, String> lopColumn;
    @FXML
    private TableColumn<Student, String> diaChiColumn;
    @FXML
    private TableColumn<Student, String> ghiChuTTColumn;


    public boolean choPhepLuu=false;
    public List<Integer> danhSachMaHS = new ArrayList<>();

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
        loadProvincesFromCSV();
        displayStudent();

    }

    // Set up the columns to use the Student class fields
    private void displayStudent(){
        sttColumn.setCellValueFactory(new PropertyValueFactory<>("stt"));
        maHSColumn.setCellValueFactory(new PropertyValueFactory<>("maHS"));
        hoDemColumn.setCellValueFactory(new PropertyValueFactory<>("hoDem"));
        tenColumn.setCellValueFactory(new PropertyValueFactory<>("ten"));
        ngaySinhColumn.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        gioiTinhColumn.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        maDinhDanhColumn.setCellValueFactory(new PropertyValueFactory<>("maDinhDanh"));
        sdtColumn.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        lopColumn.setCellValueFactory(new PropertyValueFactory<>("lop"));
        diaChiColumn.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        ghiChuTTColumn.setCellValueFactory(new PropertyValueFactory<>("ghiChuTT"));

        // Load data from the database
        ObservableList<Student> students = StudentDAO.getStudents();
            tableTTView.setItems(students);

        // Add listener for mouse click on the table
            tableTTView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double click

                Student selectedStudent = tableTTView.getSelectionModel().getSelectedItem();
                if (selectedStudent != null) {
                    displayStudentDetails(selectedStudent);
                }
            }
        });
    }
    // Method to display details of the selected student
    private void displayStudentDetails(Student student) {
        // Here you can set the text fields with the student details
        maHS_TF.setText(student.getMaHS());
        hoDem_TF.setText(student.getHoDem());
        ten_TF.setText(student.getTen());
        SDT_TF.setText(student.getSdt());
        email_TF.setText(student.getEmail());
        lop_TF.setText(student.getLop());
        gioiTinh_Btn.setSelected("1".equals(student.getGioiTinh()));
        String ngaySinhStr = student.getNgaySinh();
        if (ngaySinhStr != null && !ngaySinhStr.isEmpty()) {
            LocalDate ngaySinh = LocalDate.parse(ngaySinhStr);
            ngaySinh_Date.setValue(ngaySinh);
        } else {
            ngaySinh_Date.setValue(null); // Hoặc thiết lập một giá trị mặc định
        }
        String diaChi = student.getDiaChi();
        String[] diaChiParts = diaChi.split(",\\s*"); // Tách chuỗi theo dấu phẩy và khoảng trắng

        if (diaChiParts.length >= 4) {
            // Set giá trị cho ComboBox
            TTP_CB.setValue(diaChiParts[0]); // Tỉnh
            QH_CB.setValue(diaChiParts[1]);  // Quận/Huyện
            PX_CB.setValue(diaChiParts[2]);   // Phường/Xã

            // Set giá trị cho TextField
            chiTiet_TF.setText(diaChiParts[3].trim()); // Khu, chi tiết
        } else {
            // Xử lý trường hợp chuỗi không đủ phần
            System.out.println("Địa chỉ không hợp lệ.");
        }
        maDinhDanh_TF.setText(student.getMaDinhDanh());
        ghiChuTT_TF.setText(student.getGhiChuTT());
    }
    //
    public void loadProvincesFromCSV() {
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
        TTP_CB.setOnAction(_ -> updateDistricts());

        // Thêm sự kiện chọn Quận/Huyện để cập nhật Xã/Phường
        QH_CB.setOnAction(_ -> updateWards());
    }

    // Cập nhật danh sách Quận/Huyện khi chọn Tỉnh/Thành Phố
    private void updateDistricts() {
        QH_CB.getItems().clear();  // Xóa các quận/huyện hiện tại
        PX_CB.getItems().clear();  // Xóa các xã/phường hiện tại

        String selectedProvince = TTP_CB.getValue();
        if (selectedProvince != null && provinceDistrictMap.containsKey(selectedProvince)) {
            Set<String> districts = provinceDistrictMap.get(selectedProvince);
            QH_CB.getItems().addAll(districts);  // Thêm các quận/huyện tương ứng
        }
    }

    // Cập nhật danh sách Xã/Phường khi chọn Quận/Huyện
    private void updateWards() {
        PX_CB.getItems().clear();  // Xóa các xã/phường hiện tại

        String selectedDistrict = QH_CB.getValue();
        if (selectedDistrict != null && districtWardMap.containsKey(selectedDistrict)) {
            Set<String> wards = districtWardMap.get(selectedDistrict);
            PX_CB.getItems().addAll(wards);  // Thêm các xã/phường tương ứng
        }
    }
    @FXML
    private void luuTT(){
        danhSachKiemTra();
        System.out.println(choPhepLuu);
        if(maHS_TF.getText().equals("23xxxxxx")){
            HopThoai.baoLoi("Chưa có mã học sinh");
        }
        else if(!choPhepLuu){
            HopThoai.baoLoi("Vui lòng điền đúng và đủ thông tin");

        }
        else if (ngaySinh_Date.getValue() == null) {
            HopThoai.baoLoi("Chưa nhập ngày sinh");
        }
        else {

            ObservableList<Student> students = StudentDAO.getStudents();

            // kiểm tra xem đã tồn tại học sinh nào chưa, nếu chưa thì tạo 1 học sinh giả
            if (students.isEmpty()) {
                danhSachMaHS.add(0);
            }
            for (Student student : students) {
                if (student != null) {
                    danhSachMaHS.add(Integer.parseInt(student.getMaHS()));
                }
            }

            String script;
            String maHS = maHS_TF.getText();
            String hoDem = hoDem_TF.getText();
            String ten = ten_TF.getText();
            String sdt = SDT_TF.getText();
            String email = email_TF.getText();
            String lop = lop_TF.getText();
            String diaChi = TTP_CB.getValue() + ", " + QH_CB.getValue() + ", " + PX_CB.getValue() + ", " + (!chiTiet_TF.getText().isEmpty() ? chiTiet_TF.getText() : null);
            String ghiChu = ghiChuTT_TF.getText();
            String ngaySinh = String.valueOf(ngaySinh_Date.getValue());
            String gioiTinh = gioiTinh_Btn.isSelected() ? "1" : "0";
            String maDinhDanh = maDinhDanh_TF.getText();

            boolean isUpdate = false;
            for (int ma : danhSachMaHS){
                if (ma == Integer.parseInt(maHS)){
                    isUpdate = true;

                    break;
                }
            }
            if (isUpdate) {
                script = "UPDATE thongTinHocSinh SET hoDem = ?, ten = ?, ngaySinh = ?, gioiTinh = ?, maDinhDanh = ?, sdt = ?, email = ?, lop = ?, diaChi = ?, ghiChuTT = ? WHERE maHS = ?;";
            }else{
                script = "INSERT INTO thongTinHocSinh (hoDem, ten, ngaySinh, gioiTinh, maDinhDanh, sdt, email, lop, diaChi, ghiChuTT,maHS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            }
            LuuVaoDatabase.capNhatTT(maHS,hoDem,ten,ngaySinh,gioiTinh,maDinhDanh,sdt,email,lop,diaChi,ghiChu, script);
        }

        displayStudent();
    }
    @FXML
    private void lamMoiTT() {
        displayStudent();
        hoDem_TF.clear();
        ten_TF.clear();
        SDT_TF.clear();
        maDinhDanh_TF.clear();
        email_TF.clear();
        chiTiet_TF.clear();
        gioiTinh_Btn.setSelected(false);
        ngaySinh_Date.setValue(null);
        ghiChuTT_TF.clear();

        boolean isLuu;
        isLuu = luuLop.isSelected();
        if (!isLuu) {
            lop_TF.clear();
        }

        isLuu = luuTTP.isSelected();
        if (!isLuu) {
            TTP_CB.setValue(null);
        }

        isLuu = luuQH.isSelected();
        if (!isLuu) {
            QH_CB.setValue(null);

        }
        isLuu = luuPX.isSelected();
        if (!isLuu) {
            PX_CB.setValue(null);
        }
    }

    @FXML
    private void themMoi() {
        lamMoiTT();
        ObservableList<Student> students = StudentDAO.getStudents();

        LocalDate today = LocalDate.now();

        ConfigReader configReader = new ConfigReader();
        String endOfSchoolYear = configReader.getEndOfSchoolYear();

        int nam = Integer.parseInt(endOfSchoolYear.substring(2, 4));

        LocalDate compareDate = LocalDate.parse(endOfSchoolYear, DateTimeFormatter.ISO_LOCAL_DATE);

        // kiểm tra xem đã tồn tại học sinh nào chưa, nếu chưa thì tạo 1 học sinh giả
        if (students.size() == 0) {
            danhSachMaHS.add(0);
        }
        for (Student student : students) {
            if (student != null) {
                danhSachMaHS.add(Integer.parseInt(student.getMaHS()));
            }
        }
        System.out.println(danhSachMaHS);
        // So sánh ngày hiện tại với ngày chuỗi
        if (today.isAfter(compareDate)) {
            if (danhSachMaHS.getFirst() / 1000000 < nam) { // chia lấy nguyên cho 1000000 để lấy ra năm
                danhSachMaHS.removeFirst();
                danhSachMaHS.add(nam * 1000000 - 1); // vd mã năm là 24 thì sẽ là 24*1000000-1 = 23999999 để xuống dưới sẽ cộng thêm 1 và ra mã 24000000
            }
        }

        maHS_TF.setText(String.valueOf(danhSachMaHS.getLast() + 1));

    }

    private void loadFXML(String fxmlFile) throws IOException {
        String fxmlPath = switch (fxmlFile) {
            case "Thông tin học sinh" -> "/com/qlhs/qlhs/thongTinHocSinhView.fxml";
            case "Bảng điểm" -> "/com/qlhs/qlhs/bangDiemView.fxml";
            default -> throw new IllegalArgumentException("Unexpected value: " + fxmlFile);
        };

        System.out.println("Loading FXML: " + fxmlPath); // Debugging line
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

    private void danhSachKiemTra(){
        validateField(maHS_TF, maHS_Lb, KiemTraDuLieuNhap::isValidMaHS);
        validateField(SDT_TF, sdt_Lb, KiemTraDuLieuNhap::isValidSoDienThoai);
        validateField(maDinhDanh_TF, maDinhDanh_Lb, KiemTraDuLieuNhap::isValidMaDinhDanh);
        validateField(hoDem_TF, hoDem_Lb, KiemTraDuLieuNhap::isValidTen);
        validateField(ten_TF, ten_Lb, KiemTraDuLieuNhap::isValidTen);
    }

    private void validateField(TextField textField, Label label, Validator validator) {
        String text = textField.getText();
        if (!text.isEmpty()) {
            if (validator.isValid(text)) {
                label.setStyle("-fx-text-fill: #ffffff;");
                choPhepLuu = true;
            } else {
                label.setStyle("-fx-text-fill: #ff6363;");
                choPhepLuu = false;
            }
        } else {
            label.setStyle("-fx-text-fill: #ff6363;");
            choPhepLuu = false;
        }
    }

    @FunctionalInterface
    private interface Validator {
        boolean isValid(String text);
    }


}
