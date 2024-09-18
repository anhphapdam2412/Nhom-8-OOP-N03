package com.qlhs.qlhs;
//
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
//
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
//
import javafx.scene.layout.GridPane;
import java.io.FileReader;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class TTController {
//
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
//
//
//    @FXML
//    private TextField nguVan;
//    @FXML
//    private TextField toan;
//    @FXML
//    private TextField vatLi;
//    @FXML
//    private TextField hoaHoc;
//    @FXML
//    private TextField sinhHoc;
//    @FXML
//    private TextField lichSu;
//    @FXML
//    private TextField diaLy;
//    @FXML
//    private TextField GDCD;
//    @FXML
//    private TextField ngoaiNgu;
//    @FXML
//    private TextField congNghe;
//    @FXML
//    private TextField tinHoc;
//    @FXML
//    private TextField theDuc;
////
    private static final Map<String, Set<String>> provinceDistrictMap = new HashMap<>();  // Tỉnh -> Các quận/huyện
    private static final Map<String, Set<String>> districtWardMap = new HashMap<>();      // Quận/huyện -> Các xã/phường

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
        bang_CB.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
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
        TTP_CB.setOnAction(event -> updateDistricts());

        // Thêm sự kiện chọn Quận/Huyện để cập nhật Xã/Phường
        QH_CB.setOnAction(event -> updateWards());
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
    private void lamMoiTT(){
//        System.out.println("Làm mới được kích hoạt!");
//        System.out.println("textFie123123ld1: " + hoDem_TF.getText());
        hoDem_TF.clear();
//        System.out.println("textFiel123167567567d1: " + hoDem_TF.getText());
        ten_TF.clear();
        SDT_TF.clear();
        maDinhDanh_TF.clear();
        email_TF.clear();
        lop_TF.clear();
        chiTiet_TF.clear();
        gioiTinh_Btn.setSelected(false);
        ngaySinh_Date.setValue(null);
        TTP_CB.setValue(null);
        QH_CB.setValue(null);
        PX_CB.setValue(null);
    }
    @FXML
    private void themMoi(){
        lamMoiTT();
    }
////
////    @FXML
////    private void lamMoiDiem() {
////        nguVan.clear();
////        toan.clear();
////        vatLi.clear();
////        hoaHoc.clear();
////        sinhHoc.clear();
////        lichSu.clear();
////        diaLy.clear();
////        GDCD.clear();
////        ngoaiNgu.clear();
////        congNghe.clear();
////        tinHoc.clear();
////        theDuc.clear();
////
////    }
    private void loadFXML(String fxmlFile) throws IOException {
        String fxmlPath = "";
        switch (fxmlFile) {
            case "Thông tin học sinh":
                fxmlPath = "thongTinHocSinh.fxml";
                break;
            case "Bảng điểm":
                fxmlPath = "bangDiemView.fxml";
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + fxmlFile);
        }

        System.out.println("Loading FXML: " + fxmlPath); // Debugging line
        if (!fxmlPath.isEmpty()) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
                Stage stage = (Stage) bang_CB.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show(); // Ensure the stage is visible
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
