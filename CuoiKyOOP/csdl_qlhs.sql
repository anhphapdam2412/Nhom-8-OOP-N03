-- Create the database if it does not already exist
CREATE DATABASE IF NOT EXISTS csdl_qlhs;
USE csdl_qlhs;

-- Create the student table if it does not already exist
CREATE TABLE IF NOT EXISTS student (
    studentID VARCHAR(45) PRIMARY KEY,
    lastName VARCHAR(45),
    firstName VARCHAR(45),
    dateOfBirth DATE,
    gender TINYINT(1), -- 1: Male, 0: Female
    `id` VARCHAR(45),
    phoneNumber VARCHAR(45),
    email VARCHAR(45),
    className VARCHAR(45),
    address TEXT,
    status TINYINT(1), -- 1: Currently studying, 0: Dropped out
    notes TEXT
);

-- Create the grades table if it does not already exist
CREATE TABLE IF NOT EXISTS grade (
    studentID VARCHAR(45),
    literature FLOAT,
    math FLOAT,
    physics FLOAT,
    chemistry FLOAT,
    biology FLOAT,
    history FLOAT,
    geography FLOAT,
    civicEdu FLOAT,
    technology FLOAT,
    it FLOAT,
    physicalEdu CHAR(1),
    foreignLang FLOAT,
    avgGrade FLOAT(5, 2),
    languageCode CHAR(2),
    academicPerformance CHAR(20), -- Adjust length as needed
    conduct CHAR(3),
    gradeNotes TEXT,
    award FLOAT(5, 2),
    PRIMARY KEY (studentID),
    FOREIGN KEY (studentID) REFERENCES student(studentID)
);

-- Create trigger to calculate the average grade and academic performance
DELIMITER $$

CREATE TRIGGER trg_update_avgGrade_and_academicPerformance_on_update
BEFORE UPDATE ON grade
FOR EACH ROW
BEGIN
    -- Calculate the average grade for 11 subjects, excluding PE
    SET NEW.avgGrade = (
        (NEW.literature + NEW.math + NEW.physics + NEW.chemistry + NEW.biology + 
         NEW.history + NEW.geography + NEW.civicEdu + NEW.technology + 
         NEW.it + NEW.foreignLang) / 11
    );

    -- Update academic performance based on the avgGrade
    SET NEW.academicPerformance = (
        CASE 
            WHEN NEW.avgGrade < 5 THEN 'Trung bình'
            WHEN NEW.avgGrade >= 5 AND NEW.avgGrade < 6.5 THEN 'Khá'
            WHEN NEW.avgGrade >= 6.5 AND NEW.avgGrade < 8 THEN 'Giỏi'
            WHEN NEW.avgGrade >= 8 THEN 'Xuất sắc'
            ELSE 'Chưa xác định'
        END
    );
END$$

DELIMITER ;

