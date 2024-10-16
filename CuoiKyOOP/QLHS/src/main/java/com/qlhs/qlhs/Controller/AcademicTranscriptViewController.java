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
    // TableView và TableColumn
    @FXML
    private TableView<StudentGrade> AcademicTranscriptTableView;
    @FXML
    private TableColumn<StudentGrade, String> noColumn, studentIDColumn, firstNameColumn, lastNameColumn, dateOfBirthColumn, genderColumn, classNameColumn;
    @FXML
    private TableColumn<StudentGrade, String> foreignLangColumn, technologyColumn, itColumn, physicalEduColumn, geographyColumn, literatureColumn, mathColumn, physicsColumn, biologyColumn, historyColumn, notesColumn, civicEduColumn, chemistryColumn, academicPerformanceColumn, conductColumn, languageCodeColumn, avgGradeColumn, awardColumn;
    // DatePicker và ComboBox
    @FXML
    private ComboBox<String> conduct_CB, languageCode_CB, table_CB;
    // Buttons và Labels
    @FXML
    private RadioButton physicalEdu_Btn;
    @FXML
    private Label studentID_Lb, fullName_Lb, gender_Lb, dateOfBirth_Lb, className_Lb, literature_Lb, math_Lb, physics_Lb, chemistry_Lb, biology_Lb, history_Lb, technology_Lb, geography_Lb, civicEdu_Lb, it_Lb, conduct_Lb, foreignLang_Lb;
    // Các TextField
    @FXML
    private TextField literature_TF, math_TF, physics_TF, chemistry_TF, biology_TF, history_TF, geography_TF, civicEdu_TF, foreignLang_TF, technology_TF, it_TF, avgGrade_TF, gradeNotes_TF, award_TF, search_TF;

    private final SchoolController schoolController;
    public AcademicTranscriptViewController() {
        this.schoolController = SchoolController.getInstance();
    }

    public boolean allowUpdate = false;
    private Timeline debounce;

    @FXML
    public void initialize() {
        showAcademicTranscript(Search.filterGrade(""));
        // Thêm các bảng vào choiceBox
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

        // Đếm thời gian nghỉ sau khi nhập điều kiện tìm kiếm
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

    // Chọn học sinh từ TableView
    private void selectedStudent() {
        AcademicTranscriptTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                StudentGrade selectedGrade = AcademicTranscriptTableView.getSelectionModel().getSelectedItem();
                if (selectedGrade != null) {
                    showGradeDetail(selectedGrade);
                }
            }
        });
    }

    // Kiểm tra điều kiện nhập
    @FXML
    private void handleKeyReleased() {
        GradeCheckList();
    }
    private void checkconduct() {
        GradeCheckList();
    }

    private void checkLanguageCode() {
        GradeCheckList();
    }

    // Làm mới bảng điểm
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

    // Cập nhật bảng điểm
    @FXML
    private void updateGrade() {
        GradeCheckList();

        if (studentID_Lb.getText().equals("")) {
            Dialog.showError("Chưa có mã học sinh");
            return;
        } else if (!allowUpdate) {
            Dialog.showError("Vui lòng điền đầy đủ thông tin");
            return;
        }
        String studentID = studentID_Lb.getText();
        Float literature = Float.parseFloat(literature_TF.getText());
        Float math = Float.parseFloat(math_TF.getText());
        Float history = Float.parseFloat(history_TF.getText());
        Float biology = Float.parseFloat(biology_TF.getText());
        Float it = Float.parseFloat(it_TF.getText());
        Float technology = Float.parseFloat(technology_TF.getText());
        String gradeNotes = gradeNotes_TF.getText();
        Float foreignLang = Float.parseFloat(foreignLang_TF.getText());
        Float chemistry = Float.parseFloat(chemistry_TF.getText());
        Float physics = Float.parseFloat(physics_TF.getText());
        Float civicEdu = Float.parseFloat(civicEdu_TF.getText());
        Float geography = Float.parseFloat(geography_TF.getText());
        String physicalEdu = physicalEdu_Btn.isSelected() ? "D" : "T";
        String languageCode = languageCode_CB.getValue();
        String conduct = conduct_CB.getValue();

        Grade newGrade = new Grade(
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
                0f,
                ""
        );
        schoolController.UpdateGrade(newGrade);
        showAcademicTranscript(Search.filterGrade(""));
    }

    // Tìm kiếm
    @FXML
    private void search() {
        // Hủy timeline hiện tại nếu nó đang chạy
        debounce.stop();
        // Khởi động lại timeline, nó sẽ đợi 300ms trước khi thực hiện hành động tìm kiếm
        debounce.playFromStart();
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
        // Hiển thị lên TableView
        AcademicTranscriptTableView.setItems(studentGrades);
    }

    // Hiển thị chi tiết bảng điểm của học sinh lên phần nhập
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
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Các ô cần kiểm tra
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
}
