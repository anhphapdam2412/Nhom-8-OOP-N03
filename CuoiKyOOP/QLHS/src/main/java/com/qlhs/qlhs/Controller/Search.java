package com.qlhs.qlhs.Controller;

import com.qlhs.qlhs.Database.StudentDAO;
import com.qlhs.qlhs.Database.StudentGradeDAO;
import com.qlhs.qlhs.Model.Student;
import com.qlhs.qlhs.Model.StudentGrade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Objects;

public class Search {

    static ObservableList<Student> filter(String query) {
        ObservableList<Student> filteredStudents;
        ObservableList<Student> students = FXCollections.observableArrayList(StudentDAO.getStudents())
                .filtered(student -> Objects.equals(student.getStatus(), true));

        // Split search terms by comma
        String[] searchTerms = query.split(",");

        filteredStudents = students.filtered(student -> {
            // Default to true; will check each condition
            for (String term : searchTerms) {
                String trimmedTerm = term.trim();

                // Check for operator in the condition
                String operator = null;
                if (trimmedTerm.contains(">=")) {
                    operator = ">=";
                } else if (trimmedTerm.contains("<=")) {
                    operator = "<=";
                } else if (trimmedTerm.contains(">")) {
                    operator = ">";
                } else if (trimmedTerm.contains("<")) {
                    operator = "<";
                } else if (trimmedTerm.contains("=")) {
                    operator = "=";
                }

                // Split field and value
                String[] parts = trimmedTerm.split("[><=]+");
                if (parts.length == 2) {
                    String fieldName = parts[0].trim().toLowerCase(); // Field to search
                    String searchValue = parts[1].trim(); // Search value

                    // Check condition based on operator
                    boolean matches = false;

                    switch (fieldName) {
                        case "tên":
                        case "ten":
                        case "name":
                        case "t":
                        case "n":
                        case "last name":
                        case "lname":
                        case "ln":
                            matches = compareString(student.getLastName(), searchValue, operator);
                            break;
                        case "họ đệm":
                        case "ho dem":
                        case "hodem":
                        case "hd":
                        case "surname":
                        case "sname":
                        case "sn":
                        case "middle name":
                        case "mname":
                        case "mn":
                        case "first name":
                        case "fname":
                        case "fn":
                            matches = compareString(student.getFirstName(), searchValue, operator);
                            break;
                        case "mã học sinh":
                        case "ma hoc sinh":
                        case "mahocsinh":
                        case "mhs":
                        case "mahs":
                        case "student id":
                        case "studendid":
                        case "sid":
                            matches = compareString(student.getStudentID(), searchValue, operator);
                            break;

                        case "số điện thoại":
                        case "sodienthoai":
                        case "so dien thoai":
                        case "sdt":
                        case "phonenumber":
                        case "pn":
                            matches = compareString(student.getPhoneNumber(), searchValue, operator);
                            break;
                        case "email":
                        case "em":
                            if (operator.equals("=") && searchValue.isEmpty()) {
                                // Check if email field is empty
                                matches = (student.getEmail() == null || student.getEmail().isEmpty());
                            } else {
                                // Normal comparison if searchValue is not empty
                                matches = compareString(student.getEmail(), searchValue, operator);
                            }
                            break;

                        case "địa chỉ":
                        case "dia chi":
                        case "diachi":
                        case "dc":
                        case "address":
                        case "add":
                            matches = compareString(student.getAddress(), searchValue, operator);
                            break;
                        case "ghi chú":
                        case "ghi chu":
                        case "ghichu":
                        case "gc":
                        case "note":
                            matches = compareString(student.getNotes(), searchValue, operator);
                            break;
                        case "ngày sinh":
                        case "ngay sinh":
                        case "ngaysinh":
                        case "ns":
                        case "birth":
                        case "birth day":
                        case "bd":
                            matches = compareString(student.getDateOfBirth(), searchValue, operator);
                            break;
                        case "lớp":
                        case "lop":
                        case "l":
                        case "class":
                        case "cl":
                            matches = compareString(student.getClassName(), searchValue, operator);
                            break;
                        case "giới tính":
                        case "gioi tinh":
                        case "gioitinh":
                        case "gt":
                        case "sex":
                            matches = compareString(
                                    (student.getGender() ? "1" : "0"),
                                    (searchValue.equals("1") || searchValue.equals("nam")) ? "1" :
                                            (searchValue.equals("0") || searchValue.equals("nu")) ? "0" : null,
                                    operator
                            );
                            break;
                        case "mã định danh":
                        case "ma dinh danh":
                        case "madinhdanh":
                        case "mdd":
                        case "id":
                            matches = compareString(student.getID(), searchValue, operator);
                            break;
                        default:
                            matches = false;
                            break;
                    }

                    if (!matches) {
                        return false; // If one condition does not match, exclude this student
                    }
                } else {
                    // Free search if no operator
                    boolean matches = (student.getStudentID() != null && student.getStudentID().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (student.getFirstName() != null && student.getFirstName().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (student.getLastName() != null && student.getLastName().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (student.getPhoneNumber() != null && student.getPhoneNumber().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (student.getEmail() != null && student.getEmail().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (student.getClassName() != null && student.getClassName().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (student.getAddress() != null && student.getAddress().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (student.getNotes() != null && student.getNotes().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (student.getDateOfBirth() != null && String.valueOf(student.getDateOfBirth()).contains(trimmedTerm)) ||
                            (student.getGender() != null && String.valueOf(student.getGender()).toLowerCase().contains(trimmedTerm.toLowerCase())) ||
                            (student.getID() != null && student.getID().toLowerCase().contains(trimmedTerm.toLowerCase()));

                    if (!matches) {
                        return false; // If one condition does not match, exclude this student
                    }
                }
            }

            return true; // If all conditions match
        });

        return filteredStudents;
    }


    // Method to filter studentGrades
    static ObservableList<StudentGrade> filterGrade(String query) {
        ObservableList<StudentGrade> filteredGrades;
        ObservableList<StudentGrade> studentGrades = StudentGradeDAO.getStudentGrade().filtered(studentGrade -> Objects.equals(studentGrade.getStatus(), true));
        // Split search terms by comma
        String[] searchTerms = query.split(",");

        filteredGrades = studentGrades.filtered(studentGrade -> {
            // Default to true; will check each condition
            for (String term : searchTerms) {
                String trimmedTerm = term.trim();

                // Check for operator in the condition
                String operator = null;
                if (trimmedTerm.contains(">=")) {
                    operator = ">=";
                } else if (trimmedTerm.contains("<=")) {
                    operator = "<=";
                } else if (trimmedTerm.contains(">")) {
                    operator = ">";
                } else if (trimmedTerm.contains("<")) {
                    operator = "<";
                } else if (trimmedTerm.contains("=")) {
                    operator = "=";
                }

                // Split field and value
                String[] parts = trimmedTerm.split("[><=]+");
                if (parts.length == 2) {
                    String fieldName = parts[0].trim().toLowerCase(); // Field to search
                    String searchValue = parts[1].trim(); // Search value

                    // Check condition based on operator
                    boolean matches = false;

                    switch (fieldName) {
                        case "ngày sinh":
                        case "ngay sinh":
                        case "ngaysinh":
                        case "ns":
                        case "birth":
                        case "birth day":
                        case "bd":
                            matches = compareString(studentGrade.getDateOfBirth(), searchValue, operator);
                            break;
                        case "lớp":
                        case "lop":
                        case "l":
                        case "class":
                        case "cl":
                            matches = compareString(studentGrade.getClassName(), searchValue, operator);
                            break;
                        case "giới tính":
                        case "gioi tinh":
                        case "gioitinh":
                        case "gt":
                        case "sex":
                            matches = compareString(String.valueOf(studentGrade.getGender()),
                                    (searchValue.equals("1") || searchValue.equals("nam")) ? "1" :
                                            (searchValue.equals("0") || searchValue.equals("nu")) ? "0" : null,
                                    operator);
                            break;
                        case "tên":
                        case "ten":
                        case "name":
                        case "t":
                        case "n":
                        case "last name":
                        case "lname":
                        case "ln":
                            matches = compareString(studentGrade.getLastName(), searchValue, operator);
                            break;
                        case "họ đệm":
                        case "ho dem":
                        case "hodem":
                        case "hd":
                        case "surname":
                        case "sname":
                        case "sn":
                        case "middle name":
                        case "mname":
                        case "mn":
                        case "first name":
                        case "fname":
                        case "fn":
                            matches = compareString(studentGrade.getFirstName(), searchValue, operator);
                            break;
                        case "mã học sinh":
                        case "ma hoc sinh":
                        case "mahocsinh":
                        case "mhs":
                        case "mahs":
                        case "student id":
                        case "studendid":
                        case "sid":
                            matches = compareString(studentGrade.getStudentID(), searchValue, operator);
                            break;
                        case "điểm trung bình":
                        case "diemtrungbinh":
                        case "dtb":
                        case "diemtb":
                        case "average studentGrade":
                        case "ag":
                        case "avg":
                        case "avg studentGrade":
                        case "avgstudentGrade":
                            matches = studentGrade.getAvgGrade() != null && compareFloat(studentGrade.getAvgGrade(), searchValue, operator);
                            break;
                        case "ngữ văn":
                        case "nguvan":
                        case "van":
                        case "nv":
                        case "lit":
                        case "literature":
                            matches = studentGrade.getLiterature() != null && compareFloat(studentGrade.getLiterature(), searchValue, operator);
                            break;
                        case "toán":
                        case "toan":
                        case "math":
                            matches = studentGrade.getMath() != null && compareFloat(studentGrade.getMath(), searchValue, operator);
                            break;
                        case "vật lí":
                        case "vat li":
                        case "vatli":
                        case "li":
                        case "vl":
                        case "physics":
                        case "phy":
                            matches = studentGrade.getPhysics() != null && compareFloat(studentGrade.getPhysics(), searchValue, operator);
                            break;
                        case "hóa học":
                        case "hoa hoc":
                        case "hoahoc":
                        case "hoa":
                        case "hh":
                        case "chemistry":
                        case "chem":
                            matches = studentGrade.getChemistry() != null && compareFloat(studentGrade.getChemistry(), searchValue, operator);
                            break;
                        case "sinh học":
                        case "sinhhoc":
                        case "sinh":
                        case "sh":
                        case "biology":
                        case "bio":
                        case "b":
                            matches = studentGrade.getBiology() != null && compareFloat(studentGrade.getBiology(), searchValue, operator);
                            break;
                        case "lịch sử":
                        case "lich su":
                        case "su":
                        case "ls":
                        case "lichsu":
                        case "history":
                        case "hist":
                        case "h":
                            matches = studentGrade.getHistory() != null && compareFloat(studentGrade.getHistory(), searchValue, operator);
                            break;
                        case "địa lý":
                        case "dialy":
                        case "dia":
                        case "dl":
                        case "geography":
                        case "geo":
                        case "g":
                            matches = studentGrade.getGeography() != null && compareFloat(studentGrade.getGeography(), searchValue, operator);
                            break;
                        case "giáo dục công dân":
                        case "giaoduccongdan":
                        case "công dân":
                        case "congdan":
                        case "cong dan":
                        case "gdcd":
                        case "civic edu":
                        case "civic":
                        case "ce":
                            matches = studentGrade.getCivicEdu() != null && compareFloat(studentGrade.getCivicEdu(), searchValue, operator);
                            break;
                        case "công nghệ":
                        case "congnghe":
                        case "cong nghe":
                        case "cn":
                        case "technology":
                        case "tech":
                            matches = studentGrade.getTechnology() != null && compareFloat(studentGrade.getTechnology(), searchValue, operator);
                            break;
                        case "tin học":
                        case "tinhoc":
                        case "tin hoc":
                        case "tin":
                        case "th":
                        case "it":
                            matches = studentGrade.getIt() != null && compareFloat(studentGrade.getIt(), searchValue, operator);
                            break;
                        case "thể dục":
                        case "theduc":
                        case "the duc":
                        case "td":
                        case "physical education":
                        case "physicaleducation":
                        case "pe":
                            matches = studentGrade.getPhysicalEdu() != null && compareString(studentGrade.getPhysicalEdu(), searchValue, operator);
                            break;
                        case "ngoại ngữ":
                        case "ngoaingu":
                        case "nn":
                        case "foreign lang":
                        case "foreign":
                        case "lang":
                        case "fl":
                            matches = studentGrade.getForeignLang() != null && compareFloat(studentGrade.getForeignLang(), searchValue, operator);
                            break;
                        case "mã ngôn ngữ":
                        case "mangonngu":
                        case "mnn":
                        case "language code":
                        case "lang code":
                        case "lc":
                            matches = studentGrade.getLanguageCode() != null && compareString(studentGrade.getLanguageCode(), searchValue, operator);
                            break;
                        case "học lực":
                        case "hocluc":
                        case "hl":
                        case "academic performance":
                        case "academicperformance":
                        case "performance":
                        case "ap":
                            matches = studentGrade.getAcademicPerformance() != null && compareString(studentGrade.getAcademicPerformance(), searchValue, operator);
                            break;
                        case "hạnh kiểm":
                        case "hanhkiem":
                        case "hk":
                        case "conduct":
                        case "cd":
                            matches = studentGrade.getConduct() != null && compareString(studentGrade.getConduct(), searchValue, operator);
                            break;
                        case "ghi chú điểm":
                        case "ghichudiem":
                        case "gcd":
                        case "studentGrade notes":
                        case "notes":
                        case "gn":
                            matches = studentGrade.getGradeNotes() != null && compareString(studentGrade.getGradeNotes(), searchValue, operator);
                            break;
                        case "danh hiệu":
                        case "kết quả học tập":
                        case "dh":
                        case "danhhieu":
                        case "ketquahoctap":
                        case "ketqua":
                        case "kqht":
                        case "kq":
                        case "award":
                            matches = studentGrade.getAward() != null && compareString(studentGrade.getAward(), searchValue, operator);
                            break;
                        default:
                            matches = false;
                            break;
                    }

                    if (!matches) {
                        return false; // If one condition does not match, exclude this student
                    }
                } else {
                    // Free search if no operator
                    boolean matches = checkFreeSearch(studentGrade, trimmedTerm);
                    if (!matches) {
                        return false; // If one condition does not match, exclude this student
                    }
                }
            }

            return true; // If all conditions match
        });

        return filteredGrades;
    }

    // New method to handle free search
    private static boolean checkFreeSearch(StudentGrade studentGrade, String term) {
        return (studentGrade.getStudentID() != null && studentGrade.getStudentID().toLowerCase().contains(term.toLowerCase())) ||
                (studentGrade.getLastName() != null && studentGrade.getLastName().toLowerCase().contains(term.toLowerCase())) ||
                (studentGrade.getFirstName() != null && studentGrade.getFirstName().toLowerCase().contains(term.toLowerCase())) ||
                (studentGrade.getDateOfBirth() != null && studentGrade.getDateOfBirth().toLowerCase().contains(term.toLowerCase())) ||
                (studentGrade.getGender() != null && (studentGrade.getGender() ? "Nam" : "Nữ").toLowerCase().contains(term.toLowerCase())) || // Gender as boolean
                (studentGrade.getClassName() != null && studentGrade.getClassName().toLowerCase().contains(term.toLowerCase())) ||
                (studentGrade.getLiterature() != null && String.valueOf(studentGrade.getLiterature()).contains(term)) || // Float conversion
                (studentGrade.getMath() != null && String.valueOf(studentGrade.getMath()).contains(term)) ||
                (studentGrade.getPhysics() != null && String.valueOf(studentGrade.getPhysics()).contains(term)) ||
                (studentGrade.getChemistry() != null && String.valueOf(studentGrade.getChemistry()).contains(term)) ||
                (studentGrade.getBiology() != null && String.valueOf(studentGrade.getBiology()).contains(term)) ||
                (studentGrade.getHistory() != null && String.valueOf(studentGrade.getHistory()).contains(term)) ||
                (studentGrade.getGeography() != null && String.valueOf(studentGrade.getGeography()).contains(term)) ||
                (studentGrade.getCivicEdu() != null && String.valueOf(studentGrade.getCivicEdu()).contains(term)) ||
                (studentGrade.getTechnology() != null && String.valueOf(studentGrade.getTechnology()).contains(term)) ||
                (studentGrade.getIt() != null && String.valueOf(studentGrade.getIt()).contains(term)) ||
                (studentGrade.getPhysicalEdu() != null && String.valueOf(studentGrade.getPhysicalEdu()).contains(term)) ||
                (studentGrade.getForeignLang() != null && String.valueOf(studentGrade.getForeignLang()).contains(term)) ||
                (studentGrade.getLanguageCode() != null && studentGrade.getLanguageCode().toLowerCase().contains(term.toLowerCase())) ||
                (studentGrade.getAcademicPerformance() != null && studentGrade.getAcademicPerformance().toLowerCase().contains(term.toLowerCase())) ||
                (studentGrade.getConduct() != null && studentGrade.getConduct().toLowerCase().contains(term.toLowerCase())) ||
                (studentGrade.getGradeNotes() != null && studentGrade.getGradeNotes().toLowerCase().contains(term.toLowerCase())) ||
                (studentGrade.getAward() != null && studentGrade.getAward().toLowerCase().contains(term.toLowerCase())) ||
                (studentGrade.getAvgGrade() != null && String.valueOf(studentGrade.getAvgGrade()).contains(term)); // Float conversion
    }

    // Helper function to compare string values with operator
    private static boolean compareString(String actualValue, String searchValue, String operator) {
        if (actualValue == null || searchValue == null) return false;

        // Perform relative search if the operator is '='
        if ("=".equals(operator)) {
            return actualValue.toLowerCase().contains(searchValue.toLowerCase());
        }

        // Compare the actual value and the search value lexicographically
        float compare = actualValue.compareToIgnoreCase(searchValue);
        return checkCondition(operator, compare);
    }

    // Helper function to compare float values with operator
    private static boolean compareFloat(float actualValue, String searchValue, String operator) {
        if (searchValue == null) return false;

        try {
            float parsedValue = Float.parseFloat(searchValue);
            return checkCondition(operator, actualValue - parsedValue);
        } catch (NumberFormatException e) {
            return false; // Return false if searchValue cannot be parsed as float
        }
    }

    // Check operator and comparison value
    private static boolean checkCondition(String operator, float compare) {
        return switch (operator) {
            case ">" -> compare > 0;
            case "<" -> compare < 0;
            case ">=" -> compare >= 0;
            case "<=" -> compare <= 0;
            default -> false;
        };
    }
}
