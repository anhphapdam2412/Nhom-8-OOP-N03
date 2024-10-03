package com.qlhs.qlhs.Controller;

import com.qlhs.qlhs.Database.HocSinhDAO;
import com.qlhs.qlhs.Model.HocSinh;
import javafx.collections.ObservableList;

import java.util.Objects;

public class TimKiem {
    static ObservableList<HocSinh> toanBo(){
        ObservableList<HocSinh> dsHocSinh = HocSinhDAO.getDSHocSinh();
        ObservableList<HocSinh> dsHocSinhDaLoc;

        // Lọc danh sách học sinh có trạng thái là 1
        dsHocSinhDaLoc = dsHocSinh.filtered(hocSinh -> Objects.equals(hocSinh.getTrangThai(), "1"));
        return dsHocSinhDaLoc;
    }

    static ObservableList<HocSinh> daLoc(String string){
        ObservableList<HocSinh> dsHocSinhDaLoc;
        dsHocSinhDaLoc = toanBo().filtered(hocSinh -> Objects.equals(hocSinh.getTen(), string));
        return dsHocSinhDaLoc;
    }
}
