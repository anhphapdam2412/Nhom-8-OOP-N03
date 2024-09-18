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


public class bangDiemController {

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
    private ChoiceBox<String> bang_CB;

    @FXML
    private TableView<bangDiem> tableDiemView;
    @FXML
    private TableColumn<bangDiem, String> sttColumn;
    @FXML
    private TableColumn<bangDiem, String> maHSColumn;
    @FXML
    private TableColumn<bangDiem, String> hoDemColumn;
    @FXML
    private TableColumn<bangDiem, String> tenColumn;
    @FXML
    private TableColumn<bangDiem, String> ngaySinhColumn;
    @FXML
    private TableColumn<bangDiem, String> gioiTinhColumn;
    @FXML
    private TableColumn<bangDiem, String> maDinhDanhColumn;
    @FXML
    private TableColumn<bangDiem, String> ngoaiNguColumn;
    @FXML
    private TableColumn<bangDiem, String> congNgheColumn;
    @FXML
    private TableColumn<bangDiem, String> tinHocColumn;
    @FXML
    private TableColumn<bangDiem, String> theDucColumn;
    @FXML
    private TableColumn<bangDiem, String> diaLyColumn;
    @FXML
    private TableColumn<bangDiem, String> nguVanColumn;
    @FXML
    private TableColumn<bangDiem, String> toanColumn;
    @FXML
    private TableColumn<bangDiem, String> vatLiColumn;
    @FXML
    private TableColumn<bangDiem, String> sinhHocColumn;
    @FXML
    private TableColumn<bangDiem, String> lichSuColumn;
    @FXML
    private TableColumn<bangDiem, String> ghiChuColumn;
    @FXML
    private TableColumn<bangDiem, String> GDCDColumn;
    @FXML
    private TableColumn<bangDiem, String> hoaHocColumn;
    @FXML
    private TableColumn<bangDiem, String> hocLucColumn;
    @FXML
    private TableColumn<bangDiem, String> hanhKiemColumn;
    @FXML
    private TableColumn<bangDiem, String> maNNColumn;

    @FXML
    private void initialize() {
//
//
        // Thêm các lựa chọn vào ChoiceBox
        bang_CB.getItems().addAll("Thông tin học sinh", "Bảng điểm");
        bang_CB.setValue("Bảng điểm");

        hanhKiem_CB.getItems().addAll("Tốt", "Khá", "Trung Bình", "Yếu", "Kém");
        maNN_CB.getItems().addAll("N1", "N2", "N3");
        // Lắng nghe sự thay đổi lựa chọn trong ChoiceBox
        bang_CB.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            try {
                loadFXML(newVal);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Set up the columns to use the Mark class fields
        sttColumn.setCellValueFactory(new PropertyValueFactory<>("stt"));
        maHSColumn.setCellValueFactory(new PropertyValueFactory<>("maHS"));
        hoDemColumn.setCellValueFactory(new PropertyValueFactory<>("hoDem"));
        tenColumn.setCellValueFactory(new PropertyValueFactory<>("ten"));
        ngaySinhColumn.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        gioiTinhColumn.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        maDinhDanhColumn.setCellValueFactory(new PropertyValueFactory<>("maDinhDanh"));
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
        //
        ObservableList<bangDiem> bangDiems = bangDiemDAO.getBangDiem();
        tableDiemView.setItems(bangDiems);

    }

    private void loadFXML(String fxmlFile) throws IOException {
        String fxmlPath = "";
        switch (fxmlFile) {
            case "Thông tin học sinh":
                fxmlPath = "thongTinHocSinhView.fxml";

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
}
