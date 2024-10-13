package com.qlhs.qlhs.Controller;
//

import com.qlhs.qlhs.Database.GradeDAO;
import com.qlhs.qlhs.Database.StudentDAO;
import com.qlhs.qlhs.Database.UpdateDatabase;
import com.qlhs.qlhs.Model.Grade;
import com.qlhs.qlhs.Model.School;
import com.qlhs.qlhs.Model.Student;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SchoolController {
    private static SchoolController instance;
    private final StudentDAO studentDAO;
    private final GradeDAO gradeDAO;
    private final School school;

    public SchoolController() {
        this.school = new School();
        this.studentDAO = new StudentDAO();
        initializeStudentList();
        this.gradeDAO = new GradeDAO();
        initializeGradeList();
    }

    public SchoolController(School school) {
        // Khởi tạo đối tượng bệnh viện
        this.school = new School();
        this.studentDAO = new StudentDAO();  // Khởi tạo PatientDao
        initializeStudentList();  // Lấy dữ liệu từ DB khi khởi tạo
        this.gradeDAO = new GradeDAO();
        initializeGradeList();
    }

    // Phương thức để lấy thể hiện
    public static SchoolController getInstance() {
        if (instance == null) {
            instance = new SchoolController();
        }
        return instance;
    }

    public void initializeStudentList() {
        ArrayList<Student> studentsFromDB = StudentDAO.getStudents();
        school.setStudents(studentsFromDB);
    }

    public void initializeGradeList() {
        ArrayList<Grade> gradesFromDB = GradeDAO.getGrades();
        school.setGrades(gradesFromDB);
    }

    public void UpdateStudentInfo(Student student) {
        List<Integer> studentIDs = new ArrayList<>();
        ArrayList<Student> students = StudentDAO.getStudents();
        students.stream().map(Student::getStudentID).map(Integer::parseInt).forEach(studentIDs::add);
        boolean isUpdate = studentIDs.contains(Integer.parseInt(student.getStudentID()));

        String query = isUpdate ? "UPDATE student SET firstName = ?, lastName = ?, dateOfBirth = ?, gender = ?, ID = ?, phoneNumber = ?, email = ?, className = ?, address = ?, notes = ?, status = ? WHERE studentId = ?;"
                : "INSERT INTO student (firstName, lastName, dateOfBirth, gender, ID, phoneNumber, email, className, address, notes, status, studentId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String query2 = !isUpdate ? "INSERT INTO grade (studentId) VALUES (?);" : "";

        UpdateDatabase.updateStudentInfo(student.getStudentID(), student.getFirstName(), student.getLastName(), student.getDateOfBirth(), student.getGender(), student.getID(), student.getPhoneNumber(), student.getEmail(), student.getClassName(), student.getAddress(), student.getNotes(), student.getStatus(), query, query2);

//        ActivityLog.logInformation("Update: "+studentId+" " + firstName + lastName+ dateOfBirth + gender + ID + phoneNumber + email + className + address + notes + status);
    }

    public void deleteStudent(String studentId) {
        String status = "false";

        String query = "UPDATE student SET status = ?  WHERE studentId = ?;";
        UpdateDatabase.deleteStudentInfo(studentId, status, query);
    }

    public String addNewStudent() {
        int studentID = 0;
        List<Student> Students = StudentDAO.getStudents();
        LocalDate today = LocalDate.now();
        ConfigReader configReader = new ConfigReader();
        String endOfSchoolYear = configReader.getEndOfSchoolYear();

        int thisYear = Integer.parseInt(endOfSchoolYear.substring(2, 4));

        LocalDate compareDate = LocalDate.parse(endOfSchoolYear, DateTimeFormatter.ISO_LOCAL_DATE);
        // kiểm tra xem đã tồn tại học sinh nào chưa, nếu chưa thì tạo 1 học sinh
        if (Students.isEmpty()) {
            String query = "INSERT INTO student (firstName, lastName, dateOfBirth, gender, ID, phoneNumber, email, className, address, notes, status, studentId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            UpdateDatabase.updateStudentInfo(String.valueOf(thisYear * 1000000), "", "", String.valueOf(today), false, "ID", "", "", "", "", "", false, query, "");
            return String.valueOf(thisYear * 1000000);

        } else {
            for (Student student : Students) {
                if (student != null) {
                    studentID = Integer.parseInt(student.getStudentID());
                }
            }
            // So sánh ngày hiện tại với ngày chuỗi
            if (today.isAfter(compareDate)) {
                if (studentID / 1000000 < thisYear) { // chia lấy nguyên cho 1000000 để lấy ra năm
                    studentID = thisYear * 1000000; // vd mã năm là 24 thì sẽ là 24*1000000 = 24000000
                }
            }
            String query = "INSERT INTO student (firstName, lastName, dateOfBirth, gender, ID, phoneNumber, email, className, address, notes, status, studentId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            UpdateDatabase.updateStudentInfo(String.valueOf(studentID + 1), "", "", String.valueOf(today), false, "ID", "", "", "", "", "", false, query, "");

            return String.valueOf(studentID + 1);
        }
    }

    public void UpdateGrade(Grade grade) {
        String query = "UPDATE grade SET literature = ?, math = ?, physics = ?, chemistry = ?, biology = ?, " +
                "history = ?, geography = ?, civicEdu = ?, technology = ?, it = ?, physicalEdu = ?, " +
                "foreignLang = ?, languageCode = ?, conduct = ?, gradeNotes = ? WHERE studentID = ?";

        UpdateDatabase.updateGrades(grade.getStudentID(), grade.getLiterature(), grade.getMath(), grade.getPhysics(), grade.getChemistry(), grade.getBiology(), grade.getHistory(), grade.getGeography(), grade.getCivicEdu(), grade.getTechnology(), grade.getIt(), grade.getPhysicalEdu(), grade.getForeignLang(), grade.getLanguageCode(), grade.getConduct(), grade.getGradeNotes(), query);
    }
}