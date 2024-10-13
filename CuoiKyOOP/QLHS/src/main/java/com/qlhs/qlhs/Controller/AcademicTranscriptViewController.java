package com.qlhs.qlhs.Controller;

import com.qlhs.qlhs.Model.Grade;
import com.qlhs.qlhs.Model.StudentGrade;
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

public class AcademicTranscriptViewController {
    public boolean allowUpdate = false;
    @FXML
    private TextField literature_TF, math_TF, physics_TF, chemistry_TF, biology_TF, history_TF, geography_TF, civicEdu_TF, foreignLang_TF, technology_TF, it_TF, avgGrade_TF, gradeNotes_TF, award_TF, search_TF;
    @FXML
    private RadioButton physicalEdu_Btn;
    @FXML
    private ComboBox<String> conduct_CB, languageCode_CB, table_CB;
    @FXML
    private Label studentID_Lb, fullName_Lb, gender_Lb, dateOfBirth_Lb, className_Lb, literature_Lb, math_Lb, physics_Lb, chemistry_Lb, biology_Lb, history_Lb, technology_Lb, geography_Lb, civicEdu_Lb, it_Lb, conduct_Lb, foreignLang_Lb;
    @FXML
    private TableView<StudentGrade> AcademicTranscriptTableView;
    @FXML
    private TableColumn<StudentGrade, String> noColumn, studentIDColumn, firstNameColumn, lastNameColumn, dateOfBirthColumn, genderColumn, classNameColumn;
    @FXML
    private TableColumn<StudentGrade, String> foreignLangColumn, technologyColumn, itColumn, physicalEduColumn, geographyColumn, literatureColumn, mathColumn, physicsColumn, biologyColumn, historyColumn, notesColumn, civicEduColumn, chemistryColumn, academicPerformanceColumn, conductColumn, languageCodeColumn, avgGradeColumn, awardColumn;
    private Timeline debounce;
    private final SchoolController schoolController;

    public AcademicTranscriptViewController() {
        this.schoolController = SchoolController.getInstance();
    }


    @FXML
    public void initialize() {
        showAcademicTranscript(Search.filterGrade(""));
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

        debounce = new Timeline(new KeyFrame(Duration.millis(300), event -> {
            if (Objects.equals(search_TF.getText(), "")) {
                showAcademicTranscript(Search.filterGrade(""));
            } else {
                showAcademicTranscript(Search.filterGrade(search_TF.getText()));
            }
        }));
        debounce.setCycleCount(1);
        selectedStudent();
        conduct_CB.setOnAction(_ -> checkconduct());
        languageCode_CB.setOnAction(_ -> checkLanguageCode());
    }

