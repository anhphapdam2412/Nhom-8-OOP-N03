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
CREATE TABLE IF NOT EXISTS grades (
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
    physicalEducation CHAR(1),
    foreignLanguage FLOAT,
    avgGrade FLOAT(5, 2),
    languageCode CHAR(2),
    academicPerformance CHAR(20), -- Adjust length as needed
    conduct CHAR(3),
    gradeNote TEXT,
    PRIMARY KEY (studentID),
    FOREIGN KEY (studentID) REFERENCES student(studentID)
);

-- Create trigger to calculate the average grade and academic performance
DELIMITER $$

CREATE TRIGGER trg_update_avgGrade_and_academicPerformance_on_update
BEFORE UPDATE ON grades
FOR EACH ROW
BEGIN
    -- Calculate the average grade for 11 subjects, excluding PE
    SET NEW.avgGrade = (
        (NEW.literature + NEW.math + NEW.physics + NEW.chemistry + NEW.biology + 
         NEW.history + NEW.geography + NEW.civicEdu + NEW.technology + 
         NEW.it + NEW.foreignLanguage) / 11
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


INSERT INTO student (studentId, firstName, lastName, dateOfBirth, gender, ID, phoneNumber, email, className, address, `status`, notes)
VALUES
('23010021', 'Nguyễn', 'Bình', '2005-03-14', 1, '030225005133', '0123123143', 'nguyenbinh2@mail.com', '12A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 22', 1, 'Chuẩn bị tốt nghiệp'),
('23010022', 'Trần', 'Châu', '2006-05-11', 0, '030226005134', '0123123144', NULL, '11B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 23', 1, 'Học sinh gương mẫu'),
('23010023', 'Lê', 'Diệu', '2008-12-25', 1, '030227005135', '0123123145', 'ledieu@mail.com', '10C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 24', 1, 'Tham gia hoạt động văn nghệ'),
('23010024', 'Phạm', 'Đạt', '2007-07-09', 1, '030228005136', '0123123146', 'phamdat@mail.com', '11A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 25', 0, 'Tạm nghỉ học'),
('23010025', 'Vũ', 'Hương', '2005-01-30', 0, '030229005137', '0123123147', NULL, '12B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 26', 1, 'Đạt thành tích học tập tốt'),
('23010026', 'Hoàng', 'Khánh', '2006-09-20', 1, '030230005138', '0123123148', 'hoangkhanh@mail.com', '10A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 27', 1, 'Đang thi học kỳ II'),
('23010027', 'Bùi', 'Lan', '2007-08-15', 0, '030231005139', '0123123149', 'builan@mail.com', '11C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 28', 1, 'Học sinh mới chuyển đến'),
('23010028', 'Đỗ', 'Minh', '2005-06-11', 1, '030232005140', '0123123150', 'dominh@mail.com', '12A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 29', 1, 'Tham gia đội tuyển Toán'),
('23010029', 'Ngô', 'Ngọc', '2008-03-05', 0, '030233005141', '0123123151', 'ngongoc@mail.com', '10B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 30', 1, 'Chờ đánh giá học lực'),
('23010030', 'Đinh', 'Oanh', '2006-10-17', 0, '030234005142', '0123123152', NULL, '11A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 31', 1, 'Đạt danh hiệu học sinh giỏi'),
('23010031', 'Lý', 'Phương', '2007-11-09', 1, '030235005143', '0123123153', 'lyphuong@mail.com', '10C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 32', 1, 'Học sinh có tiềm năng'),
('23010032', 'Tô', 'Quân', '2006-02-25', 1, '030236005144', '0123123154', 'toquan@mail.com', '11B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 33', 1, 'Tham gia các kỳ thi học sinh giỏi'),
('23010033', 'Nguyễn', 'Sơn', '2005-05-21', 1, '030237005145', '0123123155', 'nguyenson@mail.com', '12C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 34', 0, 'Tạm ngừng học do sức khỏe'),
('23010034', 'Trần', 'Thái', '2008-04-14', 0, '030238005146', '0123123156', 'tranthai@mail.com', '11A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 35', 1, 'Tham gia đội tuyển Hóa học'),
('23010035', 'Phạm', 'Uyên', '2007-06-17', 1, '030239005147', '0123123157', 'phamuyen@mail.com', '10B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 36', 1, 'Đạt giải nhì kỳ thi Hóa học'),
('23010036', 'Vũ', 'Việt', '2006-03-29', 1, '030240005148', '0123123158', 'vuviet@mail.com', '11C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 37', 1, 'Chuẩn bị tốt nghiệp'),
('23010037', 'Hoàng', 'Xuân', '2005-01-08', 0, '030241005149', '0123123159', NULL, '12A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 38', 1, 'Đạt thành tích tốt trong học tập'),
('23010038', 'Lê', 'Yến', '2006-07-18', 0, '030242005150', '0123123160', 'leyen@mail.com', '10A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 39', 1, 'Tham gia đội tuyển Văn học'),
('23010039', 'Bùi', 'Anh', '2007-10-20', 1, '030243005151', '0123123161', 'buianh@mail.com', '11B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 40', 1, 'Học sinh giỏi Toán'),
('23010040', 'Nguyễn', 'Bảo', '2005-09-30', 1, '030244005152', '0123123162', 'nguyenbao@mail.com', '12B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 41', 0, 'Nghỉ học vì lý do gia đình'),
('23010041', 'Trần', 'Chính', '2008-12-25', 0, '030245005153', '0123123163', 'tranchinh@mail.com', '10C', 'Hải Dương, Hải Dương, Thạch Khôi, khu 42', 1, 'Đang hoàn thiện học lực'),
('23010042', 'Phạm', 'Dũng', '2006-04-19', 1, '030246005154', '0123123164', 'phamdung@mail.com', '11A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 43', 1, 'Học sinh giỏi Hóa học'),
('23010043', 'Vũ', 'Đạt', '2007-06-29', 0, '030247005155', '0123123165', 'vudat@mail.com', '10B', 'Hải Dương, Hải Dương, Thạch Khôi, khu 44', 1, 'Tham gia câu lạc bộ Tiếng Anh'),
('23010044', 'Hoàng', 'Giang', '2005-05-20', 1, '030248005156', '0123123166', NULL, '12A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 45', 1, 'Đạt danh hiệu học sinh giỏi Toán'),
('23010045', 'Lê', 'Hồng', '2006-11-11', 0, '030249005157', '0123123167', 'lehg@mail.com', '10A', 'Hải Dương, Hải Dương, Thạch Khôi, khu 46', 0, 'Nghỉ học')



