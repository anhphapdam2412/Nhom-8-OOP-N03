package com.qlhs.qlhs.Model;

public class Grade {

    private String studentID;
    private Float literature;
    private Float math;
    private Float physics;
    private Float chemistry;
    private Float biology;
    private Float history;
    private Float geography;
    private Float civicEdu;
    private Float technology;
    private Float it;
    private String physicalEdu;
    private Float foreignLang;
    private String languageCode;
    private String academicPerformance;
    private String conduct;
    private String gradeNotes;
    private Float avgGrade;
    private String award;

    public Grade(String studentID, Float literature, Float math, Float physics, Float chemistry, Float biology, Float history, Float geography, Float civicEdu, Float technology, Float it, String physicalEdu, Float foreignLang, String languageCode, String academicPerformance, String conduct, String gradeNotes, Float avgGrade, String award) {
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

    public Float getLiterature() {
        return literature;
    }

    public void setLiterature(Float literature) {
        this.literature = literature;
    }

    public Float getMath() {
        return math;
    }

    public void setMath(Float math) {
        this.math = math;
    }

    public Float getPhysics() {
        return physics;
    }

    public void setPhysics(Float physics) {
        this.physics = physics;
    }

    public Float getChemistry() {
        return chemistry;
    }

    public void setChemistry(Float chemistry) {
        this.chemistry = chemistry;
    }

    public Float getBiology() {
        return biology;
    }

    public void setBiology(Float biology) {
        this.biology = biology;
    }

    public Float getHistory() {
        return history;
    }

    public void setHistory(Float history) {
        this.history = history;
    }

    public Float getGeography() {
        return geography;
    }

    public void setGeography(Float geography) {
        this.geography = geography;
    }

    public Float getCivicEdu() {
        return civicEdu;
    }

    public void setCivicEdu(Float civicEdu) {
        this.civicEdu = civicEdu;
    }

    public Float getTechnology() {
        return technology;
    }

    public void setTechnology(Float technology) {
        this.technology = technology;
    }

    public Float getIt() {
        return it;
    }

    public void setIt(Float it) {
        this.it = it;
    }

    public String getPhysicalEdu() {
        return physicalEdu;
    }

    public void setPhysicalEdu(String physicalEdu) {
        this.physicalEdu = physicalEdu;
    }

    public Float getForeignLang() {
        return foreignLang;
    }

    public void setForeignLang(Float foreignLang) {
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

    public Float getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(Float avgGrade) {
        this.avgGrade = avgGrade;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }
}
