package com.qlhs.qlhs.Model;

public class StudentGrade {
    private final Student student;
    private final Grade grade;

    public StudentGrade(Student student, Grade grade) {
        this.student = student;
        this.grade = grade;
    }

    // Getters
    public Boolean getStatus() {
        return student.getStatus();
    }

    public String getStudentID() {
        return student.getStudentID();
    }

    public String getFirstName() {
        return student.getFirstName();
    }

    public String getLastName() {
        return student.getLastName();
    }

    public String getDateOfBirth() {
        return student.getDateOfBirth();
    }

    public Boolean getGender() {
        return student.getGender();
    }

    public String getClassName() {
        return student.getClassName();
    }

    // Getters for Grade fields
    public Float getLiterature() {
        return grade.getLiterature();
    }

    public Float getMath() {
        return grade.getMath();
    }

    public Float getPhysics() {
        return grade.getPhysics();
    }

    public Float getChemistry() {
        return grade.getChemistry();
    }

    public Float getBiology() {
        return grade.getBiology();
    }

    public Float getHistory() {
        return grade.getHistory();
    }

    public Float getGeography() {
        return grade.getGeography();
    }

    public Float getCivicEdu() {
        return grade.getCivicEdu();
    }

    public Float getForeignLang() {
        return grade.getForeignLang();
    }

    public Float getTechnology() {
        return grade.getTechnology();
    }

    public Float getIt() {
        return grade.getIt();
    }

    public String getPhysicalEdu() {
        return grade.getPhysicalEdu();
    }

    public Float getAvgGrade() {
        return grade.getAvgGrade();
    }

    public String getLanguageCode() {
        return grade.getLanguageCode();
    }

    public String getAcademicPerformance() {
        return grade.getAcademicPerformance();
    }

    public String getConduct() {
        return grade.getConduct();
    }

    public String getGradeNotes() {
        return grade.getGradeNotes();
    }

    public String getAward() {
        return grade.getAward();
    }


}

