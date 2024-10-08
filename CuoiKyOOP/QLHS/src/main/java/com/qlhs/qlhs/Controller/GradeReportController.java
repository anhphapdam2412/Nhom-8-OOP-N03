package com.qlhs.qlhs.Controller;

import com.qlhs.qlhs.Database.GradeReportDAO;
import com.qlhs.qlhs.Database.UpdateDatabase;
import com.qlhs.qlhs.Model.GradeReport;
import com.qlhs.qlhs.View.Dialog;
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

import java.io.IOException;
import java.util.Objects;

public class GradeReportController {

    @FXML
    private TextField literature_TF;
    @FXML
    private TextField math_TF;
    @FXML
    private TextField physics_TF;
    @FXML
    private TextField chemistry_TF;
    @FXML
    private TextField biology_TF;
    @FXML
    private TextField history_TF;
    @FXML
    private TextField geography_TF;
    @FXML
    private TextField civicEdu_TF;
    @FXML
    private TextField foreignLanguage_TF;
    @FXML
    private TextField technology_TF;
    @FXML
    private TextField it_TF;
    @FXML
    private RadioButton physicalEducation_Btn;
    @FXML
    private ComboBox<String> conduct_CB;
    @FXML
    private ComboBox<String> languageCode_CB;
    @FXML
    private TextField avgGrade_TF;
    @FXML
    private TextField gradeNotes_TF;
    @FXML
    private TextField search_TF;


    @FXML
    private Label studentID_Lb;
    @FXML
    private Label fullName_Lb;
    @FXML
    private Label gender_Lb;
    @FXML
    private Label dateOfBirth_Lb;
    @FXML
    private Label className_Lb;

    @FXML
    private Label literature_Lb;
    @FXML
    private Label math_Lb;
    @FXML
    private Label physics_Lb;
    @FXML
    private Label chemistry_Lb;
    @FXML
    private Label biology_Lb;
    @FXML
    private Label history_Lb;
    @FXML
    private Label technology_Lb;
    @FXML
    private Label geography_Lb;
    @FXML
    private Label civicEdu_Lb;
    @FXML
    private Label it_Lb;
    @FXML
    private Label conduct_Lb;
    @FXML
    private Label foreignLanguage_Lb;


    @FXML
    private ComboBox<String> table_CB;


    @FXML
    private TableView<GradeReport> gradeReportTableView;
    @FXML
    private TableColumn<GradeReport, String> noColumn;
    @FXML
    private TableColumn<GradeReport, String> studentIDColumn;
    @FXML
    private TableColumn<GradeReport, String> firstNameColumn;
    @FXML
    private TableColumn<GradeReport, String> lastNameColumn;
    @FXML
    private TableColumn<GradeReport, String> dateOfBirthColumn;
    @FXML
    private TableColumn<GradeReport, String> genderColumn;
    @FXML
    private TableColumn<GradeReport, String> classNameColumn;
    @FXML
    private TableColumn<GradeReport, String> foreignLanguageColumn;
    @FXML
    private TableColumn<GradeReport, String> technologyColumn;
    @FXML
    private TableColumn<GradeReport, String> itColumn;
    @FXML
    private TableColumn<GradeReport, String> physicalEducationColumn;
    @FXML
    private TableColumn<GradeReport, String> geographyColumn;
    @FXML
    private TableColumn<GradeReport, String> literatureColumn;
    @FXML
    private TableColumn<GradeReport, String> mathColumn;
    @FXML
    private TableColumn<GradeReport, String> physicsColumn;
    @FXML
    private TableColumn<GradeReport, String> biologyColumn;
    @FXML
    private TableColumn<GradeReport, String> historyColumn;
    @FXML
    private TableColumn<GradeReport, String> notesColumn;
    @FXML
    private TableColumn<GradeReport, String> civicEduColumn;
    @FXML
    private TableColumn<GradeReport, String> chemistryColumn;
    @FXML
    private TableColumn<GradeReport, String> academicPerformanceColumn;
    @FXML
    private TableColumn<GradeReport, String> conductColumn;
    @FXML
    private TableColumn<GradeReport, String> languageCodeColumn;
    @FXML
    private TableColumn<GradeReport, String> avgGradeColumn;

