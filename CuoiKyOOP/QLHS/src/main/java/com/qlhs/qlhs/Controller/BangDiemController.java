package com.qlhs.qlhs.Controller;
//

import com.qlhs.qlhs.Model.BangDiem;
import com.qlhs.qlhs.Model.BangDiemDAO;
import com.qlhs.qlhs.KiemTraDuLieuNhap;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;


public class BangDiemController {

    @FXML
    private TextField nguVan_TF;
    @FXML
    private TextField toan_TF;
    @FXML
    private TextField vatLi_TF;
    @FXML
    private TextField hoaHoc_TF;
    @FXML
    private TextField sinhHoc_TF;
    @FXML
    private TextField lichSu_TF;
    @FXML
    private TextField diaLy_TF;
    @FXML
    private TextField GDCD_TF;
    @FXML
    private TextField ngoaiNgu_TF;
    @FXML
    private TextField congNghe_TF;
    @FXML
    private TextField tinHoc_TF;
    @FXML
    private RadioButton theDuc_Btn;
    @FXML
    private ComboBox<String> hanhKiem_CB;
    @FXML
    private ComboBox<String> maNN_CB;


    @FXML
    private Label maHS_Lb;
    @FXML
    private Label hoTen_Lb;
    @FXML
    private Label gioiTinh_Lb;
    @FXML
    private Label ngaySinh_Lb;
    @FXML
    private Label lop_Lb;

    @FXML
    private Label diemNguVanHopLe;
    @FXML
    private Label diemToanHopLe;
    @FXML
    private Label diemVatLiHopLe;
    @FXML
    private Label diemHoaHocHopLe;
    @FXML
    private Label diemSinhHocHopLe;
    @FXML
    private Label diemLichSuHopLe;
    @FXML
    private Label diemDiaLyHopLe;
    @FXML
    private Label diemGDCDHopLe;
    @FXML
    private Label diemCongNgheHopLe;
    @FXML
    private Label diemTinHocHopLe;
    @FXML
    private Label diemNgoaiNguHopLe;


    @FXML
    private ComboBox<String> bang_CB;


    @FXML
    private TableView<BangDiem> tableDiemView;
    @FXML
    private TableColumn<BangDiem, String> sttColumn;
    @FXML
    private TableColumn<BangDiem, String> maHSColumn;
    @FXML
    private TableColumn<BangDiem, String> hoDemColumn;
    @FXML
    private TableColumn<BangDiem, String> tenColumn;
    @FXML
    private TableColumn<BangDiem, String> ngaySinhColumn;
    @FXML
    private TableColumn<BangDiem, String> gioiTinhColumn;
    @FXML
    private TableColumn<BangDiem, String> lopColumn;
    @FXML
    private TableColumn<BangDiem, String> ngoaiNguColumn;
    @FXML
    private TableColumn<BangDiem, String> congNgheColumn;
    @FXML
    private TableColumn<BangDiem, String> tinHocColumn;
    @FXML
    private TableColumn<BangDiem, String> theDucColumn;
    @FXML
    private TableColumn<BangDiem, String> diaLyColumn;
    @FXML
    private TableColumn<BangDiem, String> nguVanColumn;
    @FXML
    private TableColumn<BangDiem, String> toanColumn;
    @FXML
    private TableColumn<BangDiem, String> vatLiColumn;
    @FXML
    private TableColumn<BangDiem, String> sinhHocColumn;
    @FXML
    private TableColumn<BangDiem, String> lichSuColumn;
    @FXML
    private TableColumn<BangDiem, String> ghiChuColumn;
    @FXML
    private TableColumn<BangDiem, String> GDCDColumn;
    @FXML
    private TableColumn<BangDiem, String> hoaHocColumn;
    @FXML
    private TableColumn<BangDiem, String> hocLucColumn;
    @FXML
    private TableColumn<BangDiem, String> hanhKiemColumn;
    @FXML
    private TableColumn<BangDiem, String> maNNColumn;

