Create database if not exists CSDL_qlhs;
use CSDL_qlhs;
Create table if not exists thongTinHocSinh(
maHS varchar(45) primary key,
hoDem varchar(45),
ten varchar(45),
ngaySinh date,
gioiTinh boolean,
maDinhDanh varchar(45),
sdt varchar(45),
email varchar(45),
lop varchar(45),
diaChi text,
trangThai boolean,
ghichuTT text
);

Create table if not exists bangDiem(
maHS varchar(45) primary key,
nguVan float,
toan float,
vatLi float,
hoaHoc float,
sinhHoc float,
lichSu float,
diaLy float,
GDCD float,
congNghe float,
tinHoc float,
theDuc char,
ngoaiNgu float,
diemTB float(5,2)
AS ((nguVan + toan + vatLi +hoaHoc+sinhHoc+lichSu+diaLy+GDCD+congNghe+tinHoc+ngoaiNgu) / 11),
maNN char(2),
hocLuc char(2)
AS (
    CASE 
        WHEN diemTB < 5 THEN 'TB'
        WHEN diemTB >= 5 AND diemTB < 6.5 THEN 'K'
        WHEN diemTB >= 6.5 AND diemTB < 8 THEN 'TT'
        WHEN diemTB >= 8 THEN 'G'
        ELSE ''
    END),
hanhKiem char(2),
ghiChuDiem text
);


