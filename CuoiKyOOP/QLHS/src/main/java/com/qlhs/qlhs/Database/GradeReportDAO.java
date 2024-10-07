package com.qlhs.qlhs.Database;

import com.qlhs.qlhs.Model.GradeReport;
import com.qlhs.qlhs.Model.Grade;
import com.qlhs.qlhs.Model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GradeReportDAO {

    public static ObservableList<GradeReport> getGradeReport() {
        ObservableList<Student> studentList = StudentDAO.getStudentList();
        ObservableList<Grade> grades = GradeDAO.getGrades();

        ObservableList<GradeReport> combinedList = FXCollections.observableArrayList();

        for (Student student : studentList) {
            // Find the corresponding mark for the student
            for (Grade grade : grades) {
                if (student.getStudentID().equals(grade.getStudentID())) {
                    // Create a new GradeReport object combining student and grade info
                    GradeReport studentGrade = new GradeReport(student.getStudentID(), student.getLastName(), student.getFirstName(), student.getDateOfBirth(), student.getGender(), student.getClassName(), grade.getLiterature(), grade.getMaths(), grade.getPhysics(), grade.getChemistry(), grade.getBiology(), grade.getHistory(), grade.getGeography(), grade.getCivicEducation(), grade.getTechnology(), grade.getComputerScience(), grade.getPhysicalEducation(), grade.getForeignLanguage(), grade.getLanguageCode(), grade.getAcademicPerformance(), grade.getConduct(), grade.getGradeNotes(), student.getStatus(), grade.getGradeNotes());
                    combinedList.add(studentGrade);
                    break;
                }
            }
        }

        return combinedList;
    }
}