    @FXML
    private void initialize() {
        // Thêm các lựa chọn vào ChoiceBox
        bang_CB.getItems().addAll("Thông tin học sinh", "Bảng điểm");
        bang_CB.setValue("Bảng điểm");

        hanhKiem_CB.getItems().addAll("T", "K", "TB", "Y", "K");
        maNN_CB.getItems().addAll("N1", "N2", "N3");
        // Lắng nghe sự thay đổi lựa chọn trong ChoiceBox
        bang_CB.getSelectionModel().selectedItemProperty().addListener((_, _, newVal) -> {
            try {
                loadFXML(newVal);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // Add listener for mouse click on the table
        // Set up the columns to use the Mark class fields
        sttColumn.setCellValueFactory(new PropertyValueFactory<>("stt"));
        maHSColumn.setCellValueFactory(new PropertyValueFactory<>("maHS"));
        hoDemColumn.setCellValueFactory(new PropertyValueFactory<>("hoDem"));
        tenColumn.setCellValueFactory(new PropertyValueFactory<>("ten"));
        ngaySinhColumn.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        gioiTinhColumn.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        lopColumn.setCellValueFactory(new PropertyValueFactory<>("lop"));
        nguVanColumn.setCellValueFactory(new PropertyValueFactory<>("nguVan"));
        toanColumn.setCellValueFactory(new PropertyValueFactory<>("toan"));
        vatLiColumn.setCellValueFactory(new PropertyValueFactory<>("vatLi"));
        hoaHocColumn.setCellValueFactory(new PropertyValueFactory<>("hoaHoc"));
        sinhHocColumn.setCellValueFactory(new PropertyValueFactory<>("sinhHoc"));
        lichSuColumn.setCellValueFactory(new PropertyValueFactory<>("lichSu"));
        diaLyColumn.setCellValueFactory(new PropertyValueFactory<>("diaLy"));
        GDCDColumn.setCellValueFactory(new PropertyValueFactory<>("GDCD"));
        ngoaiNguColumn.setCellValueFactory(new PropertyValueFactory<>("ngoaiNgu"));
        congNgheColumn.setCellValueFactory(new PropertyValueFactory<>("congNghe"));
        tinHocColumn.setCellValueFactory(new PropertyValueFactory<>("tinHoc"));
        theDucColumn.setCellValueFactory(new PropertyValueFactory<>("theDuc"));
        maNNColumn.setCellValueFactory(new PropertyValueFactory<>("maNN"));
        hocLucColumn.setCellValueFactory(new PropertyValueFactory<>("hocLuc"));
        hanhKiemColumn.setCellValueFactory(new PropertyValueFactory<>("hanhKiem"));
        ghiChuColumn.setCellValueFactory(new PropertyValueFactory<>("ghiChuDiem"));

        ObservableList<BangDiem> BangDiems = BangDiemDAO.getBangDiem();
        tableDiemView.setItems(BangDiems);

        tableDiemView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Nhấp đúp chuột
                BangDiem selectedDiem = tableDiemView.getSelectionModel().getSelectedItem();
                if (selectedDiem != null) {
                    displayDiemDetails(selectedDiem);
                }
            }
        });
    }
    private void displayDiemDetails(BangDiem bangDiem) {
        nguVan_TF.setText(bangDiem.getNguVan());
        toan_TF.setText(bangDiem.getToan());
        vatLi_TF.setText(bangDiem.getVatLi());
        hoaHoc_TF.setText(bangDiem.getHoaHoc());
        sinhHoc_TF.setText(bangDiem.getSinhHoc());
        lichSu_TF.setText(bangDiem.getLichSu());
        diaLy_TF.setText(bangDiem.getDiaLy());
        GDCD_TF.setText(bangDiem.getGDCD());
        ngoaiNgu_TF.setText(bangDiem.getNgoaiNgu());
        congNghe_TF.setText(bangDiem.getCongNghe());
        tinHoc_TF.setText(bangDiem.getTinHoc());
        theDuc_Btn.setSelected("Đ".equals(bangDiem.getTheDuc()));
        maNN_CB.setValue(bangDiem.getMaNN());
        hanhKiem_CB.setValue(bangDiem.getHanhKiem());
        maHS_Lb.setText(bangDiem.getMaHS());
        lop_Lb.setText(bangDiem.getLop());
        ngaySinh_Lb.setText(bangDiem.getNgaySinh());
        hoTen_Lb.setText(bangDiem.getHoDem()+" "+bangDiem.getTen());
        gioiTinh_Lb.setText("1".equals(bangDiem.getGioiTinh())?"Nam":"Nữ");
    }
    private void loadFXML(String fxmlFile) throws IOException {
        String fxmlPath = switch (fxmlFile) {
            case "Thông tin học sinh" -> "thongTinHocSinhView.fxml";
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
    private void lamMoiDiem() {
        nguVan_TF.clear();
        toan_TF.clear();
        vatLi_TF.clear();
        hoaHoc_TF.clear();
        sinhHoc_TF.clear();
        lichSu_TF.clear();
        diaLy_TF.clear();
        GDCD_TF.clear();
        ngoaiNgu_TF.clear();
        congNghe_TF.clear();
        tinHoc_TF.clear();
        theDuc_Btn.setSelected(false);
        hanhKiem_CB.setValue(null);
        maNN_CB.setValue(null);
    }
    
    @FXML
    private void handleKeyReleased() {
        validateField(toan_TF, diemToanHopLe, KiemTraDuLieuNhap::isValidDiem);
        validateField(nguVan_TF, diemNguVanHopLe, KiemTraDuLieuNhap::isValidDiem);
        validateField(vatLi_TF, diemVatLiHopLe, KiemTraDuLieuNhap::isValidDiem);
        validateField(hoaHoc_TF, diemHoaHocHopLe, KiemTraDuLieuNhap::isValidDiem);
        validateField(sinhHoc_TF, diemSinhHocHopLe, KiemTraDuLieuNhap::isValidDiem);
        validateField(lichSu_TF, diemLichSuHopLe, KiemTraDuLieuNhap::isValidDiem);
        validateField(diaLy_TF, diemDiaLyHopLe, KiemTraDuLieuNhap::isValidDiem);
        validateField(GDCD_TF, diemGDCDHopLe, KiemTraDuLieuNhap::isValidDiem);
        validateField(congNghe_TF, diemCongNgheHopLe, KiemTraDuLieuNhap::isValidDiem);
        validateField(tinHoc_TF, diemTinHocHopLe, KiemTraDuLieuNhap::isValidDiem);
        validateField(ngoaiNgu_TF, diemNgoaiNguHopLe, KiemTraDuLieuNhap::isValidDiem);
    }
    private void validateField(TextField textField, Label label, BangDiemController.Validator validator) {
        String text = textField.getText();
        if (!text.isEmpty()) {
            if (validator.isValid(text)) {
                label.setStyle("-fx-text-fill: #ffffff;");
            } else {
                label.setStyle("-fx-text-fill: #ff6363;");
            }
        } else {
            label.setStyle("-fx-text-fill: #ffffff;");
        }
    }

    @FunctionalInterface
    private interface Validator {
        boolean isValid(String text);
    }
}

