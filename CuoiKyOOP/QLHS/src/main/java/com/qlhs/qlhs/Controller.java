package com.qlhs.qlhs;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Controller {

    @FXML
    private ChoiceBox<String> provinceChoiceBox;

    @FXML
    private ChoiceBox<String> districtChoiceBox;

    @FXML
    private ChoiceBox<String> wardChoiceBox;

    private Map<String, String> districtsMap = new HashMap<>();
    private Map<String, String> wardsMap = new HashMap<>();

    @FXML
    public void initialize() {
        loadDataFromCSV();
    }

    private void loadDataFromCSV() {
        try (InputStream inputStream = getClass().getResourceAsStream("/provinces.csv");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Bỏ qua dòng tiêu đề
                if (line.startsWith("Tỉnh Thành Phố")) {
                    continue;
                }

                // Phân tách các phần tử trong dòng CSV
                String[] parts = line.split(",");
                if (parts.length < 7) continue; // Đảm bảo có đủ dữ liệu

                String province = parts[0];
                String district = parts[2];
                String ward = parts[4];

                // Thêm tỉnh/thành phố vào ChoiceBox
                if (!provinceChoiceBox.getItems().contains(province)) {
                    provinceChoiceBox.getItems().add(province);
                }

                // Thêm quận/huyện và phường/xã vào bản đồ
                districtsMap.put(district, province);
                wardsMap.put(ward, district);
            }

            // Lắng nghe sự kiện chọn tỉnh/thành phố
            provinceChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
                updateDistricts(newValue);
            });

            // Lắng nghe sự kiện chọn quận/huyện
            districtChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
                updateWards(newValue);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateDistricts(String province) {
        districtChoiceBox.getItems().clear();
        for (Map.Entry<String, String> entry : districtsMap.entrySet()) {
            if (entry.getValue().equals(province)) {
                if (!districtChoiceBox.getItems().contains(entry.getKey())) {
                    districtChoiceBox.getItems().add(entry.getKey());
                }
            }
        }
        districtChoiceBox.setValue(null); // Reset giá trị quận/huyện
        wardChoiceBox.getItems().clear(); // Xóa phường/xã
    }

    private void updateWards(String district) {
        wardChoiceBox.getItems().clear();
        for (Map.Entry<String, String> entry : wardsMap.entrySet()) {
            if (entry.getValue().equals(district)) {
                if (!wardChoiceBox.getItems().contains(entry.getKey())) {
                    wardChoiceBox.getItems().add(entry.getKey());
                }
            }
        }
        wardChoiceBox.setValue(null); // Reset giá trị phường/xã
    }


}
