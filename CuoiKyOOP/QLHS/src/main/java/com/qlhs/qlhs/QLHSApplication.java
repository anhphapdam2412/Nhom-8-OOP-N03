package com.qlhs.qlhs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class QLHSApplication extends Application {

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("thongTinHocSinhView.fxml"));
        primaryStage.setTitle("QLHS AP_AT");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