INSERT INTO student (studentId, firstName, lastName, dateOfBirth, gender, id, phoneNumber, email, className, address, `status`, notes)
VALUES
('23010046', 'Nguyễn', 'Hà', '2006-03-12', 0, '030250005158', '0123123168', 'nguyenha@mail.com', '12C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 47', 1, 'Tham gia câu lạc bộ Hóa học'),
('23010047', 'Trần', 'Linh', '2007-05-09', 1, '030251005159', '0123123169', 'tranlinh@mail.com', '11C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 48', 1, 'Học sinh giỏi'),
('23010048', 'Lê', 'Phúc', '2008-01-23', 1, '030252005160', '0123123170', 'lephuc@mail.com', '10C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 49', 1, 'Tham gia đội tuyển Tin học'),
('23010049', 'Phạm', 'Quốc', '2006-08-17', 0, '030253005161', '0123123171', 'phamquoc@mail.com', '11B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 50', 1, 'Đang thi học kỳ I'),
('23010050', 'Vũ', 'Dũng', '2005-09-12', 1, '030254005162', '0123123172', 'vudung@mail.com', '12B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 51', 0, 'Đang tạm nghỉ học'),
('23010051', 'Hoàng', 'Lan', '2007-11-15', 0, '030255005163', '0123123173', 'hoanglan@mail.com', '11A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 52', 1, 'Đang thi học kỳ II'),
('23010052', 'Bùi', 'Nam', '2006-07-04', 1, '030256005164', '0123123174', 'buinam@mail.com', '10B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 53', 1, 'Tham gia đội tuyển Tiếng Anh'),
('23010053', 'Đỗ', 'Tú', '2005-04-29', 0, '030257005165', '0123123175', 'dotu@mail.com', '12A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 54', 0, 'Tạm nghỉ học do sức khỏe'),
('23010054', 'Ngô', 'Bình', '2006-06-13', 1, '030258005166', '0123123176', 'ngobinh@mail.com', '11C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 55', 1, 'Tham gia đội tuyển Sinh học'),
('23010055', 'Trần', 'Phương', '2007-03-19', 0, '030259005167', '0123123177', 'tranphuong@mail.com', '10A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 56', 1, 'Học sinh mới chuyển đến'),
('23010056', 'Phạm', 'Hiếu', '2008-12-11', 1, '030260005168', '0123123178', 'phamhuu@mail.com', '10B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 57', 1, 'Tham gia đội tuyển Địa lý'),
('23010057', 'Lê', 'Thảo', '2005-08-25', 0, '030261005169', '0123123179', 'lethao@mail.com', '12C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 58', 1, 'Học sinh có tiềm năng'),
('23010058', 'Nguyễn', 'Ngân', '2006-10-09', 1, '030262005170', '0123123180', 'nguyengan@mail.com', '11B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 59', 1, 'Tham gia các hoạt động ngoại khóa'),
('23010059', 'Trần', 'Mai', '2007-09-14', 0, '030263005171', '0123123181', 'tranmai@mail.com', '10C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 60', 1, 'Đạt danh hiệu học sinh giỏi Toán'),
('23010060', 'Lê', 'Khánh', '2008-02-27', 1, '030264005172', '0123123182', 'lekhanh@mail.com', '10A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 61', 1, 'Đang thi học kỳ II'),
('23010061', 'Phạm', 'Ngọc', '2006-04-12', 0, '030265005173', '0123123183', 'phamngoc@mail.com', '11A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 62', 1, 'Học sinh giỏi môn Công nghệ'),
('23010062', 'Vũ', 'Hà', '2005-05-07', 1, '030266005174', '0123123184', 'vuha@mail.com', '12B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 63', 1, 'Tham gia câu lạc bộ Khoa học'),
('23010063', 'Ngô', 'Minh', '2007-12-03', 0, '030267005175', '0123123185', 'ngominh@mail.com', '10B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 64', 1, 'Đang thi học kỳ I'),
('23010064', 'Trần', 'Tùng', '2006-11-29', 1, '030268005176', '0123123186', 'trantung@mail.com', '11C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 65', 1, 'Tham gia các kỳ thi học sinh giỏi'),
('23010065', 'Phạm', 'Khang', '2005-07-21', 1, '030269005177', '0123123187', 'phamkhang@mail.com', '12C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 66', 1, 'Học sinh giỏi môn Lịch sử');

