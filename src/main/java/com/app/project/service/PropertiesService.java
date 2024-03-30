package com.app.project.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesService {
    //        Refactoring Pattern Used: Extract Class
    //        Refactoring Pattern Used: Replace Magic String with Symbolic Constant
    public static String propertiesFilePath = "src/main/resources/application.properties";
    private static PropertiesService INSTANCE;
    private final Properties properties;

    private PropertiesService() {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream(propertiesFilePath)) {
            prop.load(fis);
        } catch (IOException ex) {
            throw new RuntimeException("Properties file doesn't exist, please create properties file src/main/resources/application.properties");
        }
        this.properties = prop;
    }


    public static PropertiesService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PropertiesService();
        }
        return INSTANCE;
    }

    public String getProperty(String property) {
        return this.properties.getProperty(property);
    }
}
