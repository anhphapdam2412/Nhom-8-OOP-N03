package com.qlhs.qlhs.Controller;

import com.qlhs.qlhs.Database.AcademicTranscriptDAO;
import com.qlhs.qlhs.Database.StudentDAO;
import com.qlhs.qlhs.Model.AcademicTranscript;
import com.qlhs.qlhs.Model.Student;
import javafx.collections.ObservableList;

import java.util.Objects;

public class Search {

    static ObservableList<Student> filter(String query) {
        ObservableList<Student> filteredStudents;
        ObservableList<Student> allStudents = StudentDAO.getStudentList().filtered(student -> Objects.equals(student.getStatus(), "1"));

        // Split search terms by comma
        String[] searchTerms = query.split(",");

        filteredStudents = allStudents.filtered(student -> {
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
                            matches = compareString(student.getGender(),
                                    (searchValue.equals("1") || searchValue.equals("nam")) ? "1" :
                                            (searchValue.equals("0") || searchValue.equals("nu")) ? "0" : null,
                                    operator);
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
                            (student.getGender() != null && student.getGender().toLowerCase().contains(trimmedTerm.toLowerCase())) ||
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

    // Method to filter grades
    static ObservableList<AcademicTranscript> filterGrade(String query) {
        ObservableList<AcademicTranscript> filteredGrades;
        ObservableList<AcademicTranscript> allGrade = AcademicTranscriptDAO.getAcademicTranscript().filtered(gradeReport -> Objects.equals(gradeReport.getStatus(), "1"));
        // Split search terms by comma
        String[] searchTerms = query.split(",");

        filteredGrades = allGrade.filtered(grade -> {
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
                            matches = compareString(grade.getDateOfBirth(), searchValue, operator);
                            break;
                        case "lớp":
                        case "lop":
                        case "l":
                        case "class":
                        case "cl":
                            matches = compareString(grade.getClassName(), searchValue, operator);
                            break;
                        case "giới tính":
                        case "gioi tinh":
                        case "gioitinh":
                        case "gt":
                        case "sex":
                            matches = compareString(grade.getGender(),
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
                            matches = compareString(grade.getLastName(), searchValue, operator);
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
                            matches = compareString(grade.getFirstName(), searchValue, operator);
                            break;
                        case "mã học sinh":
                        case "ma hoc sinh":
                        case "mahocsinh":
                        case "mhs":
                        case "mahs":
                        case "student id":
                        case "studendid":
                        case "sid":
                            matches = compareString(grade.getStudentID(), searchValue, operator);
                            break;
                        case "điểm trung bình":
                        case "diemtrungbinh":
                        case "dtb":
                        case "diemtb":
                        case "average grade":
                        case "ag":
                        case "avg":
                        case "avg grade":
                        case "avggrade":
                            matches = grade.getAvgGrade() != null && compareFloat(Float.parseFloat(grade.getAvgGrade()), searchValue, operator);
                            break;
                        case "ngữ văn":
                        case "nguvan":
                        case "van":
                        case "nv":
                        case "lit":
                        case "literature":
                            matches = grade.getLiterature() != null && compareFloat(Float.parseFloat(grade.getLiterature()), searchValue, operator);
                            break;
                        case "toán":
                        case "toan":
                        case "math":
                            matches = grade.getMath() != null && compareFloat(Float.parseFloat(grade.getMath()), searchValue, operator);
                            break;
                        case "vật lí":
                        case "vat li":
                        case "vatli":
                        case "li":
                        case "vl":
                        case "physics":
                        case "phy":
                            matches = grade.getPhysics() != null && compareFloat(Float.parseFloat(grade.getPhysics()), searchValue, operator);
                            break;
                        case "hóa học":
                        case "hoa hoc":
                        case "hoahoc":
                        case "hoa":
                        case "hh":
                        case "chemistry":
                        case "chem":
                            matches = grade.getChemistry() != null && compareFloat(Float.parseFloat(grade.getChemistry()), searchValue, operator);
                            break;
                        case "sinh học":
                        case "sinhhoc":
                        case "sinh":
                        case "sh":
                        case "biology":
                        case "bio":
                        case "b":
                            matches = grade.getBiology() != null && compareFloat(Float.parseFloat(grade.getBiology()), searchValue, operator);
                            break;
                        case "lịch sử":
                        case "lich su":
                        case "su":
                        case "ls":
                        case "lichsu":
                        case "history":
                        case "hist":
                        case "h":
                            matches = grade.getHistory() != null && compareFloat(Float.parseFloat(grade.getHistory()), searchValue, operator);
                            break;
                        case "địa lý":
                        case "dialy":
                        case "dia":
                        case "dl":
                        case "geography":
                        case "geo":
                        case "g":
                            matches = grade.getGeography() != null && compareFloat(Float.parseFloat(grade.getGeography()), searchValue, operator);
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
                            matches = grade.getCivicEdu() != null && compareFloat(Float.parseFloat(grade.getCivicEdu()), searchValue, operator);
                            break;
                        case "công nghệ":
                        case "congnghe":
                        case "cong nghe":
                        case "cn":
                        case "technology":
                        case "tech":
                            matches = grade.getTechnology() != null && compareFloat(Float.parseFloat(grade.getTechnology()), searchValue, operator);
                            break;
                        case "tin học":
                        case "tinhoc":
                        case "tin hoc":
                        case "tin":
                        case "th":
                        case "it":
                            matches = grade.getIt() != null && compareFloat(Float.parseFloat(grade.getIt()), searchValue, operator);
                            break;
                        case "thể dục":
                        case "theduc":
                        case "the duc":
                        case "td":
                        case "physical education":
                        case "physicaleducation":
                        case "pe":
                            matches = grade.getPhysicalEdu() != null && compareFloat(Float.parseFloat(grade.getPhysicalEdu()), searchValue, operator);
                            break;
                        case "ngoại ngữ":
                        case "ngoaingu":
                        case "nn":
                        case "foreign lang":
                        case "foreign":
                        case "lang":
                        case "fl":
                            matches = grade.getForeignLang() != null && compareFloat(Float.parseFloat(grade.getForeignLang()), searchValue, operator);
                            break;
                        case "mã ngôn ngữ":
                        case "mangonngu":
                        case "mnn":
                        case "language code":
                        case "lang code":
                        case "lc":
                            matches = grade.getLanguageCode() != null && compareString(grade.getLanguageCode(), searchValue, operator);
                            break;
                        case "học lực":
                        case "hocluc":
                        case "hl":
                        case "academic performance":
                        case "academicperformance":
                        case "performance":
                        case "ap":
                            matches = grade.getAcademicPerformance() != null && compareString(grade.getAcademicPerformance(), searchValue, operator);
                            break;
                        case "hạnh kiểm":
                        case "hanhkiem":
                        case "hk":
                        case "conduct":
                        case "cd":
                            matches = grade.getConduct() != null && compareString(grade.getConduct(), searchValue, operator);
                            break;
                        case "ghi chú điểm":
                        case "ghichudiem":
                        case "gcd":
                        case "grade notes":
                        case "notes":
                        case "gn":
                            matches = grade.getGradeNotes() != null && compareString(grade.getGradeNotes(), searchValue, operator);
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
                            matches = grade.getAward() != null && compareString(grade.getAward(), searchValue, operator);
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
                    boolean matches = checkFreeSearch(grade, trimmedTerm);
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
    private static boolean checkFreeSearch(AcademicTranscript grade, String term) {
        return (grade.getStudentID() != null && grade.getStudentID().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getLastName() != null && grade.getLastName().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getFirstName() != null && grade.getFirstName().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getDateOfBirth() != null && grade.getDateOfBirth().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getGender() != null && grade.getGender().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getClassName() != null && grade.getClassName().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getLiterature() != null && grade.getLiterature().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getMath() != null && grade.getMath().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getPhysics() != null && grade.getPhysics().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getChemistry() != null && grade.getChemistry().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getBiology() != null && grade.getBiology().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getHistory() != null && grade.getHistory().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getGeography() != null && grade.getGeography().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getCivicEdu() != null && grade.getCivicEdu().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getTechnology() != null && grade.getTechnology().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getIt() != null && grade.getIt().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getPhysicalEdu() != null && grade.getPhysicalEdu().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getForeignLang() != null && grade.getForeignLang().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getLanguageCode() != null && grade.getLanguageCode().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getAcademicPerformance() != null && grade.getAcademicPerformance().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getConduct() != null && grade.getConduct().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getGradeNotes() != null && grade.getGradeNotes().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getStatus() != null && grade.getStatus().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getAward() != null && grade.getAward().toLowerCase().contains(term.toLowerCase())) ||
                (grade.getAvgGrade() != null && grade.getAvgGrade().toLowerCase().contains(term.toLowerCase()));
    }
}