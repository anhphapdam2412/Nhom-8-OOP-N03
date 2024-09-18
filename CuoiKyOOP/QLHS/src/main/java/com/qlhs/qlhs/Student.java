package com.qlhs.qlhs;

public class Student {
    private final String stt;
    private final String maHS;
    private final String hoDem;
    private final String ten;
    private final String ngaySinh;
    private final String gioiTinh;
    private final String maDinhDanh;
    private final String sdt;
    private final String email;
    private final String lop;
    private final String diaChi;
    private final String ghiChuTT;

    // Constructor
    public Student(String stt,String maHS, String hoDem, String ten, String ngaySinh, String gioiTinh,
                   String maDinhDanh, String sdt, String email, String lop, String diaChi, String ghiChuTT) {
        this.stt = stt;
        this.maHS = maHS;
        this.hoDem = hoDem;
        this.ten = ten;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.maDinhDanh = maDinhDanh;
        this.sdt = sdt;
        this.email = email;
        this.lop = lop;
        this.diaChi = diaChi;
        this.ghiChuTT = ghiChuTT;
    }

    // Getters and Setters for each field
    public String getStt() {
        return stt;
    }

    public String getMaHS() {
        return maHS;
    }

    public String getHoDem() {
        return hoDem;
    }

    public String getTen() {
        return ten;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public String getMaDinhDanh() {
        return maDinhDanh;
    }

    public String getSdt() {
        return sdt;
    }

    public String getEmail() {
        return email;
    }

    public String getLop() {
        return lop;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getGhiChuTT() {
        return ghiChuTT;
    }
}
