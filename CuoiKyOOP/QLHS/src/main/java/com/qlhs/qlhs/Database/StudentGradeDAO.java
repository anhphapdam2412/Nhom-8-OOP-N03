package com.qlhs.qlhs.Database;

import com.qlhs.qlhs.Model.Grade;
import com.qlhs.qlhs.Model.Student;
import com.qlhs.qlhs.Model.StudentGrade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class StudentGradeDAO {

    public static ObservableList<StudentGrade> getStudentGrade() {
        ArrayList<Student> studentList = StudentDAO.getStudents();
        ArrayList<Grade> grades = GradeDAO.getGrades();

        ObservableList<StudentGrade> combinedList = FXCollections.observableArrayList();

        for (Student student : studentList) {
            // Find the corresponding mark for the student
            for (Grade grade : grades) {
                if (student.getStudentID().equals(grade.getStudentID())) {
                    // Create a new StudentGrade object combining student and grade info
//                    StudentGrade studentGrade = new StudentGrade(student.getStudentID(), student.getLastName(), student.getFirstName(), student.getDateOfBirth(), student.getGender(), student.getClassName(), grade.getLiterature(), grade.getMath(), grade.getPhysics(), grade.getChemistry(), grade.getBiology(), grade.getHistory(), grade.getGeography(), grade.getCivicEdu(), grade.getTechnology(), grade.getIt(), grade.getPhysicalEdu(), grade.getForeignLang(), grade.getLanguageCode(), grade.getAcademicPerformance(), grade.getConduct(), grade.getGradeNotes(), student.getStatus(), grade.getAvgGrade(), grade.getAward());

                    StudentGrade studentGrade = new StudentGrade(student, grade);
                    combinedList.add(studentGrade);
                    break;
                }
            }
        }

        return combinedList;
    }
}