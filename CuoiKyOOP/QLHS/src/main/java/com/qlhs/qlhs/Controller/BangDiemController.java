package com.qlhs.qlhs.Controller;
//

import com.qlhs.qlhs.Database.CapNhatDatabase;
import com.qlhs.qlhs.Model.BangDiem;
import com.qlhs.qlhs.Database.BangDiemDAO;
import com.qlhs.qlhs.View.HopThoai;
import javafx.beans.property.SimpleStringProperty;
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
    private TextField diemTb_TF;
    @FXML
    private TextField ghiChuDiem_TF;


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
    private Label nguVan_Lb;
    @FXML
    private Label toan_Lb;
    @FXML
    private Label vatLi_Lb;
    @FXML
    private Label hoaHoc_Lb;
    @FXML
    private Label sinhHoc_Lb;
    @FXML
    private Label lichSu_Lb;
    @FXML
    private Label congNghe_Lb;
    @FXML
    private Label diaLy_Lb;
    @FXML
    private Label GDCD_Lb;
    @FXML
    private Label tinHoc_Lb;
    @FXML
    private Label hanhKiem_Lb;
    @FXML
    private Label ngoaiNgu_Lb;


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
    private TableColumn<BangDiem, String> diemTbColumn;


    public boolean choPhepCapNhat = false;

    @FXML
    private void initialize() {
        // Thêm các lựa chọn vào ChoiceBox
        bang_CB.getItems().addAll("Thông tin học sinh", "Bảng điểm");
        bang_CB.setValue("Bảng điểm");

        hanhKiem_CB.getItems().addAll("T", "K", "TB", "Y", "Kém");
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
        sttColumn.setCellValueFactory(cellData -> {
            // Lấy chỉ số của học sinh trong danh sách
            int index = tableDiemView.getItems().indexOf(cellData.getValue()) + 1;
            return new SimpleStringProperty(String.valueOf(index));
        });
        hienThiBangDiem();
        chonHocSinh();
        hanhKiem_CB.setOnAction(_ -> kiemTraHK());
        maNN_CB.setOnAction(_ -> kiemTraMaNN());
    }

    private void hienThiBangDiem(){
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
        diemTbColumn.setCellValueFactory(new PropertyValueFactory<>("diemTb"));
        maNNColumn.setCellValueFactory(new PropertyValueFactory<>("maNN"));
        hocLucColumn.setCellValueFactory(new PropertyValueFactory<>("hocLuc"));
        hanhKiemColumn.setCellValueFactory(new PropertyValueFactory<>("hanhKiem"));
        ghiChuColumn.setCellValueFactory(new PropertyValueFactory<>("ghiChuDiem"));

        ObservableList<BangDiem> dsDiem = BangDiemDAO.getBangDiem();

        // Lọc danh sách học sinh có trạng thái là 1
        ObservableList<BangDiem> dsDiemDaLoc = dsDiem.filtered(bangDiem -> Objects.equals(bangDiem.getTrangThai(), "1"));

        // Đặt danh sách đã lọc vào bảng
        tableDiemView.setItems(dsDiemDaLoc);
    }

    private void chonHocSinh() {
        tableDiemView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Nhấp đúp chuột
                BangDiem selectedDiem = tableDiemView.getSelectionModel().getSelectedItem();
                if (selectedDiem != null) {
                    hienDiemChiTiet(selectedDiem);
                }
            }
        });
    }
    private void kiemTraHK() {
        danhSachKiemTraDiem();
    }
    private void kiemTraMaNN() {
        danhSachKiemTraDiem();
    }
    private void hienDiemChiTiet(BangDiem bangDiem) {
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
        theDuc_Btn.setSelected("D".equals(bangDiem.getTheDuc()));
        maNN_CB.setValue(bangDiem.getMaNN());
        hanhKiem_CB.setValue(bangDiem.getHanhKiem());
        maHS_Lb.setText(bangDiem.getMaHS());
        lop_Lb.setText(bangDiem.getLop());
        diemTb_TF.setText(bangDiem.getDiemTb());
        ngaySinh_Lb.setText(bangDiem.getNgaySinh());
        hoTen_Lb.setText(bangDiem.getHoDem()+" "+bangDiem.getTen());
        gioiTinh_Lb.setText("1".equals(bangDiem.getGioiTinh())?"Nam":"Nữ");
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
    private void capNhatDiem(){
        danhSachKiemTraDiem();
        if(maHS_Lb.getText().equals("")){
            HopThoai.baoLoi("Chưa có mã học sinh");
        }
        else if(!choPhepCapNhat){
            HopThoai.baoLoi("Vui lòng điền đầy đủ thông tin");
        }
        else {
            String query = "";
            String maHS = maHS_Lb.getText();
            String nguVan = nguVan_TF.getText();
            String toan = toan_TF.getText();
            String lichSu = lichSu_TF.getText();
            String sinhHoc = sinhHoc_TF.getText();
            String tinHoc = tinHoc_TF.getText();
            String congNghe = congNghe_TF.getText();
            String ghiChuDiem = ghiChuDiem_TF.getText();
            String ngoaiNgu = ngoaiNgu_TF.getText();

            String hoaHoc = hoaHoc_TF.getText();
            String vatLi = vatLi_TF.getText();
            String GDCD = GDCD_TF.getText();

            String diaLy = diaLy_TF.getText();
            String theDuc = theDuc_Btn.isSelected()?"D":"T";
            String maNN = maNN_CB.getValue();
            String hanhKiem = hanhKiem_CB.getValue();

            query = "UPDATE bangdiem SET nguVan = ?, toan = ?, vatLi = ?, hoaHoc = ?, sinhHoc = ?, " +
                    "lichSu = ?, diaLy = ?, GDCD = ?, congNghe = ?, tinHoc = ?, theDuc = ?, " +
                    "ngoaiNgu = ?, maNN = ?, hanhKiem = ?, ghiChuDiem = ? WHERE maHS = ?";

            CapNhatDatabase.capNhatDiem(maHS, nguVan, toan, vatLi, hoaHoc, sinhHoc, lichSu, diaLy, GDCD, congNghe, tinHoc, theDuc, ngoaiNgu, maNN, hanhKiem, ghiChuDiem, query);
        }

        hienThiBangDiem();
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
        danhSachKiemTraDiem();
    }
    private void danhSachKiemTraDiem() {
        KiemTraDuLieuNhap.validateField(nguVan_TF.getText(), nguVan_Lb, KiemTraDuLieuNhap::isValidDiem);
        KiemTraDuLieuNhap.validateField(toan_TF.getText(), toan_Lb, KiemTraDuLieuNhap::isValidDiem);
        KiemTraDuLieuNhap.validateField(vatLi_TF.getText(), vatLi_Lb, KiemTraDuLieuNhap::isValidDiem);
        KiemTraDuLieuNhap.validateField(hoaHoc_TF.getText(), hoaHoc_Lb, KiemTraDuLieuNhap::isValidDiem);
        KiemTraDuLieuNhap.validateField(sinhHoc_TF.getText(), sinhHoc_Lb, KiemTraDuLieuNhap::isValidDiem);
        KiemTraDuLieuNhap.validateField(diaLy_TF.getText(), diaLy_Lb, KiemTraDuLieuNhap::isValidDiem);
        KiemTraDuLieuNhap.validateField(lichSu_TF.getText(), lichSu_Lb, KiemTraDuLieuNhap::isValidDiem);
        KiemTraDuLieuNhap.validateField(GDCD_TF.getText(), GDCD_Lb, KiemTraDuLieuNhap::isValidDiem);
        KiemTraDuLieuNhap.validateField(congNghe_TF.getText(), congNghe_Lb, KiemTraDuLieuNhap::isValidDiem);
        KiemTraDuLieuNhap.validateField(tinHoc_TF.getText(), tinHoc_Lb, KiemTraDuLieuNhap::isValidDiem);
        KiemTraDuLieuNhap.validateField(maNN_CB.getValue(),ngoaiNgu_Lb, KiemTraDuLieuNhap::isValidComboBox);
        KiemTraDuLieuNhap.validateField(ngoaiNgu_TF.getText(), ngoaiNgu_Lb, KiemTraDuLieuNhap::isValidDiem);
        KiemTraDuLieuNhap.validateField(hanhKiem_CB.getValue(), hanhKiem_Lb, KiemTraDuLieuNhap::isValidComboBox);

        if(
            !KiemTraDuLieuNhap.validateField(nguVan_TF.getText(), nguVan_Lb, KiemTraDuLieuNhap::isValidDiem)||
                    !KiemTraDuLieuNhap.validateField(toan_TF.getText(), toan_Lb, KiemTraDuLieuNhap::isValidDiem)||
                    !KiemTraDuLieuNhap.validateField(vatLi_TF.getText(), vatLi_Lb, KiemTraDuLieuNhap::isValidDiem)||
                    !KiemTraDuLieuNhap.validateField(hoaHoc_TF.getText(), hoaHoc_Lb, KiemTraDuLieuNhap::isValidDiem)||
                    !KiemTraDuLieuNhap.validateField(sinhHoc_TF.getText(), sinhHoc_Lb, KiemTraDuLieuNhap::isValidDiem)||
                    !KiemTraDuLieuNhap.validateField(diaLy_TF.getText(), diaLy_Lb, KiemTraDuLieuNhap::isValidDiem)||
                    !KiemTraDuLieuNhap.validateField(lichSu_TF.getText(), lichSu_Lb, KiemTraDuLieuNhap::isValidDiem)||
                    !KiemTraDuLieuNhap.validateField(GDCD_TF.getText(), GDCD_Lb, KiemTraDuLieuNhap::isValidDiem)||
                    !KiemTraDuLieuNhap.validateField(congNghe_TF.getText(), congNghe_Lb, KiemTraDuLieuNhap::isValidDiem)||
                    !KiemTraDuLieuNhap.validateField(tinHoc_TF.getText(), tinHoc_Lb, KiemTraDuLieuNhap::isValidDiem)||
                    !KiemTraDuLieuNhap.validateField(maNN_CB.getValue(),ngoaiNgu_Lb, KiemTraDuLieuNhap::isValidComboBox)||
                    !KiemTraDuLieuNhap.validateField(ngoaiNgu_TF.getText(), ngoaiNgu_Lb, KiemTraDuLieuNhap::isValidDiem)||
                    !KiemTraDuLieuNhap.validateField(hanhKiem_CB.getValue(), hanhKiem_Lb, KiemTraDuLieuNhap::isValidComboBox)
        ){
            choPhepCapNhat = false;
        }else{
            choPhepCapNhat = true;
        }
    }

}

