package com.app.project.service.database.gateway;

import com.app.project.service.database.SchoolDatabaseService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SchoolOrganizationMappingGateway {
    private static SchoolOrganizationMappingGateway INSTANCE;
    private static final Logger LOGGER = Logger.getLogger(SchoolOrganizationMappingGateway.class.getName());

    private SchoolOrganizationMappingGateway() {
    }

    public static SchoolOrganizationMappingGateway getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SchoolOrganizationMappingGateway();
        }
        return INSTANCE;
    }

    public int upsert(int schoolId, int organizationId, String organizationCode) {
        String query = "INSERT INTO school_organization_mapping(school_id, organization_id, organization_code)" +
                " VALUES(?, ?, ?) on conflict (school_id) do update set organization_id = ?, " +
                "organization_code = ? returning id";

        try (Connection con = SchoolDatabaseService.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, schoolId);
            pst.setInt(2, organizationId);
            pst.setString(3, organizationCode);
            pst.setInt(4, organizationId);
            pst.setString(5, organizationCode);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            LOGGER.severe("Sql error happened: " + ex);
        }

        return 0;
    }

    public List<Map<String, Object>> getByOrganizationId(int organizationId) {
        String query = "select * from school_organization_mapping where organization_id::text = ?";
        return SchoolDatabaseService.getInstance().getDataAsListOfMaps(query, List.of(String.valueOf(organizationId)));
    }

    public Map<String, Object> getBySchoolId(int schoolId) {
        String query = "SELECT * from school_organization_mapping where school_id::text = ?";
        List<Map<String, Object>> data = SchoolDatabaseService.getInstance().getDataAsListOfMaps(query, List.of(String.valueOf(schoolId)));
        return data.stream().findFirst().orElse(Collections.emptyMap());
    }

}
