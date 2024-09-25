package com.qlhs.qlhs.Controller;

import javafx.scene.control.Alert;

public class HopThoai {
    public static void baoLoi(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null); // Không cần tiêu đề
        alert.setContentText(message); // Nội dung thông báo

        alert.showAndWait(); // Hiển thị hộp thoại và chờ người dùng phản hồi
    }
}
