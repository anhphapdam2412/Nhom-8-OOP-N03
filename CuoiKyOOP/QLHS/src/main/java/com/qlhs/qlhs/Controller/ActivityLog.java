package com.qlhs.qlhs.Controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActivityLog {
    private static final String LOG_DIRECTORY = "logs/";
    private static final String LOG_FILE_EXTENSION = ".txt";

    // Ghi lại hoạt động của người dùng
    public static void logActivity(ActionType actionType, String newValue) {
        String action;
        String message = "";
        switch (actionType) {
            case UPDATE:
                action = "UPDATE STUDENT | ";
                message = "Action: " + action
                        + "new: " + newValue;
                break;
            case UPDATE_GRADE:
                action = "UPDATE GRADE | ";
                message = "Action: " + action
                        + "new: " + newValue;
                break;
            case DELETE:
                action = "DELETE | ";
                message = "Action: " + action
                        + "ID: " + newValue;
                break;
        }

        logInformation(message);
    }

    // Ghi thông tin log
    public static void logInformation(String message) {
        try {
            if (Files.notExists(Paths.get(LOG_DIRECTORY))) {
                Files.createDirectory(Paths.get(LOG_DIRECTORY));
            }

            // Ghi log thông tin
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(getLogFilePathForToday()),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {

                String timestamp = getCurrentTimestamp();
                writer.write("[" + timestamp + "] " + message);
                writer.newLine();
            }
            System.out.println("Log written successfully!");

        } catch (IOException e) {
            System.err.println("Error writing log: " + e.getMessage());
        }
    }

    // Lấy đường dẫn file ngày hiện tại
    private static String getLogFilePathForToday() {
        return LOG_DIRECTORY + "log_" + getCurrentDateForFile() + LOG_FILE_EXTENSION;
    }

    // Lấy timestamp
    private static String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
    // Lấy ngày hiện tại cho tên file
    private static String getCurrentDateForFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDateTime.now().format(formatter);
    }

    // Định nghĩa enum cho loại hành động
    public enum ActionType {
        ADD,
        UPDATE,
        DELETE,
        UPDATE_GRADE
    }
}
