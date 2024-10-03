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
diemTb float(5,2)
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
hanhKiem char(3),
ghiChuDiem text
);

INSERT INTO thongtinhocsinh (maHS, hoDem, ten, ngaySinh, gioiTinh, maDinhDanh, sdt, email, lop, diaChi, trangThai, ghichuTT)
VALUES
('23010601', 'Đàm', 'Anh', '2005-01-02', 1, '030205005113', '0123123123', 'dama@mail.com', '12A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 2', 1, 'Không có ghi chú'),
('23010602', 'Nguyễn', 'Bình', '2006-03-15', 0, '030206005114', '0123123124', 'nguyenbinh@mail.com', '11B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 3', 1, 'Tham gia hoạt động ngoại khóa'),
('23010603', 'Trần', 'Châu', '2007-05-10', 1, '030207005115', '0123123125', NULL, '10C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 4', 1, 'Chờ xác nhận học bạ'),
('23010604', 'Phạm', 'Dương', '2005-12-25', 1, '030208005116', '0123123126', 'phamduong@mail.com', '12B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 5', 1, 'Thường xuyên vắng mặt'),
('23010605', 'Vũ', 'Hải', '2008-07-08', 0, '030209005117', '0123123127', NULL, '11C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 6', 0, 'Đã chuyển trường'),
('23010606', 'Hoàng', 'Giang', '2006-09-22', 1, '030210005118', '0123123128', 'hoanggiang@mail.com', '10A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 7', 1, 'Học sinh xuất sắc'),
('23010607', 'Lê', 'Huy', '2005-04-18', 0, '030211005119', '0123123129', 'lehuy@mail.com', '12C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 8', 1, 'Đạt giải thưởng học tập'),
('23010608', 'Bùi', 'Khoa', '2007-11-03', 1, '030212005120', '0123123130', NULL, '11A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 9', 1, 'Đã hoàn thành học kỳ I'),
('23010609', 'Đỗ', 'Lam', '2006-08-11', 1, '030213005121', '0123123131', 'dolam@mail.com', '10B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 10', 0, 'Nghỉ học vì lý do sức khỏe'),
('2301210', 'Ngô', 'Mai', '2008-02-17', 0, '030214005122', '0123123132', 'ngomai@mail.com', '11B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 11', 1, 'Tham gia câu lạc bộ văn nghệ'),
('2301211', 'Đinh', 'Nhật', '2005-06-21', 1, '030215005123', '0123123133', NULL, '12A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 12', 1, 'Chuẩn bị thi tốt nghiệp'),
('2301212', 'Lý', 'Oanh', '2006-10-09', 0, '030216005124', '0123123134', 'lyoanh@mail.com', '10C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 13', 1, 'Đang trong thời gian nghỉ thai sản'),
('2301213', 'Tô', 'Phú', '2007-09-29', 1, '030217005125', '0123123135', 'tophu@mail.com', '11A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 14', 1, 'Hoàn thành kiểm tra cuối kỳ'),
('2301214', 'Nguyễn', 'Quỳnh', '2005-08-12', 0, '030218005126', '0123123136', NULL, '12B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 15', 0, 'Bị đình chỉ học tập'),
('2301215', 'Trần', 'Sơn', '2008-03-01', 1, '030219005127', '0123123137', 'transon@mail.com', '11C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 16', 1, 'Được tuyên dương trong năm học'),
('2301216', 'Phạm', 'Thảo', '2006-12-20', 0, '030220005128', '0123123138', 'phamthao@mail.com', '10A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 17', 1, 'Nghỉ học tạm thời'),
('2301217', 'Vũ', 'Uyên', '2007-05-25', 1, '030221005129', '0123123139', NULL, '11B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 18', 1, 'Đã tham gia chương trình học nâng cao'),
('2301218', 'Hoàng', 'Việt', '2005-07-14', 0, '030222005130', '0123123140', 'hoangviet@mail.com', '12C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 19', 1, 'Đạt thành tích cao trong thể thao'),
('2301219', 'Lê', 'Xuân', '2008-06-19', 1, '030223005131', '0123123141', 'lexuan@mail.com', '11A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 20', 0, 'Đã chuyển trường sang khu vực khác'),
('2301220', 'Bùi', 'Yến', '2006-01-08', 0, '030224005132', '0123123142', 'buiyen@mail.com', '10B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 21', 1, 'Đang chuẩn bị cho kỳ thi sắp tới');


