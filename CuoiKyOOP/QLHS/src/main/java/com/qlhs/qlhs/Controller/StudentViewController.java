package com.qlhs.qlhs.Controller;

import com.qlhs.qlhs.Model.Student;
import com.qlhs.qlhs.View.Dialog;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class StudentViewController {
    private static final Map<String, Set<String>> provinceDistrictMap = new TreeMap<>(); // Tỉnh -> Quận/huyện
    private static final Map<String, Set<String>> districtWardMap = new TreeMap<>();      // Quận/huyện -> Xã/phường
    public boolean allowUpdate = false;
    @FXML
    private TableColumn<Student, String> noColumn, studentIDColumn, lastNameColumn, firstNameColumn, dateOfBirthColumn, genderColumn, IDColumn, phoneNumberColumn, emailColumn, classNameColumn, addressColumn, notesColumn;
    @FXML
    private RadioButton male_Btn, saveProvinces, saveDistrict, saveWard, saveClass, saveBirthdate, female_Btn;
    // TableView and TableColumn
    @FXML
    private TableView<Student> studentTableView;
    private Timeline debounce;
    // Birthdate and ComboBox
    @FXML
    private DatePicker dateOfBirth_Picker;
    @FXML
    private ComboBox<String> province_CB, district_CB, ward_CB, table_CB;
    // Buttons and Labels
    @FXML
    private Button addNew_Btn, delete_Btn;
    @FXML
    private Label gender_Lb, phoneNumber_Lb, ID_Lb, lastName_Lb, firstName_Lb, studentID_Lb, className_Lb, province_Lb, district_Lb, dateOfBirth_Lb;
    // Các TextField
    @FXML
    private TextField studentID_TF, lastName_TF, firstName_TF, phoneNumber_TF, ID_TF, email_TF, className_TF, detail_TF, notes_TF, search_TF;
    private final SchoolController schoolController;

    public StudentViewController() {
        this.schoolController = SchoolController.getInstance();
    }

    @FXML
    public void initialize() {
        showStudent(Search.filter(""));

        // Tạo ToggleGroup để chỉ cho phép chọn một nút
        ToggleGroup group = new ToggleGroup();
        male_Btn.setToggleGroup(group);
        female_Btn.setToggleGroup(group);

        studentTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("Dòng đã chọn: " + newSelection);
            }
        });
        // Thêm các lựa chọn vào ChoiceBox
        table_CB.getItems().addAll("Thông tin học sinh", "Bảng điểm");
        table_CB.setValue("Thông tin học sinh");

        // Lắng nghe sự thay đổi lựa chọn trong ChoiceBox
        table_CB.getSelectionModel().selectedItemProperty().addListener((_, _, newVal) -> {
            try {
                loadFXML(newVal);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Đếm thời gian nghỉ sau khi nhập điều kiện tìm kiếm
        debounce = new Timeline(new KeyFrame(Duration.millis(300), event -> {
            if (Objects.equals(search_TF.getText(), "")) {
                showStudent(Search.filter(""));
            } else {
                showStudent(Search.filter(search_TF.getText()));
            }
        }));
        debounce.setCycleCount(1);

        selectStudent();
        getProvinces();
    }

    public void getProvinces() {
        String filePath = "../CuoiKyOOP/QLHS/src/main/resources/provinces.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 5) {
                    String province = values[0];  // Cột tỉnh/thành phố
                    String district = values[2];  // Cột quận/huyện
                    String ward = values[4];      // Cột phường/xã

                    // Thêm quận/huyện vào tỉnh/thành phố
                    provinceDistrictMap.putIfAbsent(province, new TreeSet<>());
                    provinceDistrictMap.get(province).add(district);

                    // Thêm xã/phường vào quận/huyện
                    districtWardMap.putIfAbsent(district, new TreeSet<>());
                    districtWardMap.get(district).add(ward);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Thêm các tỉnh thành phố vào ChoiceBox
        province_CB.getItems().addAll(provinceDistrictMap.keySet());
        // Thêm sự kiện chọn tỉnh/thành phố để cập nhật Quận/Huyện
        province_CB.setOnAction(_ -> updateDistrict());
        // Thêm sự kiện chọn Quận/Huyện để cập nhật Xã/Phường
        district_CB.setOnAction(_ -> updateWard());
    }

    // Cập nhật danh sách Quận/Huyện khi chọn Tỉnh/Thành Phố
    private void updateDistrict() {
        checkList();
        district_CB.getItems().clear();  // Xóa các quận/huyện hiện tại
        ward_CB.getItems().clear();  // Xóa các xã/phường hiện tại

        String selectedProvince = province_CB.getValue();
        if (selectedProvince != null && provinceDistrictMap.containsKey(selectedProvince)) {
            Set<String> districts = provinceDistrictMap.get(selectedProvince);
            district_CB.getItems().addAll(districts);  // Thêm các quận/huyện tương ứng
        }
    }

    // Cập nhật danh sách Xã/Phường khi chọn Quận/Huyện
    private void updateWard() {
        checkList();
        ward_CB.getItems().clear();  // Xóa các xã/phường hiện tại

        String selectedDistrict = district_CB.getValue();
        if (selectedDistrict != null && districtWardMap.containsKey(selectedDistrict)) {
            Set<String> wards = districtWardMap.get(selectedDistrict);
            ward_CB.getItems().addAll(wards);  // Thêm các xã/phường tương ứng
        }
    }

    // Mở View bảng học sinh hoặc View bảng điểm
    void loadFXML(String fxmlFile) throws IOException {
        String fxmlPath = switch (fxmlFile) {
            case "Thông tin học sinh" -> "/com/qlhs/qlhs/StudentView.fxml";
            case "Bảng điểm" -> "/com/qlhs/qlhs/AcademicTranscriptView.fxml";
            default -> throw new IllegalArgumentException("Unexpected value: " + fxmlFile);
        };

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            Stage stage = (Stage) table_CB.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show(); // Ensure the stage is visible
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showStudent(ObservableList<Student> students) {
        // Thiết lập các cột
        noColumn.setCellValueFactory(cellData -> {
            int index = studentTableView.getItems().indexOf(cellData.getValue()) + 1;
            return new SimpleStringProperty(String.valueOf(index));
        });
        studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        genderColumn.setCellValueFactory(cellData -> {
            Boolean genderValue = cellData.getValue().getGender();
            String genderText = Objects.equals(genderValue, false) ? "Nữ" : "Nam";
            return new javafx.beans.property.SimpleStringProperty(genderText);
        });
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));

        addressColumn.setCellValueFactory(cellData -> {
            String address = cellData.getValue().getAddress();
            if (address != null) {
                address = address.replace(", null", "");
            }
            return new SimpleStringProperty(address);
        });
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));

        // Đặt danh sách đã lọc vào bảng
        studentTableView.setItems(students);
    }

    @FXML
    private void addNewStudent() {
        studentTableView.setDisable(true);
        table_CB.setDisable(true);
        delete_Btn.setDisable(true);
        addNew_Btn.setDisable(true);
        studentID_TF.setText(schoolController.addNewStudent());
        search_TF.setText(null);
        search_TF.setDisable(true);

        refreshInfo();
        checkList();
    }

    // Kiểm tra điều kiện nhập ngay khi gõ 1 ký tự
    @FXML
    private void handleKeyReleased() {
        checkList();
    }

    @FXML
    private void deleteStudent() {
        if (studentID_TF.getText().equals("23xxxxxx")) {
            Dialog.showError("Chưa có mã học sinh");
        } else {
            boolean confirmed = Dialog.showDeleteConfirmation("Bạn có chắc chắn muốn xóa không?");
            if (confirmed) {
                schoolController.deleteStudent(studentID_TF.getText());

                refreshInfo();
                showStudent(Search.filter(""));

                delete_Btn.setDisable(true);
                studentID_TF.setText("23xxxxxx");
            }
        }
    }

    // Điền thông tin học sinh được chọn từ bảng lên các ô điền thông tin
    private void showStudentDetails(Student student) {
        studentID_TF.setText(student.getStudentID());
        firstName_TF.setText(student.getFirstName());
        lastName_TF.setText(student.getLastName());
        phoneNumber_TF.setText(student.getPhoneNumber());
        email_TF.setText(student.getEmail());
        className_TF.setText(student.getClassName());
        if (student.getGender()) {
            male_Btn.setSelected(true);
        } else {
            female_Btn.setSelected(true);
        }
        String dateOfBirthStr = student.getDateOfBirth();
        if (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr);
            dateOfBirth_Picker.setValue(dateOfBirth);
        } else {
            dateOfBirth_Picker.setValue(null); // Hoặc thiết lập một giá trị mặc định
        }
        String address = student.getAddress();
        String[] addressParts = address.split(",\\s*"); // Tách chuỗi theo dấu phẩy và khoảng trắng

        // Set giá trị cho các ô địa chỉ
        if (addressParts.length >= 4) {
            province_CB.setValue(addressParts[0]); // Tỉnh
            district_CB.setValue(addressParts[1]);  // Quận/Huyện
            if (!addressParts[2].equals("null")) {
                ward_CB.setValue(addressParts[2]);   // Phường/Xã
            }
            if (!addressParts[3].equals("null")) {
                detail_TF.setText(addressParts[3].trim()); // Khu, chi tiết
            }
        }
        ID_TF.setText(student.getID());
        notes_TF.setText(student.getNotes());
    }

    @FXML
    private void UpdateStudentInfo() {
        checkList();
        if (!allowUpdate || studentID_TF.getText().isEmpty()) {
            Dialog.showError("Vui lòng điền đầy đủ thông tin hoặc nhập mã học sinh.");
            return;
        }

        // Lấy dữ liệu từ các TextField
        String studentID = studentID_TF.getText();
        String firstName = firstName_TF.getText();
        String lastName = lastName_TF.getText();
        String phoneNumber = phoneNumber_TF.getText();
        String email = email_TF.getText();
        String className = className_TF.getText();
        String address = province_CB.getValue() + ", " + district_CB.getValue() + ", " + ward_CB.getValue() + ", " + (!detail_TF.getText().isEmpty() ? detail_TF.getText() : null);
        String notes = notes_TF.getText();
        String dateOfBirth = String.valueOf(dateOfBirth_Picker.getValue());
        Boolean gender = male_Btn.isSelected();
        String ID = ID_TF.getText();
        Boolean status = true;

        // Tạo một đối tượng Student mới từ các trường nhập liệu
        Student newStudent = new Student(studentID, firstName, lastName, dateOfBirth, gender, ID, phoneNumber, email, className, address, notes, status);

        schoolController.UpdateStudentInfo(newStudent);

        showStudent(Search.filter(""));

        search_TF.setText(null);
        addNew_Btn.setDisable(true);
        search_TF.setDisable(false);

        studentTableView.setDisable(false);
        table_CB.setDisable(false);
        delete_Btn.setDisable(false);
    }

    @FXML
    private void refreshInfo() {
        firstName_TF.clear();
        lastName_TF.clear();
        phoneNumber_TF.clear();
        ID_TF.clear();
        email_TF.clear();
        detail_TF.clear();
        male_Btn.setSelected(false);
        notes_TF.clear();
        search_TF.clear();
        female_Btn.setSelected(false);

        boolean isSave;
        isSave = saveBirthdate.isSelected();
        if (!isSave) {
            dateOfBirth_Picker.setValue(null);
        }
        isSave = saveClass.isSelected();
        if (!isSave) {
            className_TF.clear();
        }
        isSave = saveProvinces.isSelected();
        if (!isSave) {
            province_CB.setValue(null);
        }
        isSave = saveDistrict.isSelected();
        if (!isSave) {
            district_CB.setValue(null);
        }
        isSave = saveWard.isSelected();
        if (!isSave) {
            ward_CB.setValue(null);
        }
        checkList();
    }

    @FXML
    private void search() {
        // Hủy timeline hiện tại nếu nó đang chạy
        debounce.stop();
        // Khởi động lại timeline, nó sẽ đợi 300ms trước khi thực hiện hành động tìm kiếm
        debounce.playFromStart();
    }

    // Chọn học sinh từ TableView
    private void selectStudent() {
        studentTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Student selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
                if (selectedStudent != null) {
                    showStudentDetails(selectedStudent);
                    checkList();
                    delete_Btn.setDisable(false);
                    addNew_Btn.setDisable(false);
                }
            }
        });
    }

    // Kiểm tra điều kiện nhập
    private void checkList() {
        DataValidation.validateField(studentID_TF.getText(), studentID_Lb, DataValidation::isValidStudentID);
        DataValidation.validateField(className_TF.getText(), className_Lb, DataValidation::isValidClass);
        DataValidation.validateField(phoneNumber_TF.getText(), phoneNumber_Lb, DataValidation::isValidPhoneNumber);
        DataValidation.validateField(ID_TF.getText(), ID_Lb, DataValidation::isValidID);
        DataValidation.validateField(firstName_TF.getText(), firstName_Lb, DataValidation::isValidName);
        DataValidation.validateField(lastName_TF.getText(), lastName_Lb, DataValidation::isValidName);
        DataValidation.validateField(province_CB.getValue(), province_Lb, DataValidation::isValidComboBox);
        DataValidation.validateField(district_CB.getValue(), district_Lb, DataValidation::isValidComboBox);
        DataValidation.validateField(String.valueOf(male_Btn.isSelected() || female_Btn.isSelected()), gender_Lb, DataValidation::isValidSex);
        DataValidation.validateField(String.valueOf(dateOfBirth_Picker.getValue()), dateOfBirth_Lb, DataValidation::isValidBirthOfDate);

        allowUpdate = DataValidation.validateField(studentID_TF.getText(), studentID_Lb, DataValidation::isValidStudentID) &&
                DataValidation.validateField(phoneNumber_TF.getText(), phoneNumber_Lb, DataValidation::isValidPhoneNumber) &&
                DataValidation.validateField(ID_TF.getText(), ID_Lb, DataValidation::isValidID) &&
                DataValidation.validateField(firstName_TF.getText(), firstName_Lb, DataValidation::isValidName) &&
                DataValidation.validateField(lastName_TF.getText(), lastName_Lb, DataValidation::isValidName) &&
                DataValidation.validateField(province_CB.getValue(), province_Lb, DataValidation::isValidComboBox) &&
                DataValidation.validateField(district_CB.getValue(), district_Lb, DataValidation::isValidComboBox) &&
                DataValidation.validateField(className_TF.getText(), className_Lb, DataValidation::isValidClass) &&
                DataValidation.validateField(String.valueOf(male_Btn.isSelected() || female_Btn.isSelected()), gender_Lb, DataValidation::isValidSex) &&
                DataValidation.validateField(String.valueOf(dateOfBirth_Picker.getValue()), dateOfBirth_Lb, DataValidation::isValidBirthOfDate);
    }
}