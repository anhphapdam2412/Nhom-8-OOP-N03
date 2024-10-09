package com.qlhs.qlhs.Controller;

import com.qlhs.qlhs.Database.AcademicTranscriptDAO;
import com.qlhs.qlhs.Database.UpdateDatabase;
import com.qlhs.qlhs.Model.AcademicTranscript;
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

import java.io.IOException;
import java.util.Objects;

public class AcademicTranscriptController {

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
    private TextField foreignLang_TF;
    @FXML
    private TextField technology_TF;
    @FXML
    private TextField it_TF;
    @FXML
    private RadioButton physicalEdu_Btn;
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
    private Label foreignLang_Lb;


    @FXML
    private ComboBox<String> table_CB;


    @FXML
    private TableView<AcademicTranscript> gradeReportTableView;
    @FXML
    private TableColumn<AcademicTranscript, String> noColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> studentIDColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> firstNameColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> lastNameColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> dateOfBirthColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> genderColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> classNameColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> foreignLangColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> technologyColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> itColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> physicalEduColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> geographyColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> literatureColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> mathColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> physicsColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> biologyColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> historyColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> notesColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> civicEduColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> chemistryColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> academicPerformanceColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> conductColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> languageCodeColumn;
    @FXML
    private TableColumn<AcademicTranscript, String> avgGradeColumn;

    private Timeline debounce;
    public boolean choPhepCapNhat = false;


    @FXML
    private void initialize() {
        // Thêm các lựa chọn vào ChoiceBox
        table_CB.getItems().addAll("Thông tin học sinh", "Bảng điểm");
        table_CB.setValue("Bảng điểm");

        conduct_CB.getItems().addAll("T", "Kh", "TB", "Y", "K");
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

        debounce = new Timeline(new KeyFrame(Duration.millis(300), event -> {
            if (Objects.equals(search_TF.getText(), "")) {
                showAcademicTranscript(Search.filterGrade(""));
            } else {
                showAcademicTranscript(Search.filterGrade(search_TF.getText()));
            }
        }));
        debounce.setCycleCount(1);

        showAcademicTranscript(Search.filterGrade(""));
        selectedStudent();
        search_TF.setText(null);
        conduct_CB.setOnAction(_ -> checkconduct());
        languageCode_CB.setOnAction(_ -> checkLanguageCode());
    }

