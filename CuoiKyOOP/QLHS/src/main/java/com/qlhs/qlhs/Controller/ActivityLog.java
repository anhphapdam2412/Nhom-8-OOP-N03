package com.qlhs.qlhs.Controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class ActivityLog {
    private static final String LOG_FILE_PATH = "logs/log.txt";
    private static final long MAX_LOG_SIZE = 5 * 1024 * 1024; // 5 MB
    private static final long LOG_RETENTION_PERIOD = TimeUnit.DAYS.toMillis(30); // 30 ngày

    // Định nghĩa enum cho loại hành động
    public enum ActionType {
        ADD,
        UPDATE,
        DELETE
    }

    // Ghi lại hoạt động của người dùng
    public static void logActivity(String username, ActionType actionType, String affectedData, String previousValue, String newValue) {
        String action = "";
        switch (actionType) {
            case ADD:
                action = "Thêm mới";
                break;
            case UPDATE:
                action = "Sửa đổi";
                break;
            case DELETE:
                action = "Xóa";
                break;
        }

        String message = "Người dùng: " + username
                + " | Hành động: " + action
                + " | Dữ liệu bị ảnh hưởng: " + affectedData
                + " | Giá trị trước: " + previousValue
                + " | Giá trị sau: " + newValue;
        logInformation(message);
    }

    // Ghi thông tin log
    public static void logInformation(String message) {
        try {
            if (Files.notExists(Paths.get("logs"))) {
                Files.createDirectory(Paths.get("logs"));
            }

            // Kiểm tra kích thước file log
            if (Files.exists(Paths.get(LOG_FILE_PATH)) && Files.size(Paths.get(LOG_FILE_PATH)) > MAX_LOG_SIZE) {
                // Tạo một file log mới nếu file hiện tại vượt quá giới hạn kích thước
                Files.move(Paths.get(LOG_FILE_PATH), Paths.get("logs/log_" + getCurrentTimestampForFile() + ".txt"));
            }

            // Ghi log thông tin
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(LOG_FILE_PATH),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {

                String timestamp = getCurrentTimestamp();
                writer.write("[" + timestamp + "] " + message);
                writer.newLine();
            }
            System.out.println("Log written successfully!");

            // Xóa các log cũ
            cleanOldLogs();

        } catch (IOException e) {
            System.err.println("Error writing log: " + e.getMessage());
        }
    }

    // Phương thức xóa các file log cũ
    public static void cleanOldLogs() {
        try {
            // Lấy thời gian hiện tại
            long currentTimeMillis = System.currentTimeMillis();

            // Duyệt qua tất cả các file trong thư mục logs
            Files.list(Paths.get("logs"))
                    .filter(Files::isRegularFile) // Chỉ lọc các file thường
                    .forEach(path -> {
                        try {
                            // Lấy thời gian sửa đổi cuối cùng của file
                            long lastModifiedTime = Files.getLastModifiedTime(path).toMillis();
                            // Kiểm tra xem file đã quá thời gian quy định chưa
                            if ((currentTimeMillis - lastModifiedTime) > LOG_RETENTION_PERIOD) {
                                // Xóa file log cũ
                                Files.delete(path);
                                System.out.println("Deleted old log file: " + path.getFileName());
                            }
                        } catch (IOException e) {
                            System.err.println("Error deleting log file: " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            System.err.println("Error cleaning old logs: " + e.getMessage());
        }
    }

    // Phương thức để lấy timestamp cho việc ghi log
    private static String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    // Phương thức để lấy timestamp cho tên file (được sử dụng khi file log quá lớn)
    private static String getCurrentTimestampForFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        return LocalDateTime.now().format(formatter);
    }
}


