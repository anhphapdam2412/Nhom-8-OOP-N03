package com.qlhs.qlhs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class QLHSApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StudentView.fxml")));
        primaryStage.setTitle("QLHS AP_DT");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
