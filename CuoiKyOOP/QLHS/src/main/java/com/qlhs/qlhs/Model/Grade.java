package com.qlhs.qlhs.Model;

public class Grade {

    private final String studentID;
    private final String literature;
    private final String math;
    private final String physics;
    private final String chemistry;
    private final String biology;
    private final String history;
    private final String geography;
    private final String civicEdu;
    private final String technology;
    private final String it;
    private final String physicalEducation;
    private final String foreignLang;
    private final String languageCode;
    private final String academicPerformance;
    private final String conduct;
    private final String gradeNotes;
    private final String avgGrade;

    public Grade(String studentID, String literature, String math, String physics, String chemistry, String biology, String history, String geography, String civicEdu, String technology, String it, String physicalEducation, String foreignLang, String languageCode, String academicPerformance, String conduct, String gradeNotes, String avgGrade) {
        this.studentID = studentID;
        this.literature = literature;
        this.math = math;
        this.physics = physics;
        this.chemistry = chemistry;
        this.biology = biology;
        this.history = history;
        this.geography = geography;
        this.civicEdu = civicEdu;
        this.technology = technology;
        this.it = it;
        this.physicalEducation = physicalEducation;
        this.foreignLang = foreignLang;
        this.languageCode = languageCode;
        this.academicPerformance = academicPerformance;
        this.conduct = conduct;
        this.gradeNotes = gradeNotes;
        this.avgGrade = avgGrade;
    }

    public String getAvgGrade() {
        return avgGrade;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getLiterature() {
        return literature;
    }

    public String getMaths() {
        return math;
    }

    public String getPhysics() {
        return physics;
    }

    public String getChemistry() {
        return chemistry;
    }

    public String getBiology() {
        return biology;
    }

    public String getHistory() {
        return history;
    }

    public String getGeography() {
        return geography;
    }

    public String getCivicEducation() {
        return civicEdu;
    }

    public String getTechnology() {
        return technology;
    }

    public String getComputerScience() {
        return it;
    }

    public String getPhysicalEducation() {
        return physicalEducation;
    }

    public String getForeignLanguage() {
        return foreignLang;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getAcademicPerformance() {
        return academicPerformance;
    }

    public String getConduct() {
        return conduct;
    }

    public String getGradeNotes() {
        return gradeNotes;
    }
}
