package com.app.project.service.database.gateway;

import com.app.project.service.database.SchoolDatabaseService;
import com.app.project.service.database.gateway.strategy.SchoolDataQueryStrategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SchoolGateway {
    private static SchoolGateway INSTANCE;
    private static final Logger LOGGER = Logger.getLogger(SchoolGateway.class.getName());

    private SchoolGateway() {
    }

    public static SchoolGateway getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SchoolGateway();
        }
        return INSTANCE;
    }

    public int upsert(String name, String phone, String studentOpenDate) {
        String query = "INSERT INTO school(name, phone, student_open_date) VALUES(?, ?, ?) " +
                "on conflict (name) do update set phone = ?, student_open_date = ? returning id";

        try (Connection con = SchoolDatabaseService.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, name);
            pst.setString(2, phone);
            pst.setString(3, studentOpenDate);
            pst.setString(4, phone);
            pst.setString(5, studentOpenDate);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            LOGGER.severe("Sql error happened: " + ex);
        }

        return 0;
    }

    public Map<String, Object> get(int schoolId) {
        String query = "SELECT * from school where id::text = ?";
        List<Map<String, Object>> data = SchoolDatabaseService.getInstance().getDataAsListOfMaps(query, List.of(String.valueOf(schoolId)));
        return data.stream().findFirst().orElse(Collections.emptyMap());
    }

    public Map<String, Object> getByName(String schoolName) {
        String query = "SELECT * from school where name = ?";
        List<Map<String, Object>> data = SchoolDatabaseService.getInstance().getDataAsListOfMaps(query, List.of(String.valueOf(schoolName)));
        return data.stream().findFirst().orElse(Collections.emptyMap());
    }

    public List<Map<String, Object>> getAll(SchoolDataQueryStrategy strategy) {
        String query = strategy.getAll();
        return SchoolDatabaseService.getInstance().getDataAsListOfMaps(query, Collections.emptyList());
    }

    public List<Map<String, Object>> getAll(SchoolDataQueryStrategy strategy, String phonePrefix) {
        String query = strategy.getAll(phonePrefix);
        return SchoolDatabaseService.getInstance().getDataAsListOfMaps(query, List.of(phonePrefix + '%'));
    }

    public List<Map<String, Object>> getByYearOpened(String yearOpened) {
        String query = "SELECT * from school where student_open_date ilike ?";
        return SchoolDatabaseService.getInstance().getDataAsListOfMaps(query, List.of(yearOpened + '%'));
    }

}
