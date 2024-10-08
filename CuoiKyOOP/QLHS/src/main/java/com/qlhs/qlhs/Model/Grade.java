package com.qlhs.qlhs.Model;

public class Grade {

    private final String studentID;
    private final String literature;
    private final String maths;
    private final String physics;
    private final String chemistry;
    private final String biology;
    private final String history;
    private final String geography;
    private final String civicEdu;
    private final String technology;
    private final String computerScience;
    private final String physicalEducation;
    private final String foreignLanguage;
    private final String languageCode;
    private final String academicPerformance;
    private final String conduct;
    private final String gradeNotes;
    private final String averageGrade;

    public Grade(String studentID, String literature, String maths, String physics, String chemistry, String biology, String history, String geography, String civicEdu, String technology, String computerScience, String physicalEducation, String foreignLanguage, String languageCode, String academicPerformance, String conduct, String gradeNotes, String averageGrade) {
        this.studentID = studentID;
        this.literature = literature;
        this.maths = maths;
        this.physics = physics;
        this.chemistry = chemistry;
        this.biology = biology;
        this.history = history;
        this.geography = geography;
        this.civicEdu = civicEdu;
        this.technology = technology;
        this.computerScience = computerScience;
        this.physicalEducation = physicalEducation;
        this.foreignLanguage = foreignLanguage;
        this.languageCode = languageCode;
        this.academicPerformance = academicPerformance;
        this.conduct = conduct;
        this.gradeNotes = gradeNotes;
        this.averageGrade = averageGrade;
    }

    public String getAverageGrade() {
        return averageGrade;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getLiterature() {
        return literature;
    }

    public String getMaths() {
        return maths;
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
        return computerScience;
    }

    public String getPhysicalEducation() {
        return physicalEducation;
    }

    public String getForeignLanguage() {
        return foreignLanguage;
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
