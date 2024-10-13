package com.qlhs.qlhs.Model;

public class Grade {

    private String studentID;
    private float literature;
    private float math;
    private float physics;
    private float chemistry;
    private float biology;
    private float history;
    private float geography;
    private float civicEdu;
    private float technology;
    private float it;
    private String physicalEdu;
    private float foreignLang;
    private String languageCode;
    private String academicPerformance;
    private String conduct;
    private String gradeNotes;
    private float avgGrade;
    private String award;

    public Grade(String studentID, float literature, float math, float physics, float chemistry, float biology, float history, float geography, float civicEdu, float technology, float it, String physicalEdu, float foreignLang, String languageCode, String academicPerformance, String conduct, String gradeNotes, float avgGrade, String award) {
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
        this.physicalEdu = physicalEdu;
        this.foreignLang = foreignLang;
        this.languageCode = languageCode;
        this.academicPerformance = academicPerformance;
        this.conduct = conduct;
        this.gradeNotes = gradeNotes;
        this.avgGrade = avgGrade;
        this.award = award;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public float getLiterature() {
        return literature;
    }

    public void setLiterature(float literature) {
        this.literature = literature;
    }

    public float getMath() {
        return math;
    }

    public void setMath(float math) {
        this.math = math;
    }

    public float getPhysics() {
        return physics;
    }

    public void setPhysics(float physics) {
        this.physics = physics;
    }

    public float getChemistry() {
        return chemistry;
    }

    public void setChemistry(float chemistry) {
        this.chemistry = chemistry;
    }

    public float getBiology() {
        return biology;
    }

    public void setBiology(float biology) {
        this.biology = biology;
    }

    public float getHistory() {
        return history;
    }

    public void setHistory(float history) {
        this.history = history;
    }

    public float getGeography() {
        return geography;
    }

    public void setGeography(float geography) {
        this.geography = geography;
    }

    public float getCivicEdu() {
        return civicEdu;
    }

    public void setCivicEdu(float civicEdu) {
        this.civicEdu = civicEdu;
    }

    public float getTechnology() {
        return technology;
    }

    public void setTechnology(float technology) {
        this.technology = technology;
    }

    public float getIt() {
        return it;
    }

    public void setIt(float it) {
        this.it = it;
    }

    public String getPhysicalEdu() {
        return physicalEdu;
    }

    public void setPhysicalEdu(String physicalEdu) {
        this.physicalEdu = physicalEdu;
    }

    public float getForeignLang() {
        return foreignLang;
    }

    public void setForeignLang(float foreignLang) {
        this.foreignLang = foreignLang;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getAcademicPerformance() {
        return academicPerformance;
    }

    public void setAcademicPerformance(String academicPerformance) {
        this.academicPerformance = academicPerformance;
    }

    public String getConduct() {
        return conduct;
    }

    public void setConduct(String conduct) {
        this.conduct = conduct;
    }

    public String getGradeNotes() {
        return gradeNotes;
    }

    public void setGradeNotes(String gradeNotes) {
        this.gradeNotes = gradeNotes;
    }

    public float getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(float avgGrade) {
        this.avgGrade = avgGrade;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }
}