    private void showAcademicTranscript(ObservableList<AcademicTranscript> query){
        studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
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
        foreignLangColumn.setCellValueFactory(new PropertyValueFactory<>("foreignLang"));
        technologyColumn.setCellValueFactory(new PropertyValueFactory<>("technology"));
        itColumn.setCellValueFactory(new PropertyValueFactory<>("it"));
        physicalEduColumn.setCellValueFactory(new PropertyValueFactory<>("physicalEdu"));
        avgGradeColumn.setCellValueFactory(new PropertyValueFactory<>("avgGrade"));
        languageCodeColumn.setCellValueFactory(new PropertyValueFactory<>("languageCode"));
        academicPerformanceColumn.setCellValueFactory(new PropertyValueFactory<>("academicPerformance"));
        conductColumn.setCellValueFactory(new PropertyValueFactory<>("conduct"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("gradeNotes"));

        // Đặt danh sách đã lọc vào bảng
        gradeReportTableView.setItems(query);
    }

    private void selectedStudent() {
        gradeReportTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Nhấp đúp chuột
                AcademicTranscript selectedDiem = gradeReportTableView.getSelectionModel().getSelectedItem();
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
    private void showGradeDetail(AcademicTranscript AcademicTranscript) {
        literature_TF.setText(String.valueOf(AcademicTranscript.getLiterature()));
        math_TF.setText(String.valueOf(AcademicTranscript.getMath()));
        physics_TF.setText(String.valueOf(String.valueOf(AcademicTranscript.getPhysics())));
        chemistry_TF.setText(String.valueOf(AcademicTranscript.getChemistry()));
        biology_TF.setText(String.valueOf(AcademicTranscript.getBiology()));
        history_TF.setText(String.valueOf(AcademicTranscript.getHistory()));
        geography_TF.setText(String.valueOf(AcademicTranscript.getGeography()));
        civicEdu_TF.setText(String.valueOf(AcademicTranscript.getCivicEdu()));
        foreignLang_TF.setText(String.valueOf(AcademicTranscript.getForeignLang()));
        technology_TF.setText(String.valueOf(AcademicTranscript.getTechnology()));
        it_TF.setText(String.valueOf(AcademicTranscript.getIt()));
        physicalEdu_Btn.setSelected("D".equals(AcademicTranscript.getPhysicalEdu()));
        languageCode_CB.setValue(AcademicTranscript.getLanguageCode());
        conduct_CB.setValue(AcademicTranscript.getConduct());
        studentID_Lb.setText(String.valueOf(AcademicTranscript.getStudentID()));
        className_Lb.setText(String.valueOf(AcademicTranscript.getClassName()));
        avgGrade_TF.setText(String.valueOf(AcademicTranscript.getAvgGrade()));
        dateOfBirth_Lb.setText(String.valueOf(AcademicTranscript.getDateOfBirth()));
        fullName_Lb.setText(AcademicTranscript.getFirstName()+" "+AcademicTranscript.getLastName());
        gender_Lb.setText("1".equals(AcademicTranscript.getGender())?"Nam":"Nữ");
        gradeNotes_TF.setText(String.valueOf(AcademicTranscript.getGradeNotes()));
    }
    private void loadFXML(String fxmlFile) throws IOException {
        String fxmlPath = switch (fxmlFile) {
            case "Thông tin học sinh" -> "/com/qlhs/qlhs/StudentView.fxml";
            case "Bảng điểm" -> "/com/qlhs/qlhs/AcademicTranscriptView.fxml";
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
            String foreignLang = foreignLang_TF.getText();

            String chemistry = chemistry_TF.getText();
            String physics = physics_TF.getText();
            String civicEdu = civicEdu_TF.getText();

            String geography = geography_TF.getText();
            String physicalEdu = physicalEdu_Btn.isSelected()?"D":"T";
            String languageCode = languageCode_CB.getValue();
            String conduct = conduct_CB.getValue();

            avgGrade_TF.setText(String.format("%.2f",(Float.parseFloat(literature )+ Float.parseFloat(math )+ Float.parseFloat(physics )+Float.parseFloat(chemistry)+Float.parseFloat(biology)+Float.parseFloat(history)+Float.parseFloat(geography)+Float.parseFloat(civicEdu)+Float.parseFloat(technology)+Float.parseFloat(it)+Float.parseFloat(foreignLang)) / 11));

            query = "UPDATE grade SET literature = ?, math = ?, physics = ?, chemistry = ?, biology = ?, " +
                    "history = ?, geography = ?, civicEdu = ?, technology = ?, it = ?, physicalEdu = ?, " +
                    "foreignLang = ?, languageCode = ?, conduct = ?, gradeNotes = ? WHERE studentID = ?";

            UpdateDatabase.updateGrades(studentID, literature, math, physics, chemistry, biology, history, geography, civicEdu, technology, it, physicalEdu, foreignLang, languageCode, conduct, gradeNotes, query);
        }

        showAcademicTranscript(Search.filterGrade(""));
    }
    @FXML
    private void search() {
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
        foreignLang_TF.clear();
        technology_TF.clear();
        it_TF.clear();
        physicalEdu_Btn.setSelected(false);
        conduct_CB.setValue(null);
        search_TF.clear();
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
        DataValidation.validateField(languageCode_CB.getValue(),foreignLang_Lb, DataValidation::isValidComboBox);
        DataValidation.validateField(foreignLang_TF.getText(), foreignLang_Lb, DataValidation::isValidGrade);
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
                DataValidation.validateField(languageCode_CB.getValue(), foreignLang_Lb, DataValidation::isValidComboBox) &&
                DataValidation.validateField(foreignLang_TF.getText(), foreignLang_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(conduct_CB.getValue(), conduct_Lb, DataValidation::isValidComboBox);
    }

}