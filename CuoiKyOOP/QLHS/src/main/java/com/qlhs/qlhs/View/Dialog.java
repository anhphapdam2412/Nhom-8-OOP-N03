package com.qlhs.qlhs.View;

import javafx.scene.control.Alert;

public class Dialog {
    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setHeaderText("Error"); // No need for title
        alert.setContentText(message); // Notification content

        alert.showAndWait(); // Show the dialog and wait for user response
    }
}
