package com.qlhs.qlhs.Model;

import java.util.ArrayList;
import java.util.List;

public class School {
    List<Student> students;
    List<Grade> grades;
    private int nextPatientId;
    private int nextRoomId;


    public School() {
        this.students = new ArrayList<>();
        this.grades = new ArrayList<>();
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
//    public static List<Student> getStudents() {
//        return students;
//    }
}