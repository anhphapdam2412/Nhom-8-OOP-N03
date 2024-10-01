package com.qlhs.qlhs.Database;

import com.qlhs.qlhs.Model.BangDiem;
import com.qlhs.qlhs.Model.Diem;
import com.qlhs.qlhs.Model.HocSinh;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BangDiemDAO {

    public static ObservableList<BangDiem> getBangDiem() {
        ObservableList<HocSinh> dsHocSinh = HocSinhDAO.getDSHocSinh();
        ObservableList<Diem> diems = DiemDAO.getDiem();

        ObservableList<BangDiem> combinedList = FXCollections.observableArrayList();

        for (HocSinh hocSinh : dsHocSinh) {
            // Find the corresponding mark for the hocSinh
            for (Diem diem : diems) {
                if (hocSinh.getMaHS().equals(diem.getMaHS())) {
                    // Create a new BangDiem object combining hocSinh and diem info
                    BangDiem hocSinhMark = new BangDiem(hocSinh.getMaHS(), hocSinh.getHoDem(), hocSinh.getTen(), hocSinh.getNgaySinh(), hocSinh.getGioiTinh(), hocSinh.getLop(), diem.getNguVan(), diem.getToan(), diem.getVatLi(), diem.getHoaHoc(), diem.getSinhHoc(), diem.getLichSu(), diem.getDiaLy(), diem.getGDCD(), diem.getCongNghe(), diem.getTinHoc(), diem.getTheDuc(), diem.getNgoaiNgu(), diem.getMaNN(), diem.getHocLuc(), diem.getHanhKiem(), diem.getGhiChuDiem(), hocSinh.getTrangThai(), diem.getDiemTb());
                    combinedList.add(hocSinhMark);
                    break;
                }
            }
        }

        return combinedList;
    }
}
