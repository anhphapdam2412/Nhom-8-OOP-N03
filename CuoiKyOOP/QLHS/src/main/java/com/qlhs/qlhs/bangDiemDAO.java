package com.qlhs.qlhs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class bangDiemDAO {

    public static ObservableList<bangDiem> getBangDiem() {
        ObservableList<Student> students = StudentDAO.getStudents();
        ObservableList<Diem> diems = DiemDAO.getDiem();

        ObservableList<bangDiem> combinedList = FXCollections.observableArrayList();

        for (Student student : students) {
            // Find the corresponding mark for the student
            for (Diem diem : diems) {
                if (student.getMaHS().equals(diem.getMaHS())) {
                    // Create a new bangDiem object combining student and diem info
                    bangDiem studentMark = new bangDiem(
                            student.getStt(),
                            student.getMaHS(),
                            student.getHoDem(),
                            student.getTen(),
                            student.getNgaySinh(),
                            student.getGioiTinh(),
                            student.getMaDinhDanh(),
                            diem.getNguVan(),
                            diem.getToan(),
                            diem.getVatLi(),
                            diem.getHoaHoc(),
                            diem.getSinhHoc(),
                            diem.getLichSu(),
                            diem.getDiaLy(),
                            diem.getGDCD(),
                            diem.getCongNghe(),
                            diem.getTinHoc(),
                            diem.getTheDuc(),
                            diem.getNgoaiNgu(),
                            diem.getMaNN(),
                            diem.getHocLuc(),
                            diem.getHanhKiem(),
                            diem.getGhiChuDiem()
                    );
                    combinedList.add(studentMark);
                    break;
                }
            }
        }

        return combinedList;
    }
}
