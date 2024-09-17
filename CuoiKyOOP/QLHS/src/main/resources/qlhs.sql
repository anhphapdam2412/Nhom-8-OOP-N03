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
ghichu text
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
ngonNgu float,
congNghe float,
tinHoc float,
theDuc char,
ghiChu text
);

INSERT INTO thongTinHocSinh 
(maHocSinh, hoDem, ten, ngaySinh, gioiTinh, maDinhDanh, sdt, email, lop, diaChi, ghichu) 
VALUES 
('HS001', 'Nguyen Van', 'A', '2005-09-01', 1, 'ID001', '0912345678', 'nguyenvana@example.com', '12A1', '123 Main St', 'None'),
('HS002', 'Tran Thi', 'B', '2006-03-15', 0, 'ID002', '0987654321', 'tranthib@example.com', '12A2', '456 Maple St', 'None');

