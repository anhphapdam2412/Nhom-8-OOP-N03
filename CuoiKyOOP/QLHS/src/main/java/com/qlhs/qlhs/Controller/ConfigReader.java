package com.qlhs.qlhs.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private final Properties properties = new Properties();

    public ConfigReader() {
        // Sử dụng ClassLoader để tải file properties từ classpath
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getEndOfSchoolYear() {
        return properties.getProperty("endOfSchoolYear");
    }
    public String getURL() {return properties.getProperty("db.url");}
    public String getUSER() {return properties.getProperty("db.user");}
    public String getPASSWORD() {return properties.getProperty("db.password");}
}

