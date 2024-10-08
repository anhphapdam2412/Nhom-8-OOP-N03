package com.qlhs.qlhs.Controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActivityLog {
    private static final String LOG_FILE_PATH = "logs/log.txt";
    private static final long MAX_LOG_SIZE = 5 * 1024 * 1024; // 5 MB

    // Log user activity with additional details
    public static void logActivity(String username, String actionType, String affectedData, String previousValue, String newValue) {
        String message = "Người dùng: " + username
                + " | Hành động: " + actionType
                + " | Dữ liệu bị ảnh hưởng: " + affectedData
                + " | Giá trị trước: " + previousValue
                + " | Giá trị sau: " + newValue;
        logInformation(message);
    }

    // Original log function to write log messages
    public static void logInformation(String message) {
        try {
            if (Files.notExists(Paths.get("logs"))) {
                Files.createDirectory(Paths.get("logs"));
            }

            // Check log file size
            if (Files.exists(Paths.get(LOG_FILE_PATH)) && Files.size(Paths.get(LOG_FILE_PATH)) > MAX_LOG_SIZE) {
                // Create a new log file if the current one exceeds size limit
                Files.move(Paths.get(LOG_FILE_PATH), Paths.get("logs/log_" + getCurrentTimestampForFile() + ".txt"));
            }

            // Try-with-resources to auto-close BufferedWriter
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(LOG_FILE_PATH),
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

    // Function to get timestamp for logging
    private static String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    // Function to get timestamp for file name (used when the log file is too large)
    private static String getCurrentTimestampForFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        return LocalDateTime.now().format(formatter);
    }
}
