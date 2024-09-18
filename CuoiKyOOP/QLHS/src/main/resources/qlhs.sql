Create database if not exists CSDL_qlhs;
use CSDL_qlhs;
Create table if not exists thongTinHocSinh(
maHocSinh varchar(45) primary key,
hoDem varchar(45),
ten varchar(45),
ngaySinh date,
gioiTinh smallint,
maDinhDanh varchar(45),
sdt varchar(45),
email varchar(45),
lop varchar(45),
diaChi text,
ghichuTT text
);

Create table if not exists bangDiem(
maHocSinh varchar(45) primary key,
nguVan float,
toan float,
vatLi float,
hoaHoc float,
sinhHoc float,
lichSu float,
diaLy float,
GDCD float,
ngoaiNgu float,
congNghe float,
tinHoc float,
theDuc char,
ghiChuDiem text
);

INSERT INTO thongTinHocSinh 
(maHocSinh, hoDem, ten, ngaySinh, gioiTinh, maDinhDanh, sdt, email, lop, diaChi, ghichuTT) 
VALUES 
('HS001', 'Nguyen Van', 'A', '2005-09-01', 1, 'ID001', '0912345678', 'nguyenvana@example.com', '12A1', '123 Main St', 'None'),
('HS002', 'Tran Thi', 'B', '2006-03-15', 0, 'ID002', '0987654321', 'tranthib@example.com', '12A2', '456 Maple St', 'None');

INSERT INTO bangDiem (maHocSinh, nguVan, toan, vatLi, hoaHoc, sinhHoc, lichSu, diaLy, GDCD, ngoaiNgu, congNghe, tinHoc, theDuc, ghiChuDiem) 
VALUES
('HS001', 8.5, 9.0, 7.5, 8.0, 7.8, 6.5, 7.2, 8.0, 8.5, 7.0, 8.0, 'A', 'Excellent performance'),
('HS002', 7.5, 6.8, 8.0, 7.0, 8.2, 6.9, 7.5, 7.8, 7.0, 7.5, 7.9, 'B', 'Good performance');

