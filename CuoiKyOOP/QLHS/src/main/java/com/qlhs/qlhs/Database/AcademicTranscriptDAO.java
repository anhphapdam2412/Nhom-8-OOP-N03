package com.qlhs.qlhs.Database;

import com.qlhs.qlhs.Model.AcademicTranscript;
import com.qlhs.qlhs.Model.Grade;
import com.qlhs.qlhs.Model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AcademicTranscriptDAO {

    public static ObservableList<AcademicTranscript> getAcademicTranscript() {
        ObservableList<Student> studentList = StudentDAO.getStudentList();
        ObservableList<Grade> grades = GradeDAO.getGrades();

        ObservableList<AcademicTranscript> combinedList = FXCollections.observableArrayList();

        for (Student student : studentList) {
            // Find the corresponding mark for the student
            for (Grade grade : grades) {
                if (student.getStudentID().equals(grade.getStudentID())) {
                    // Create a new AcademicTranscript object combining student and grade info
                    AcademicTranscript studentGrade = new AcademicTranscript(student.getStudentID(), student.getLastName(), student.getFirstName(), student.getDateOfBirth(), student.getGender(), student.getClassName(), grade.getLiterature(), grade.getMaths(), grade.getPhysics(), grade.getChemistry(), grade.getBiology(), grade.getHistory(), grade.getGeography(), grade.getCivicEducation(), grade.getTechnology(), grade.getComputerScience(), grade.getPhysicalEducation(), grade.getForeignLanguage(), grade.getLanguageCode(), grade.getAcademicPerformance(), grade.getConduct(), grade.getGradeNotes(), student.getStatus(), grade.getAvgGrade(), grade.getAward());
                    combinedList.add(studentGrade);
                    break;
                }
            }
        }

        return combinedList;
    }
}
