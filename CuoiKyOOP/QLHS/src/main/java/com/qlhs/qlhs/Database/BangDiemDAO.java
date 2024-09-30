package com.qlhs.qlhs.Database;

import com.qlhs.qlhs.Model.BangDiem;
import com.qlhs.qlhs.Model.Diem;
import com.qlhs.qlhs.Model.HocSinh;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BangDiemDAO {

    public static ObservableList<BangDiem> getBangDiem() {
        ObservableList<HocSinh> students = HocSinhDAO.getDSHocSinh();
        ObservableList<Diem> diems = DiemDAO.getDiem();

        ObservableList<BangDiem> combinedList = FXCollections.observableArrayList();

        for (HocSinh student : students) {
            // Find the corresponding mark for the student
            for (Diem diem : diems) {
                if (student.getMaHS().equals(diem.getMaHS())) {
                    // Create a new BangDiem object combining student and diem info
                    BangDiem studentMark = new BangDiem(student.getStt(), student.getMaHS(), student.getHoDem(), student.getTen(), student.getNgaySinh(), student.getGioiTinh(), student.getLop(), diem.getNguVan(), diem.getToan(), diem.getVatLi(), diem.getHoaHoc(), diem.getSinhHoc(), diem.getLichSu(), diem.getDiaLy(), diem.getGDCD(), diem.getCongNghe(), diem.getTinHoc(), diem.getTheDuc(), diem.getNgoaiNgu(), diem.getMaNN(), diem.getHocLuc(), diem.getHanhKiem(), diem.getGhiChuDiem());
                    combinedList.add(studentMark);
                    break;
                }
            }
        }

        return combinedList;
    }
}
