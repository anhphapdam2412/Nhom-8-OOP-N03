package com.qlhs.qlhs;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.*;

import javafx.scene.layout.GridPane;
import java.io.FileReader;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Controller {

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
    private TextField nguVan;
    @FXML
    private TextField toan;
    @FXML
    private TextField vatLi;
    @FXML
    private TextField hoaHoc;
    @FXML
    private TextField sinhHoc;
    @FXML
    private TextField lichSu;
    @FXML
    private TextField diaLy;
    @FXML
    private TextField DGCD;
    @FXML
    private TextField ngoaiNgu;
    @FXML
    private TextField congNghe;
    @FXML
    private TextField tinHoc;
    @FXML
    private TextField theDuc;




    @FXML
    private GridPane gridPane;

    private Map<String, Set<String>> provinceDistrictMap = new HashMap<>();  // Tỉnh -> Các quận/huyện
    private Map<String, Set<String>> districtWardMap = new HashMap<>();      // Quận/huyện -> Các xã/phường

    @FXML
    private ChoiceBox<String> bang_CB;
    @FXML
    private StackPane stackPane;
    @FXML
    private Pane thongTinHocSinh;
    @FXML
    private Pane bangDiem;
    @FXML
    private void initialize() {
        // Thêm các lựa chọn vào ChoiceBox
        bang_CB.getItems().addAll("Thông tin học sinh", "Bảng điểm");

        // Đặt mặc định cho Pane
        thongTinHocSinh.setVisible(true); // Hoặc false tùy thuộc vào trạng thái mặc định
        bangDiem.setVisible(false);
        bang_CB.setValue("Thông tin học sinh");

        // Lắng nghe sự thay đổi lựa chọn trong ChoiceBox
        bang_CB.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if ("Bảng điểm".equals(newValue)) {
                thongTinHocSinh.setVisible(false);
                bangDiem.setVisible(true);
            } else if ("Thông tin học sinh".equals(newValue)) {
                thongTinHocSinh.setVisible(true);
                bangDiem.setVisible(false);
            }
        });
        loadProvincesFromCSV();

    }
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

    @FXML
    private void lamMoiDiem() {
        nguVan.clear();
        toan.clear();
        vatLi.clear();
        hoaHoc.clear();
        sinhHoc.clear();
        lichSu.clear();
        diaLy.clear();
        DGCD.clear();
        ngoaiNgu.clear();
        congNghe.clear();
        tinHoc.clear();
        theDuc.clear();

    }
}