INSERT INTO grade (studentID, literature, math, physics, chemistry, biology, history, geography, civicEdu, technology, it, physicalEdu, foreignLang, avgGrade, languageCode, academicPerformance, conduct, gradeNotes)
VALUES
('23010046', 7.5, 8.0, 7.0, 6.5, 7.0, 8.5, 7.0, 7.0, 8.0, 8.0, 'A', 7.5, 7.50, 'VN', 'Giỏi', 'T', 'Tham gia kỳ thi HSG Toán'),
('23010047', 6.0, 5.5, 6.0, 5.0, 5.5, 6.0, 5.5, 6.0, 6.5, 6.5, 'A', 6.0, 6.00, 'VN', 'Khá', 'T', 'Tham gia hoạt động văn nghệ'),
('23010048', 9.0, 8.5, 8.0, 8.5, 8.0, 9.0, 8.5, 9.0, 9.5, 9.0, 'A', 8.5, 8.50, 'VN', 'Xuất sắc', 'T', 'Đạt giải HSG Toán'),
('23010049', 5.5, 5.0, 5.5, 5.0, 5.5, 6.0, 5.5, 5.5, 6.0, 6.5, 'B', 6.0, 5.60, 'VN', 'Khá', 'K', 'Đang thi học kỳ'),
('23010050', 8.0, 7.5, 8.5, 7.0, 8.0, 9.0, 8.0, 7.5, 9.0, 8.0, 'A', 8.0, 7.50, 'VN', 'Giỏi', 'T', 'Tham gia câu lạc bộ Khoa học'),
('23010051', 4.5, 5.0, 4.0, 5.0, 4.5, 5.0, 4.0, 4.5, 5.0, 4.5, 'C', 5.5, 4.55, 'VN', 'Trung bình', 'K', 'Tham gia câu lạc bộ Hóa học'),
('23010052', 7.0, 6.5, 7.5, 6.0, 7.0, 8.0, 7.0, 7.0, 8.5, 8.0, 'B', 7.5, 7.00, 'VN', 'Giỏi', 'T', 'Tham gia đội tuyển Địa lý'),
('23010053', 5.0, 5.5, 5.5, 6.0, 5.5, 5.0, 5.5, 5.0, 5.5, 5.5, 'B', 5.5, 5.55, 'VN', 'Trung bình', 'K', 'Tham gia đội tuyển Lịch sử'),
('23010054', 8.5, 9.0, 8.0, 9.0, 8.5, 9.5, 8.5, 9.5, 9.0, 8.5, 'A', 8.5, 8.75, 'VN', 'Xuất sắc', 'T', 'Tham gia kỳ thi HSG Địa lý'),
('23010055', 6.5, 7.0, 7.0, 6.5, 7.5, 8.0, 7.0, 7.0, 8.0, 7.0, 'A', 7.5, 7.00, 'VN', 'Khá', 'T', 'Đang thi học kỳ'),
('23010056', 7.5, 7.0, 7.5, 6.5, 7.5, 8.0, 7.5, 7.0, 7.5, 7.0, 'A', 7.5, 7.40, 'VN', 'Giỏi', 'T', 'Tham gia đội tuyển Công nghệ'),
('23010057', 9.0, 8.5, 8.5, 8.0, 9.0, 9.5, 8.5, 8.0, 9.0, 9.5, 'A', 9.0, 8.75, 'VN', 'Xuất sắc', 'T', 'Đạt giải HSG Tin học'),
('23010058', 5.5, 6.0, 6.5, 5.5, 6.0, 6.5, 6.5, 6.0, 6.0, 6.0, 'A', 6.0, 5.95, 'VN', 'Khá', 'T', 'Tham gia câu lạc bộ Tin học'),
('23010059', 8.0, 7.5, 8.0, 7.0, 8.5, 9.0, 8.0, 8.5, 9.0, 9.5, 'A', 8.5, 8.25, 'VN', 'Giỏi', 'T', 'Đạt giải HSG Hóa học'),
('23010060', 6.0, 6.5, 7.0, 6.5, 6.0, 7.0, 7.0, 6.0, 7.0, 7.0, 'B', 6.5, 6.60, 'VN', 'Khá', 'K', 'Tham gia đội tuyển Lịch sử'),
('23010061', 7.0, 7.5, 7.0, 7.5, 7.0, 7.5, 7.0, 7.5, 8.0, 8.0, 'A', 7.5, 7.45, 'VN', 'Giỏi', 'T', 'Tham gia hoạt động tình nguyện'),
('23010062', 6.5, 7.0, 7.5, 6.0, 6.5, 7.0, 6.5, 6.0, 7.5, 7.0, 'B', 6.5, 6.95, 'VN', 'Khá', 'K', 'Tham gia kỳ thi HSG Sinh học'),
('23010063', 9.0, 8.5, 8.5, 8.0, 9.0, 9.5, 8.5, 8.0, 9.0, 9.5, 'A', 8.5, 8.75, 'VN', 'Xuất sắc', 'T', 'Đạt giải HSG Lịch sử'),
('23010064', 5.0, 5.5, 5.0, 6.0, 5.5, 5.0, 5.5, 5.5, 6.0, 6.5, 'B', 5.5, 5.50, 'VN', 'Trung bình', 'K', 'Đang thi học kỳ'),
('23010065', 8.5, 9.0, 8.0, 8.5, 9.0, 9.5, 8.5, 9.0, 9.0, 9.0, 'A', 8.5, 8.70, 'VN', 'Xuất sắc', 'T', 'Tham gia kỳ thi học sinh giỏi Toán');



