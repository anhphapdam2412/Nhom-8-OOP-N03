package com.qlhs.qlhs.Controller;
//

import com.qlhs.qlhs.Database.UpdateDatabase;
import com.qlhs.qlhs.Database.StudentDAO;
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

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class StudentController {

    private static final Map<String, Set<String>> provinceDistrictMap = new TreeMap<>(); // Tỉnh -> Quận/huyện
    private static final Map<String, Set<String>> districtWardMap = new TreeMap<>();      // Quận/huyện -> Xã/phường
    public boolean allowUpdate = false;
    public List<Integer> studentIDs = new ArrayList<>();

    // Các TextField
    @FXML private TextField studentId_TF, lastName_TF, firstName_TF, phoneNumber_TF, ID_TF, email_TF, className_TF, detail_TF, notes_TF, search_TF;
    @FXML private RadioButton male_Btn, saveProvinces, saveDistrict, saveWard, saveClass, saveBirthdate, female_Btn;



    // Birthdate and ComboBox
    @FXML private DatePicker dateOfBirth_Picker;
    @FXML private ComboBox<String> province_CB, district_CB, ward_CB, table_CB;

    // Buttons and Labels
    @FXML private Button addNew_Btn, delete_Btn;
    @FXML private Label gender_Lb, phoneNumber_Lb, ID_Lb, lastName_Lb, firstName_Lb, studentId_Lb, className_Lb, province_Lb, district_Lb, ward_Lb, dateOfBirth_Lb;

    // TableView and TableColumn
    @FXML private TableView<Student> studentTableView;
    @FXML private TableColumn<Student, String> noColumn, studentIdColumn, lastNameColumn, firstNameColumn, dateOfBirthColumn, genderColumn, IDColumn, phoneNumberColumn, emailColumn, classNameColumn, addressColumn, notesColumn;

    private Timeline debounce;

    @FXML
    private void initialize() {
//

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

        // Tạo ToggleGroup để chỉ cho phép chọn một nút
        ToggleGroup group = new ToggleGroup();
        male_Btn.setToggleGroup(group);
        female_Btn.setToggleGroup(group);



        debounce = new Timeline(new KeyFrame(Duration.millis(300), event -> {
            if (Objects.equals(search_TF.getText(), "")) {
                displayStudentsOnScreen(Search.filter(""));
            } else {
                displayStudentsOnScreen(Search.filter(search_TF.getText()));
            }
        }));
        debounce.setCycleCount(1);

        layTTTinhTHanhTuCSV();
        displayStudentsOnScreen(Search.filter(""));
        selectStudent();

    }

    private void displayStudentsOnScreen(ObservableList<Student> query) {
        // Thiết lập các cột
        noColumn.setCellValueFactory(cellData -> {
            // Lấy chỉ số của học sinh trong danh sách
            int index = studentTableView.getItems().indexOf(cellData.getValue()) + 1;
            return new SimpleStringProperty(String.valueOf(index));
        });
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
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
        studentTableView.setItems(query);
    }

    // Phương thức để thiết lập sự kiện chuột cho bảng
    private void selectStudent() {
        studentTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Nhấp đúp chuột
                Student selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
                if (selectedStudent != null) {
                    displayStudentDetails(selectedStudent);
                    CheckList();
                    delete_Btn.setDisable(false);
                    addNew_Btn.setDisable(false);
                }
            }
        });
    }

    // Điền thông tin học sinh được chọn từ bảng lên các ô điền thông tin
    private void displayStudentDetails(Student student) {
        studentId_TF.setText(student.getStudentID());
        firstName_TF.setText(student.getFirstName());
        lastName_TF.setText(student.getLastName());
        phoneNumber_TF.setText(student.getPhoneNumber());
        email_TF.setText(student.getEmail());
        className_TF.setText(student.getClassName());
        if ("1".equals(student.getGender())) {
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

        if (addressParts.length >= 4) {
            // Set giá trị cho ComboBox
            province_CB.setValue(addressParts[0]); // Tỉnh
            district_CB.setValue(addressParts[1]);  // Quận/Huyện
            if (!addressParts[2].equals("null")) {
                ward_CB.setValue(addressParts[2]);   // Phường/Xã
            }
            // Set giá trị cho TextField
            if (!addressParts[3].equals("null")) {

                detail_TF.setText(addressParts[3].trim()); // Khu, chi tiết
            }
        } else {
            // Xử lý trường hợp chuỗi không đủ phần
            System.out.println("Địa chỉ không hợp lệ.");
        }
        ID_TF.setText(student.getID());
        notes_TF.setText(student.getNotes());
    }

    //
    public void layTTTinhTHanhTuCSV() {
        String filePath = "../CuoiKyOOP/QLHS/src/main/resources/provinces.csv";  // Đường dẫn tới file CSV

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");  // Giả định CSV sử dụng dấu phẩy
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
        province_CB.setOnAction(_ -> capNhatQH());


        // Thêm sự kiện chọn Quận/Huyện để cập nhật Xã/Phường
        district_CB.setOnAction(_ -> capNhatPX());
    }

    // Cập nhật danh sách Quận/Huyện khi chọn Tỉnh/Thành Phố
    private void capNhatQH() {
        CheckList();
        district_CB.getItems().clear();  // Xóa các quận/huyện hiện tại
        ward_CB.getItems().clear();  // Xóa các xã/phường hiện tại

        String selectedProvince = province_CB.getValue();
        if (selectedProvince != null && provinceDistrictMap.containsKey(selectedProvince)) {
            Set<String> districts = provinceDistrictMap.get(selectedProvince);
            district_CB.getItems().addAll(districts);  // Thêm các quận/huyện tương ứng
        }
    }

    // Cập nhật danh sách Xã/Phường khi chọn Quận/Huyện
    private void capNhatPX() {
        CheckList();
        ward_CB.getItems().clear();  // Xóa các xã/phường hiện tại

        String selectedDistrict = district_CB.getValue();
        if (selectedDistrict != null && districtWardMap.containsKey(selectedDistrict)) {
            Set<String> wards = districtWardMap.get(selectedDistrict);
            ward_CB.getItems().addAll(wards);  // Thêm các xã/phường tương ứng
        }
    }

    @FXML
    private void UpdateStudentInfo() {
        CheckList();
        if (!allowUpdate || studentId_TF.getText().isEmpty()) {
            Dialog.showError("Vui lòng điền đầy đủ thông tin hoặc nhập mã học sinh.");
            return;
        }

        ObservableList<Student> dsStudent = StudentDAO.getStudentList();
        dsStudent.stream().map(Student::getStudentID).map(Integer::parseInt).forEach(studentIDs::add);

        String studentId = studentId_TF.getText();
        String firstName = firstName_TF.getText();
        String lastName= lastName_TF.getText();
        String phoneNumber = phoneNumber_TF.getText();
        String email = email_TF.getText();
        String className = className_TF.getText();
        String address = province_CB.getValue() + ", " + district_CB.getValue() + ", " + ward_CB.getValue() + ", " + (!detail_TF.getText().isEmpty() ? detail_TF.getText() : null);
        String notes = notes_TF.getText();
        String dateOfBirth = String.valueOf(dateOfBirth_Picker.getValue());
        Boolean gender = male_Btn.isSelected();
        String ID = ID_TF.getText();
        String status = "true";

        boolean isUpdate = studentIDs.contains(Integer.parseInt(studentId_TF.getText()));
        String query = isUpdate ? "UPDATE student SET firstName = ?, lastName = ?, dateOfBirth = ?, gender = ?, ID = ?, phoneNumber = ?, email = ?, className = ?, address = ?, notes = ?, status = ? WHERE studentId = ?;"
                : "INSERT INTO student (firstName, lastName, dateOfBirth, gender, ID, phoneNumber, email, className, address, notes, status, studentId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String query2 = !isUpdate ? "INSERT INTO grade (studentId) VALUES (?);" : "";

        UpdateDatabase.updateStudentInfo(studentId, firstName, lastName, dateOfBirth, gender, ID, phoneNumber, email, className, address, notes, status, query, query2);
        displayStudentsOnScreen(Search.filter(""));
        search_TF.setText(null);
        addNew_Btn.setDisable(false);

        ActivityLog.logInformation(studentId + firstName + lastName+ dateOfBirth + gender + ID + phoneNumber + email + className + address + notes + status);
    }

    @FXML
    private void refreshInfo() {
        displayStudentsOnScreen(Search.filter(""));
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

        boolean isUpdate;

        isUpdate = saveBirthdate.isSelected();
        if (!isUpdate) {
            dateOfBirth_Picker.setValue(null);
        }

        isUpdate = saveClass.isSelected();
        if (!isUpdate) {
            className_TF.clear();
        }

        isUpdate = saveProvinces.isSelected();
        if (!isUpdate) {
            province_CB.setValue(null);
        }

        isUpdate = saveDistrict.isSelected();
        if (!isUpdate) {
            district_CB.setValue(null);

        }
        isUpdate = saveWard.isSelected();
        if (!isUpdate) {
            ward_CB.setValue(null);
        }

    }

    @FXML
    private void deleteStudent() {
        if (studentId_TF.getText().equals("23xxxxxx")) {
            Dialog.showError("Chưa có mã học sinh");
        } else {
            String studentId = studentId_TF.getText();
            String status = "false";

            String query = "UPDATE student SET status = ?  WHERE studentId = ?;";

            UpdateDatabase.deleteStudentInfo(studentId, status, query);
            displayStudentsOnScreen(Search.filter(""));
            refreshInfo();
            delete_Btn.setDisable(true);
            studentId_TF.setText("23xxxxxx");
        }

    }

    @FXML
    private void addNewStudent() {
        delete_Btn.setDisable(true);
        refreshInfo();
        ObservableList<Student> Students = StudentDAO.getStudentList();

        LocalDate today = LocalDate.now();

        ConfigReader configReader = new ConfigReader();
        String endOfSchoolYear = configReader.getEndOfSchoolYear();

        int nam = Integer.parseInt(endOfSchoolYear.substring(2, 4));

        LocalDate compareDate = LocalDate.parse(endOfSchoolYear, DateTimeFormatter.ISO_LOCAL_DATE);

        // kiểm tra xem đã tồn tại học sinh nào chưa, nếu chưa thì tạo 1 học sinh giả
        for (Student student : Students) {
            if (student != null) {
                studentIDs.add(Integer.parseInt(student.getStudentID()));
            }
        }
        if (Students.isEmpty()) {
            studentIDs.add(0);
        }
        // So sánh ngày hiện tại với ngày chuỗi
        if (today.isAfter(compareDate)) {
            if (studentIDs.getLast() / 1000000 < nam) { // chia lấy nguyên cho 1000000 để lấy ra năm
                studentIDs.add(nam * 1000000 - 1); // vd mã năm là 24 thì sẽ là 24*1000000-1 = 23999999 để xuống dưới sẽ cộng thêm 1 và ra mã 24000000
                studentIDs.removeFirst();
            }
        }

        studentId_TF.setText(String.valueOf(studentIDs.getLast() + 1));
        CheckList();
        addNew_Btn.setDisable(true);

    }

    private void loadFXML(String fxmlFile) throws IOException {
        String fxmlPath = switch (fxmlFile) {
            case "Thông tin học sinh" -> "/com/qlhs/qlhs/StudentView.fxml";
            case "Bảng điểm" -> "/com/qlhs/qlhs/bangDiemView.fxml";
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

    @FXML
    private void handleKeyReleased() {
        CheckList();
    }

    @FXML
    private void search() {
        // Hủy timeline hiện tại nếu nó đang chạy
        debounce.stop();
        // Khởi động lại timeline, nó sẽ đợi 300ms trước khi thực hiện hành động tìm kiếm
        debounce.playFromStart();
    }

    private void CheckList() {
        DataValidation.validateField(studentId_TF.getText(), studentId_Lb, DataValidation::isValidStudentID);
        DataValidation.validateField(className_TF.getText(), className_Lb, DataValidation::isValidClass);
        DataValidation.validateField(phoneNumber_TF.getText(), phoneNumber_Lb, DataValidation::isValidPhoneNumber);
        DataValidation.validateField(ID_TF.getText(), ID_Lb, DataValidation::isValidID);
        DataValidation.validateField(firstName_TF.getText(), firstName_Lb, DataValidation::isValidName);
        DataValidation.validateField(lastName_TF.getText(), lastName_Lb, DataValidation::isValidName);
        DataValidation.validateField(province_CB.getValue(), province_Lb, DataValidation::isValidComboBox);
        DataValidation.validateField(district_CB.getValue(), district_Lb, DataValidation::isValidComboBox);
        DataValidation.validateField(String.valueOf(male_Btn.isSelected())+String.valueOf(female_Btn.isSelected()),gender_Lb, DataValidation::isValidSex);
//        validateField(ward_CB.getValue(),PX_Lb, DataValidation::isValidPX);
        String dateOfBirth = String.valueOf(dateOfBirth_Picker.getValue());
        DataValidation.validateField(dateOfBirth, dateOfBirth_Lb, DataValidation::isValidBirthOfDate);


        allowUpdate = DataValidation.validateField(studentId_TF.getText(), studentId_Lb, DataValidation::isValidStudentID) &&
                DataValidation.validateField(phoneNumber_TF.getText(), phoneNumber_Lb, DataValidation::isValidPhoneNumber) &&
                DataValidation.validateField(ID_TF.getText(), ID_Lb, DataValidation::isValidID) &&
                DataValidation.validateField(firstName_TF.getText(), firstName_Lb, DataValidation::isValidName) &&
                DataValidation.validateField(lastName_TF.getText(), lastName_Lb, DataValidation::isValidName) &&
                DataValidation.validateField(province_CB.getValue(), province_Lb, DataValidation::isValidComboBox) &&
                DataValidation.validateField(district_CB.getValue(), district_Lb, DataValidation::isValidComboBox) &&
//              !  validateField(ward_CB.getValue(),PX_Lb, DataValidation::isValidPX)||
                DataValidation.validateField(className_TF.getText(), className_Lb, DataValidation::isValidClass) &&
                DataValidation.validateField(String.valueOf(male_Btn.isSelected())+String.valueOf(female_Btn.isSelected()),gender_Lb, DataValidation::isValidSex)&&
                DataValidation.validateField(dateOfBirth, dateOfBirth_Lb, DataValidation::isValidBirthOfDate);
    }
}