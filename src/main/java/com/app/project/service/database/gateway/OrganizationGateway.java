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

public class OrganizationGateway {
    private static OrganizationGateway INSTANCE;
    private static final Logger LOGGER = Logger.getLogger(OrganizationGateway.class.getName());

    private OrganizationGateway() {
    }

    public static OrganizationGateway getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrganizationGateway();
        }
        return INSTANCE;
    }

    public int upsert(String organizationType) {
        String query = "INSERT INTO organization(organization_type) VALUES(?) " +
                "on conflict (organization_type) do update set organization_type = ? returning id";

        try (Connection con = SchoolDatabaseService.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, organizationType);
            pst.setString(2, organizationType);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            LOGGER.severe("Sql error happened: " + ex);
        }

        return 0;
    }

    public Map<String, Object> getByType(String organizationType) {
        String query = "select * from organization where LOWER(organization_type) = LOWER(?)";
        List<Map<String, Object>> data = SchoolDatabaseService.getInstance().getDataAsListOfMaps(query, List.of(organizationType));
        return data.stream().findFirst().orElse(Collections.emptyMap());
    }

}
