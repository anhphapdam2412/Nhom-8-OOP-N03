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
            for (Grade grade : grades) {
                if (student.getStudentID().equals(grade.getStudentID())) {
                    StudentGrade studentGrade = new StudentGrade(student, grade);
                    combinedList.add(studentGrade);
                    break;
                }
            }
        }
        return combinedList;
    }
}