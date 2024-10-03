package com.qlhs.qlhs.Controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LuuLichSuHoatDong {
    private static final String LOG_FILE_PATH = "log.txt";
    public static void logThongTin(String string) {
        try {
            if (Files.notExists(Paths.get("logs"))) {
                Files.createDirectory(Paths.get("logs"));
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true));

            writer.write(string);
            writer.newLine();
            writer.close();
            System.out.println("Log ok");

        } catch (IOException e) {
            System.out.println("Có lỗi khi ghi log: " + e.getMessage());
        }
    }
}
