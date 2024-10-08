package com.qlhs.qlhs.Model;

public class GradeReport {
    private final String studentID;
    private final String lastName;
    private final String firstName;
    private final String dateOfBirth;
    private final String gender;
    private final String className;
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
    private final String physicalEdu;
    private final String foreignLang;
    private final String languageCode;
    private final String academicPerformance;
    private final String conduct;
    private final String gradeNotes;
    private final String status;
    private final String avgGrade;

    public GradeReport(String studentID, String lastName, String firstName, String dateOfBirth, String gender, String className, String literature, String math, String physics, String chemistry, String biology, String history, String geography, String civicEdu, String technology, String it, String physicalEdu, String foreignLang, String languageCode, String academicPerformance, String conduct, String gradeNotes, String status, String avgGrade) {
        this.studentID = studentID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.className = className;
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
        this.physicalEdu = physicalEdu;
        this.foreignLang = foreignLang;
        this.languageCode = languageCode;
        this.academicPerformance = academicPerformance;
        this.conduct = conduct;
        this.gradeNotes = gradeNotes;
        this.status = status;
        this.avgGrade = avgGrade;
    }

    public String getAverageGrade() {
        return avgGrade;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getClassName() {
        return className;
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

    public String getCivicEdu() {
        return civicEdu;
    }

    public String getTechnology() {
        return technology;
    }

    public String getComputerScience() {
        return it;
    }

    public String getPhysicalEdu() {
        return physicalEdu;
    }

    public String getForeignLang() {
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

    public String getStatus() {
        return status;
    }
}
