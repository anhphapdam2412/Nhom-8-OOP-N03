package com.qlhs.qlhs.View;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Dialog {
    // Thông báo lỗi
    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Lỗi");
        alert.setContentText(message);

        alert.showAndWait();
    }

    // Thông báo xác nhận xóa
    public static boolean showDeleteConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText("Xác nhận xóa");
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
