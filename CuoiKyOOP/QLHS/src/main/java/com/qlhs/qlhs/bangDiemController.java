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
    private ChoiceBox<String> bang_CB;

    @FXML
    private TableView<Mark> tableDiemView;
    @FXML
    private TableColumn<Mark, String> ngoaiNguColumn;
    @FXML
    private TableColumn<Mark, String> congNgheColumn;
    @FXML
    private TableColumn<Mark, String> tinHocColumn;
    @FXML
    private TableColumn<Mark, String> theDucColumn;
    @FXML
    private TableColumn<Mark, String> diaLyColumn;
    @FXML
    private TableColumn<Mark, String> nguVanColumn;
    @FXML
    private TableColumn<Mark, String> toanColumn;
    @FXML
    private TableColumn<Mark, String> vatLiColumn;
    @FXML
    private TableColumn<Mark, String> sinhHocColumn;
    @FXML
    private TableColumn<Mark, String> lichSuColumn;
    @FXML
    private TableColumn<Mark, String> ghiChuDiemColumn;
    @FXML
    private TableColumn<Mark, String> GDCDColumn;
    @FXML
    private TableColumn<Mark, String> hoaHocColumn;

    @FXML
    private void initialize() {
//
//
        // Thêm các lựa chọn vào ChoiceBox
        bang_CB.getItems().addAll("Thông tin học sinh", "Bảng điểm");
        bang_CB.setValue("Bảng điểm");

        // Lắng nghe sự thay đổi lựa chọn trong ChoiceBox
        bang_CB.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            try {
                loadFXML(newVal);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
        ghiChuDiemColumn.setCellValueFactory(new PropertyValueFactory<>("ghiChuDiem"));
        //
        ObservableList<Mark> marks = MarkDAO.getMark();
        tableDiemView.setItems(marks);

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

}
