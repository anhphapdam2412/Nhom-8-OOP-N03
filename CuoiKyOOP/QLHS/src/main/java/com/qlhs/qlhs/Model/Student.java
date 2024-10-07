package com.qlhs.qlhs.Model;

public class Student {
    private final String studentID;
    private final String lastName;
    private final String firstName;
    private final String dateOfBirth;
    private final String gender;
    private final String ID;
    private final String phoneNumber;
    private final String email;
    private final String className;
    private final String address;
    private final String notes;
    private final String status;

    // Constructor
    public Student(String studentID, String lastName, String firstName, String dateOfBirth, String gender, String ID, String phoneNumber, String email, String className, String address, String notes, String status) {
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

    public String getID() {
        return ID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getClassName() {
        return className;
    }

    public String getAddress() {
        return address;
    }

    public String getNotes() {
        return notes;
    }

    public String getStatus() {
        return status;
    }
}
