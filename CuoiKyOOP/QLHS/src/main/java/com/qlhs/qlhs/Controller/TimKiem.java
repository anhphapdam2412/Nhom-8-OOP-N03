package com.qlhs.qlhs.Controller;

import com.qlhs.qlhs.Database.HocSinhDAO;
import com.qlhs.qlhs.Model.HocSinh;
import javafx.collections.ObservableList;

import java.util.Objects;

public class TimKiem {
    static ObservableList<HocSinh> toanBo(){
        return HocSinhDAO.getDSHocSinh().filtered(hocSinh -> Objects.equals(hocSinh.getTrangThai(), "1"));
    }

    static ObservableList<HocSinh> theoMaHS(String string){
        ObservableList<HocSinh> dsHocSinhDaLoc;
        dsHocSinhDaLoc = toanBo().filtered(hocSinh -> Objects.equals(hocSinh.getMaHS(), string));
        return dsHocSinhDaLoc;
    }
}
