package com.qlhs.qlhs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;

public class bangDiemController {
    @FXML
    private ChoiceBox<String> bang_CB;
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
