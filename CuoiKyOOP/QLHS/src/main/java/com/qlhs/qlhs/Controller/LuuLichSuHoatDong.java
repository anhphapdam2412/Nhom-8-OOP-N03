package com.qlhs.qlhs.Controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LuuLichSuHoatDong {
    private static final String LOG_DIRECTORY = "logs"; // Thư mục chứa log
    private static final String LOG_FILE_PATH = LOG_DIRECTORY + "/log.txt"; // Đường dẫn đến file log

    // Hàm ghi log với thông tin tùy chọn
    public static void logThongTin(String message) {
        try {
            // Kiểm tra và tạo thư mục logs nếu chưa tồn tại
            if (Files.notExists(Paths.get(LOG_DIRECTORY))) {
                Files.createDirectory(Paths.get(LOG_DIRECTORY));
            }

            // Ghi log vào file với ngày giờ
            BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true));
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("[" + timestamp + "] " + message);
            writer.newLine();
            writer.close();
            System.out.println("Log thành công");

        } catch (IOException e) {
            System.out.println("Có lỗi khi ghi log: " + e.getMessage());
        }
    }

    // Hàm ghi log với nhiều thông tin
    public static void logThongTin(String[] messages) {
        for (String message : messages) {
            logThongTin(message);
        }
    }
}