    private Timeline debounce;
    public boolean choPhepCapNhat = false;


    @FXML
    private void initialize() {
        // Thêm các lựa chọn vào ChoiceBox
        table_CB.getItems().addAll("Thông tin học sinh", "Bảng điểm");
        table_CB.setValue("Bảng điểm");

        conduct_CB.getItems().addAll("T", "K", "TB", "Y", "Kém");
        languageCode_CB.getItems().addAll("N1", "N2", "N3");
        // Lắng nghe sự thay đổi lựa chọn trong ChoiceBox
        table_CB.getSelectionModel().selectedItemProperty().addListener((_, _, newVal) -> {
            try {
                loadFXML(newVal);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // Add listener for mouse click on the table
        // Set up the columns to use the Mark class fields
        noColumn.setCellValueFactory(cellData -> {
            // Lấy chỉ số của học sinh trong danh sách
            int index = gradeReportTableView.getItems().indexOf(cellData.getValue()) + 1;
            return new SimpleStringProperty(String.valueOf(index));
        });

//        debounce = new Timeline(new KeyFrame(Duration.millis(300), event -> {
//            if (Objects.equals(search_TF.getText(), "")) {
//                showGradeReport(TimKiem.mathBo());
//            } else {
//                showGradeReport(TimKiem.theoMaHS(search_TF.getText()));
//            }
//        }));
//        debounce.setCycleCount(1);

        showGradeReport();
        selectedStudent();
        conduct_CB.setOnAction(_ -> checkconduct());
        languageCode_CB.setOnAction(_ -> checkLanguageCode());
    }

    private void showGradeReport(){
        studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        literatureColumn.setCellValueFactory(new PropertyValueFactory<>("literature"));
        mathColumn.setCellValueFactory(new PropertyValueFactory<>("math"));
        physicsColumn.setCellValueFactory(new PropertyValueFactory<>("physics"));
        chemistryColumn.setCellValueFactory(new PropertyValueFactory<>("chemistry"));
        biologyColumn.setCellValueFactory(new PropertyValueFactory<>("biology"));
        historyColumn.setCellValueFactory(new PropertyValueFactory<>("history"));
        geographyColumn.setCellValueFactory(new PropertyValueFactory<>("geography"));
        civicEduColumn.setCellValueFactory(new PropertyValueFactory<>("civicEdu"));
        foreignLanguageColumn.setCellValueFactory(new PropertyValueFactory<>("foreignLanguage"));
        technologyColumn.setCellValueFactory(new PropertyValueFactory<>("technology"));
        itColumn.setCellValueFactory(new PropertyValueFactory<>("it"));
        physicalEducationColumn.setCellValueFactory(new PropertyValueFactory<>("physicalEducation"));
        avgGradeColumn.setCellValueFactory(new PropertyValueFactory<>("avgGrade"));
        languageCodeColumn.setCellValueFactory(new PropertyValueFactory<>("languageCode"));
        academicPerformanceColumn.setCellValueFactory(new PropertyValueFactory<>("academicPerformance"));
        conductColumn.setCellValueFactory(new PropertyValueFactory<>("conduct"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("gradeNotes"));

        ObservableList<GradeReport> dsDiem = GradeReportDAO.getGradeReport();

        // Lọc danh sách học sinh có trạng thái là 1
        ObservableList<GradeReport> dsDiemDaLoc = dsDiem.filtered(GradeReport -> Objects.equals(GradeReport.getStatus(), "1"));

        // Đặt danh sách đã lọc vào bảng
        gradeReportTableView.setItems(dsDiemDaLoc);
    }

    private void selectedStudent() {
        gradeReportTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Nhấp đúp chuột
                GradeReport selectedDiem = gradeReportTableView.getSelectionModel().getSelectedItem();
                if (selectedDiem != null) {
                    showGradeDetail(selectedDiem);
                }
            }
        });
    }
    private void checkconduct() {
        GradeCheckList();
    }
    private void checkLanguageCode() {
        GradeCheckList();
    }
    private void showGradeDetail(GradeReport GradeReport) {
        literature_TF.setText(String.valueOf(GradeReport.getLiterature()));
        math_TF.setText(String.valueOf(GradeReport.getMaths()));
        physics_TF.setText(String.valueOf(String.valueOf(GradeReport.getPhysics())));
        chemistry_TF.setText(String.valueOf(GradeReport.getChemistry()));
        biology_TF.setText(String.valueOf(GradeReport.getBiology()));
        history_TF.setText(String.valueOf(GradeReport.getHistory()));
        geography_TF.setText(String.valueOf(GradeReport.getGeography()));
        civicEdu_TF.setText(String.valueOf(GradeReport.getCivicEdu()));
        foreignLanguage_TF.setText(String.valueOf(GradeReport.getForeignLang()));
        technology_TF.setText(String.valueOf(GradeReport.getTechnology()));
        it_TF.setText(String.valueOf(GradeReport.getComputerScience()));
        physicalEducation_Btn.setSelected("D".equals(GradeReport.getPhysicalEdu()));
        languageCode_CB.setValue(GradeReport.getLanguageCode());
        conduct_CB.setValue(GradeReport.getConduct());
        studentID_Lb.setText(String.valueOf(GradeReport.getStudentID()));
        className_Lb.setText(String.valueOf(GradeReport.getClassName()));
        avgGrade_TF.setText(String.valueOf(GradeReport.getAverageGrade()));
        dateOfBirth_Lb.setText(String.valueOf(GradeReport.getDateOfBirth()));
        fullName_Lb.setText(String.valueOf(GradeReport.getFirstName()+" "+GradeReport.getLastName()));
        gender_Lb.setText(String.valueOf("1".equals(GradeReport.getGender())?"Nam":"Nữ"));
    }
    private void loadFXML(String fxmlFile) throws IOException {
        String fxmlPath = switch (fxmlFile) {
            case "Thông tin học sinh" -> "/com/qlhs/qlhs/HocSinhView.fxml";
            case "Bảng điểm" -> "/com/qlhs/qlhs/GradeReportView.fxml";
            default -> throw new IllegalArgumentException("Unexpected value: " + fxmlFile);
        };

        System.out.println("Loading FXML: " + fxmlPath); // Debugging line
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
    private void updateGrade(){
        GradeCheckList();
        if(studentID_Lb.getText().equals("")){
            Dialog.showError("Chưa có mã học sinh");
        }
        else if(!choPhepCapNhat){
            Dialog.showError("Vui lòng điền đầy đủ thông tin");
        }
        else {
            String query = "";
            String studentID = studentID_Lb.getText();
            String literature = literature_TF.getText();
            String math = math_TF.getText();
            String history = history_TF.getText();
            String biology = biology_TF.getText();
            String it = it_TF.getText();
            String technology = technology_TF.getText();
            String gradeNotes = gradeNotes_TF.getText();
            String foreignLanguage = foreignLanguage_TF.getText();

            String chemistry = chemistry_TF.getText();
            String physics = physics_TF.getText();
            String civicEdu = civicEdu_TF.getText();

            String geography = geography_TF.getText();
            String physicalEducation = physicalEducation_Btn.isSelected()?"D":"T";
            String languageCode = languageCode_CB.getValue();
            String conduct = conduct_CB.getValue();

            avgGrade_TF.setText(String.format("%.2f",(Float.parseFloat(literature )+ Float.parseFloat(math )+ Float.parseFloat(physics )+Float.parseFloat(chemistry)+Float.parseFloat(biology)+Float.parseFloat(history)+Float.parseFloat(geography)+Float.parseFloat(civicEdu)+Float.parseFloat(technology)+Float.parseFloat(it)+Float.parseFloat(foreignLanguage)) / 11));

            query = "UPDATE grade SET literature = ?, math = ?, physics = ?, chemistry = ?, biology = ?, " +
                    "history = ?, geography = ?, civicEdu = ?, technology = ?, it = ?, physicalEducation = ?, " +
                    "foreignLanguage = ?, languageCode = ?, conduct = ?, gradeNotes = ? WHERE studentID = ?";

            UpdateDatabase.updateGrades(studentID, literature, math, physics, chemistry, biology, history, geography, civicEdu, technology, it, physicalEducation, foreignLanguage, languageCode, conduct, gradeNotes, query);
        }

        showGradeReport();
    }
    @FXML
    private void search(){
        // Hủy timeline hiện tại nếu nó đang chạy
        debounce.stop();
        // Khởi động lại timeline, nó sẽ đợi 300ms trước khi thực hiện hành động tìm kiếm
        debounce.playFromStart();
    }
    @FXML
    private void refreshGrades() {
        literature_TF.clear();
        math_TF.clear();
        physics_TF.clear();
        chemistry_TF.clear();
        biology_TF.clear();
        history_TF.clear();
        geography_TF.clear();
        civicEdu_TF.clear();
        foreignLanguage_TF.clear();
        technology_TF.clear();
        it_TF.clear();
        physicalEducation_Btn.setSelected(false);
        conduct_CB.setValue(null);
        languageCode_CB.setValue(null);
    }

    @FXML
    private void handleKeyReleased() {
        GradeCheckList();
    }
    private void GradeCheckList() {
        DataValidation.validateField(literature_TF.getText(), literature_Lb, DataValidation::isValidGrade);
        DataValidation.validateField(math_TF.getText(), math_Lb, DataValidation::isValidGrade);
        DataValidation.validateField(physics_TF.getText(), physics_Lb, DataValidation::isValidGrade);
        DataValidation.validateField(chemistry_TF.getText(), chemistry_Lb, DataValidation::isValidGrade);
        DataValidation.validateField(biology_TF.getText(), biology_Lb, DataValidation::isValidGrade);
        DataValidation.validateField(geography_TF.getText(), geography_Lb, DataValidation::isValidGrade);
        DataValidation.validateField(history_TF.getText(), history_Lb, DataValidation::isValidGrade);
        DataValidation.validateField(civicEdu_TF.getText(), civicEdu_Lb, DataValidation::isValidGrade);
        DataValidation.validateField(technology_TF.getText(), technology_Lb, DataValidation::isValidGrade);
        DataValidation.validateField(it_TF.getText(), it_Lb, DataValidation::isValidGrade);
        DataValidation.validateField(languageCode_CB.getValue(),foreignLanguage_Lb, DataValidation::isValidComboBox);
        DataValidation.validateField(foreignLanguage_TF.getText(), foreignLanguage_Lb, DataValidation::isValidGrade);
        DataValidation.validateField(conduct_CB.getValue(), conduct_Lb, DataValidation::isValidComboBox);

        choPhepCapNhat = DataValidation.validateField(literature_TF.getText(), literature_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(math_TF.getText(), math_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(physics_TF.getText(), physics_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(chemistry_TF.getText(), chemistry_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(biology_TF.getText(), biology_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(geography_TF.getText(), geography_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(history_TF.getText(), history_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(civicEdu_TF.getText(), civicEdu_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(technology_TF.getText(), technology_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(it_TF.getText(), it_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(languageCode_CB.getValue(), foreignLanguage_Lb, DataValidation::isValidComboBox) &&
                DataValidation.validateField(foreignLanguage_TF.getText(), foreignLanguage_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(conduct_CB.getValue(), conduct_Lb, DataValidation::isValidComboBox);
    }

}