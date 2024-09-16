package com.qlhs.qlhs;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Controller {

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
            } else if ("Thông tin học sinh".equals(newValue)){
                thongTinHocSinh.setVisible(true);
                bangDiem.setVisible(false);
            }
        });
    }
}
