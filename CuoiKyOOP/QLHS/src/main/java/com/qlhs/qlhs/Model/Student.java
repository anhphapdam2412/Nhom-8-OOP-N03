package com.qlhs.qlhs.Model;

public class Student {
    private String studentID;
    private String lastName;
    private String firstName;
    private String dateOfBirth;
    private Boolean gender;
    private String ID;
    private String phoneNumber;
    private String email;
    private String className;
    private String address;
    private String notes;
    private Boolean status;

    public Student() {
    }

    // Constructor
    public Student(String studentID, String lastName, String firstName, String dateOfBirth, Boolean gender, String ID, String phoneNumber, String email, String className, String address, String notes, Boolean status) {
        this.studentID = studentID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.ID = ID;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.className = className;
        this.address = address;
        this.notes = notes;
        this.status = status;
    }

    // Getters for each field

    public String getStudentID() {
        return studentID;
    }

    // Setter methods
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
