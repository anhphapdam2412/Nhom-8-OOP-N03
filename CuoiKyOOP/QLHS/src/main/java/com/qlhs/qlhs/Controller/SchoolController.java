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
        this.school = new School();
        this.studentDAO = new StudentDAO();
        initializeStudentList();
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

        String query ="UPDATE student SET firstName = ?, lastName = ?, dateOfBirth = ?, gender = ?, ID = ?, phoneNumber = ?, email = ?, className = ?, address = ?, notes = ?, status = ? WHERE studentID = ?;";
        UpdateDatabase.updateStudentInfo(student.getStudentID(), student.getFirstName(), student.getLastName(), student.getDateOfBirth(), student.getGender(), student.getID(), student.getPhoneNumber(), student.getEmail(), student.getClassName(), student.getAddress(), student.getNotes(), student.getStatus(), query);

        ActivityLog.logActivity(
                ActivityLog.ActionType.UPDATE,
                student.getStudentID() + ", " +
                        student.getLastName() + " " + student.getFirstName() + ", " +
                        student.getDateOfBirth() + ", " +
                        (student.getGender() ? "nam" : "nữ") + ", " +
                        student.getID() + ", " +
                        student.getPhoneNumber() + ", " +
                        student.getEmail() + ", " +
                        student.getClassName() + ", " +
                        student.getAddress() + ", " +
                        student.getNotes() + ", " +
                        student.getStatus()
        );
    }

    public void deleteStudent(String studentID) {
        String status = "false";

        String query = "UPDATE student SET status = ?  WHERE studentID = ?;";
        UpdateDatabase.deleteStudentInfo(studentID, status, query);

        ActivityLog.logActivity( ActivityLog.ActionType.DELETE, studentID);
    }

    public String addNewStudent() {
        List<Student> Students = StudentDAO.getStudents();

        ConfigReader configReader = new ConfigReader();
        String endOfSchoolYear = configReader.getEndOfSchoolYear();

        int studentID = 0;
        String studentIDToString ="";

        String studentQuery = "INSERT INTO student (firstName, lastName, dateOfBirth, gender, ID, phoneNumber, email, className, address, notes, status, studentID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String gradeQuery = "INSERT INTO grade (studentID) VALUES (?);";

        // Lấy 2 số cuối của năm 2024->24
        int thisYear = Integer.parseInt(endOfSchoolYear.substring(2, 4));

        // Lấy ngày kết thúc năm học
        LocalDate compareDate = LocalDate.parse(endOfSchoolYear, DateTimeFormatter.ISO_LOCAL_DATE);
        // Lấy ngày hiện tại
        LocalDate today = LocalDate.now();

        // kiểm tra xem đã tồn tại học sinh nào chưa, nếu chưa thì tạo 1 học sinh
        if (Students.isEmpty()) {
            studentIDToString = String.valueOf(thisYear * 1000000);
        } else {
            for (Student student : Students) {
                studentID = Integer.parseInt(student.getStudentID()); // Gán ID học sinh cuối
            }
            // So sánh ngày hiện tại với ngày chuỗi
            if (today.isAfter(compareDate)) {
                if (studentID / 1000000 < thisYear) { // chia lấy nguyên cho 1000000 để lấy ra năm
                    studentID = thisYear * 1000000 - 1; // vd mã năm là 24 thì sẽ là 24*1000000 = 24000000
                }
            }
            studentIDToString = String.valueOf(studentID+1);
        }
        UpdateDatabase.updateStudentInfo(studentIDToString, "", "", String.valueOf(today), false, "ID", "", "", "", "", "", false, studentQuery);
        UpdateDatabase.insertGrade(studentIDToString, gradeQuery);
        return studentIDToString;
    }

    public void UpdateGrade(Grade grade) {
        String query = "UPDATE grade SET literature = ?, math = ?, physics = ?, chemistry = ?, biology = ?, " +
                "history = ?, geography = ?, civicEdu = ?, technology = ?, it = ?, physicalEdu = ?, " +
                "foreignLang = ?, languageCode = ?, conduct = ?, gradeNotes = ? WHERE studentID = ?";

        UpdateDatabase.updateGrades(grade.getStudentID(), grade.getLiterature(), grade.getMath(), grade.getPhysics(), grade.getChemistry(), grade.getBiology(), grade.getHistory(), grade.getGeography(), grade.getCivicEdu(), grade.getTechnology(), grade.getIt(), grade.getPhysicalEdu(), grade.getForeignLang(), grade.getLanguageCode(), grade.getConduct(), grade.getGradeNotes(), query);

        ActivityLog.logActivity(
                ActivityLog.ActionType.UPDATE_GRADE,
                grade.getStudentID() + ", Literature: " +
                        grade.getLiterature() + ", Math: " +
                        grade.getMath() + ", Physics: " +
                        grade.getPhysics() + ", Chemistry: " +
                        grade.getChemistry() + ", Biology: " +
                        grade.getBiology() + ", History: " +
                        grade.getHistory() + ", Geography: " +
                        grade.getGeography() + ", CivicEdu: " +
                        grade.getCivicEdu() + ", Technology: " +
                        grade.getTechnology() + ", It: " +
                        grade.getIt() + ", PhysicalEdu: " +
                        grade.getPhysicalEdu() + ", ForeignLang: " +
                        grade.getForeignLang() + ", LanguageCode: " +
                        grade.getLanguageCode() + ", Conduct: " +
                        grade.getConduct() + ", GradeNotes: " +
                        grade.getGradeNotes()
        );
    }
}