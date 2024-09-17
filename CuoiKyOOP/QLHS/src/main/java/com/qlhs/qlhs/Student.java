package com.qlhs.qlhs;

public class Student {
    private String maHS;
    private String hoDem;
    private String ten;
    private String ngaySinh;
    private String gioiTinh;
    private String maDinhDanh;
    private String sdt;
    private String email;
    private String lop;
    private String diaChi;
    private String ghiChu;

    // Constructor
    public Student(String maHS, String hoDem, String ten, String ngaySinh, String gioiTinh,
                   String maDinhDanh, String sdt, String email, String lop, String diaChi, String ghiChu) {
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
        this.ghiChu = ghiChu;
    }

    // Getters and Setters for each field
    public String getMaHS() { return maHS; }
    public String getHoDem() { return hoDem; }
    public String getTen() { return ten; }
    public String getNgaySinh() { return ngaySinh; }
    public String getGioiTinh() { return gioiTinh; }
    public String getMaDinhDanh() { return maDinhDanh; }
    public String getSdt() { return sdt; }
    public String getEmail() { return email; }
    public String getLop() { return lop; }
    public String getDiaChi() { return diaChi; }
    public String getGhiChu() { return ghiChu; }
}