    private void selectedStudent() {
        AcademicTranscriptTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Nhấp đúp chuột
                StudentGrade selectedGrade = AcademicTranscriptTableView.getSelectionModel().getSelectedItem();
                if (selectedGrade != null) {
                    showGradeDetail(selectedGrade);
                }
            }
        });
    }

    private void showGradeDetail(StudentGrade studentGrade) {
        literature_TF.setText(!Objects.equals(String.valueOf(studentGrade.getLiterature()), "null") ? String.valueOf(studentGrade.getLiterature()) : "");
        math_TF.setText(!Objects.equals(String.valueOf(studentGrade.getMath()), "null") ? String.valueOf(studentGrade.getMath()) : "");
        physics_TF.setText(!Objects.equals(String.valueOf(studentGrade.getPhysics()), "null") ? String.valueOf(studentGrade.getPhysics()) : "");
        chemistry_TF.setText(!Objects.equals(String.valueOf(studentGrade.getChemistry()), "null") ? String.valueOf(studentGrade.getChemistry()) : "");
        biology_TF.setText(!Objects.equals(String.valueOf(studentGrade.getBiology()), "null") ? String.valueOf(studentGrade.getBiology()) : "");
        history_TF.setText(!Objects.equals(String.valueOf(studentGrade.getHistory()), "null") ? String.valueOf(studentGrade.getHistory()) : "");
        geography_TF.setText(!Objects.equals(String.valueOf(studentGrade.getGeography()), "null") ? String.valueOf(studentGrade.getGeography()) : "");
        civicEdu_TF.setText(!Objects.equals(String.valueOf(studentGrade.getCivicEdu()), "null") ? String.valueOf(studentGrade.getCivicEdu()) : "");
        foreignLang_TF.setText(!Objects.equals(String.valueOf(studentGrade.getForeignLang()), "null") ? String.valueOf(studentGrade.getForeignLang()) : "");
        technology_TF.setText(!Objects.equals(String.valueOf(studentGrade.getTechnology()), "null") ? String.valueOf(studentGrade.getTechnology()) : "");
        it_TF.setText(!Objects.equals(String.valueOf(studentGrade.getIt()), "null") ? String.valueOf(studentGrade.getIt()) : "");
        physicalEdu_Btn.setSelected("D".equals(studentGrade.getPhysicalEdu()));
        languageCode_CB.setValue(!Objects.equals(studentGrade.getLanguageCode(), "null") ? studentGrade.getLanguageCode() : "");
        conduct_CB.setValue(!Objects.equals(studentGrade.getConduct(), "null") ? studentGrade.getConduct() : "");
        studentID_Lb.setText(!Objects.equals(String.valueOf(studentGrade.getStudentID()), "null") ? String.valueOf(studentGrade.getStudentID()) : "");
        className_Lb.setText(!Objects.equals(String.valueOf(studentGrade.getClassName()), "null") ? String.valueOf(studentGrade.getClassName()) : "");
        avgGrade_TF.setText(!Objects.equals(String.valueOf(studentGrade.getAvgGrade()), "null") ? String.valueOf(studentGrade.getAvgGrade()) : "");
        dateOfBirth_Lb.setText(!Objects.equals(String.valueOf(studentGrade.getDateOfBirth()), "null") ? String.valueOf(studentGrade.getDateOfBirth()) : "");
        fullName_Lb.setText((!Objects.equals(studentGrade.getFirstName(), "null") ? studentGrade.getFirstName() : "") + " " +
                (!Objects.equals(studentGrade.getLastName(), "null") ? studentGrade.getLastName() : ""));
        gender_Lb.setText("1".equals(studentGrade.getGender()) ? "Nam" : "Nữ");
        gradeNotes_TF.setText(!Objects.equals(String.valueOf(studentGrade.getGradeNotes()), "null") ? String.valueOf(studentGrade.getGradeNotes()) : "");
        award_TF.setText(!Objects.equals(String.valueOf(studentGrade.getAward()), "null") ? String.valueOf(studentGrade.getAward()) : "");
    }

    private void loadFXML(String fxmlFile) throws IOException {
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

    @FXML
    private void handleKeyReleased() {
        GradeCheckList();
    }

    private void GradeCheckList() {
        DataValidation.validateField(languageCode_CB.getValue(), foreignLang_Lb, DataValidation::isValidComboBox);
        DataValidation.validateField(foreignLang_TF.getText(), foreignLang_Lb, DataValidation::isValidGrade);
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
        DataValidation.validateField(conduct_CB.getValue(), conduct_Lb, DataValidation::isValidComboBox);

        allowUpdate = DataValidation.validateField(literature_TF.getText(), literature_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(languageCode_CB.getValue(), foreignLang_Lb, DataValidation::isValidComboBox) &&
                DataValidation.validateField(foreignLang_TF.getText(), foreignLang_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(math_TF.getText(), math_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(physics_TF.getText(), physics_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(chemistry_TF.getText(), chemistry_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(biology_TF.getText(), biology_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(geography_TF.getText(), geography_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(history_TF.getText(), history_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(civicEdu_TF.getText(), civicEdu_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(technology_TF.getText(), technology_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(it_TF.getText(), it_Lb, DataValidation::isValidGrade) &&
                DataValidation.validateField(conduct_CB.getValue(), conduct_Lb, DataValidation::isValidComboBox);
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
    private void updateGrade() {
        GradeCheckList();
        Grade newGrade;
        if (studentID_Lb.getText().equals("")) {
            Dialog.showError("Chưa có mã học sinh");
            return;
        } else if (!allowUpdate) {
            Dialog.showError("Vui lòng điền đầy đủ thông tin");
            return;
        }
        String studentID = studentID_Lb.getText();
        float literature = Float.parseFloat(literature_TF.getText());
        float math = Float.parseFloat(math_TF.getText());
        float history = Float.parseFloat(history_TF.getText());
        float biology = Float.parseFloat(biology_TF.getText());
        float it = Float.parseFloat(it_TF.getText());
        float technology = Float.parseFloat(technology_TF.getText());
        String gradeNotes = gradeNotes_TF.getText();
        float foreignLang = Float.parseFloat(foreignLang_TF.getText());
        float chemistry = Float.parseFloat(chemistry_TF.getText());
        float physics = Float.parseFloat(physics_TF.getText());
        float civicEdu = Float.parseFloat(civicEdu_TF.getText());
        float geography = Float.parseFloat(geography_TF.getText());
        String physicalEdu = physicalEdu_Btn.isSelected() ? "D" : "T";
        String languageCode = languageCode_CB.getValue();
        String conduct = conduct_CB.getValue();

        // Creating the Grade object
        newGrade = new Grade(
                studentID,
                literature,
                math,
                physics,
                chemistry,
                biology,
                history,
                geography,
                civicEdu,
                technology,
                it,
                physicalEdu,
                foreignLang,
                languageCode,
                "",
                conduct,
                gradeNotes,
                0,
                ""
        );
        schoolController.UpdateGrade(newGrade);
        showAcademicTranscript(Search.filterGrade(""));
    }

    @FXML
    private void search() {
        // Hủy timeline hiện tại nếu nó đang chạy
        debounce.stop();
        // Khởi động lại timeline, nó sẽ đợi 300ms trước khi thực hiện hành động tìm kiếm
        debounce.playFromStart();
    }

    private void checkconduct() {
        GradeCheckList();
    }

    private void checkLanguageCode() {
        GradeCheckList();
    }

    private void showAcademicTranscript(ObservableList<StudentGrade> studentGrades) {
        // Thiết lập các cột
        noColumn.setCellValueFactory(cellData -> {
            // Lấy chỉ số của học sinh trong danh sách
            int index = AcademicTranscriptTableView.getItems().indexOf(cellData.getValue()) + 1;
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
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));

        // Set up columns for grade fields
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
        awardColumn.setCellValueFactory(new PropertyValueFactory<>("award"));

        // Set the list into the table
        AcademicTranscriptTableView.setItems(studentGrades);
    }


}
