package com.qlhs.qlhs;
//

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


public class TTController {

    private static final Map<String, Set<String>> provinceDistrictMap = new HashMap<>();  // Tỉnh -> Các quận/huyện
    private static final Map<String, Set<String>> districtWardMap = new HashMap<>();      // Quận/huyện -> Các xã/phường
    @FXML
    private Label maHS_Lb;
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
    private Label sdtHopLe;
    @FXML
    private Label maDinhDanhHopLe;
    @FXML
    private Label hoDemHopLe;
    @FXML
    private Label tenHopLe;


    @FXML
    private ChoiceBox<String> bang_CB;


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
////

    //
    @FXML
    private void initialize() {
//
//
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

        // Set up the columns to use the Student class fields
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
                    provinceDistrictMap.putIfAbsent(province, new HashSet<>());
                    provinceDistrictMap.get(province).add(district);

                    // Thêm xã/phường vào quận/huyện
                    districtWardMap.putIfAbsent(district, new HashSet<>());
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
    private void lamMoiTT() {
        hoDem_TF.clear();
        ten_TF.clear();
        SDT_TF.clear();
        maDinhDanh_TF.clear();
        email_TF.clear();
        chiTiet_TF.clear();
        gioiTinh_Btn.setSelected(false);
        ngaySinh_Date.setValue(null);
        ghiChuTT_TF.clear();

        var isLuu = luuLop.getText();
        if (isLuu == null) {
            lop_TF.clear();
        }

        isLuu = luuTTP.getText();
        if (isLuu == null) {
            TTP_CB.setValue(null);
        }
        isLuu = luuQH.getText();
        if (isLuu == null) {
            QH_CB.setValue(null);

        }
        isLuu = luuPX.getText();
        if (isLuu == null) {
            PX_CB.setValue(null);
        }
    }

    @FXML
    private void themMoi() {
        ObservableList<Student> students = StudentDAO.getStudents();

        List<Integer> danhSachMaHS = new ArrayList<>();

//        if(LocalDate.now() == LocalDate.now()){

//        }
        // Lấy ngày hiện tại
        LocalDate today = LocalDate.now();

        // Chuỗi ngày cần so sánh
        ConfigReader configReader = new ConfigReader();
        String endOfSchoolYear = configReader.getEndOfSchoolYear();

        int nam = Integer.parseInt(endOfSchoolYear.substring(2, 4));

        LocalDate compareDate = LocalDate.parse(endOfSchoolYear, DateTimeFormatter.ISO_LOCAL_DATE);

        for (Student student : students) {
            if (student != null) {
                danhSachMaHS.add(Integer.parseInt(student.getMaHS()));
            }
            if (danhSachMaHS.size() > 1) {
                danhSachMaHS.removeFirst();
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

        maHS_Lb.setText(String.valueOf(danhSachMaHS.getFirst() + 1));

        lamMoiTT();
    }

    private void loadFXML(String fxmlFile) throws IOException {
        String fxmlPath = switch (fxmlFile) {
            case "Thông tin học sinh" -> "thongTinHocSinh.fxml";
            case "Bảng điểm" -> "bangDiemView.fxml";
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
        validateField(SDT_TF, sdtHopLe, kiemTraDuLieuNhap::isValidSoDienThoai);
        validateField(maDinhDanh_TF, maDinhDanhHopLe, kiemTraDuLieuNhap::isValidMaDinhDanh);
        validateField(hoDem_TF, hoDemHopLe, kiemTraDuLieuNhap::isValidTen);
        validateField(ten_TF, tenHopLe, kiemTraDuLieuNhap::isValidTen);

    }

    private void validateField(TextField textField, Label label, Validator validator) {
        String text = textField.getText();
        if (!text.isEmpty()) {
            if (validator.isValid(text)) {
                label.setStyle("-fx-text-fill: black;");
            } else {
                label.setStyle("-fx-text-fill: red;");
            }
        } else {
            label.setStyle("-fx-text-fill: black;");
        }
    }

    @FunctionalInterface
    private interface Validator {
        boolean isValid(String text);
    }
}
