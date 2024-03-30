package com.app.project.service.database.gateway;

import com.app.project.service.database.SchoolDatabaseService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SchoolAddressGateway {
    private static SchoolAddressGateway INSTANCE;
    private static final Logger LOGGER = Logger.getLogger(SchoolAddressGateway.class.getName());

    private SchoolAddressGateway() {
    }

    public static SchoolAddressGateway getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SchoolAddressGateway();
        }
        return INSTANCE;
    }

    public int upsert(int schoolId, String districtName, String address, String town,
                      String zipcode, String latitude, String longitude) {
        String query = "INSERT INTO school_address(school_id, district_name, address, town, zipcode, latitude, " +
                "longitude) VALUES(?, ?, ?, ?, ?, ?, ?) on conflict (school_id) do update " +
                "set district_name = ?, address = ?, town = ?, zipcode = ?, latitude = ?," +
                " longitude = ? returning id";

        try (Connection con = SchoolDatabaseService.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, schoolId);
            pst.setString(2, districtName);
            pst.setString(3, address);
            pst.setString(4, town);
            pst.setString(5, zipcode);
            pst.setString(6, latitude);
            pst.setString(7, longitude);
            pst.setString(8, districtName);
            pst.setString(9, address);
            pst.setString(10, town);
            pst.setString(11, zipcode);
            pst.setString(12, latitude);
            pst.setString(13, longitude);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            LOGGER.severe("Sql error happened: " + ex);
        }

        return 0;
    }

    public List<Map<String, Object>> getByTown(String town) {
        String query = "select * from school_address where LOWER(town) = LOWER(?)";
        return SchoolDatabaseService.getInstance().getDataAsListOfMaps(query, List.of(town));
    }

}
