package com.app.project.service.database;

import com.app.project.service.PropertiesService;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class SchoolDatabaseService {
    private static SchoolDatabaseService INSTANCE;

    private final String databaseUrl;
    private final String databaseUsername;
    private final String databasePassword;

    private SchoolDatabaseService() {
        PropertiesService propertiesService = PropertiesService.getInstance();
        this.databaseUrl = propertiesService.getProperty("database.school.url");
        this.databaseUsername = propertiesService.getProperty("database.school.username");
        this.databasePassword = propertiesService.getProperty("database.school.password");
    }

    public static SchoolDatabaseService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SchoolDatabaseService();
        }
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
    }

    public List<Map<String, Object>> getDataAsListOfMaps(String query, List<String> parameters) {
        try (Connection con = this.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
            for (int i = 1; i <= parameters.size(); i++) {
                pst.setString(i, parameters.get(i - 1));
            }
            ResultSet resultSet = pst.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            List<Map<String, Object>> tableData = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> rowData = new LinkedHashMap<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    rowData.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
                tableData.add(rowData);
            }
            return tableData;
        } catch (SQLException ex) {
            throw new RuntimeException("Sql error happened: " + ex);
        }
    }

}
