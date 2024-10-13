package com.qlhs.qlhs.View;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Dialog {
    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Lỗi"); // No need for title
        alert.setContentText(message); // Notification content

        alert.showAndWait(); // Show the dialog and wait for user response
    }

    public static boolean showDeleteConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText("Xác nhận xóa");
        alert.setContentText(message);

        // Hiển thị hộp thoại và đợi người dùng phản hồi
        Optional<ButtonType> result = alert.showAndWait();

        // Kiểm tra nếu người dùng chọn OK hoặc YES để xác nhận
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
