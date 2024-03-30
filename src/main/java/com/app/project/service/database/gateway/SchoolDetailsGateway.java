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

public class SchoolDetailsGateway {
    private static SchoolDetailsGateway INSTANCE;
    private static final Logger LOGGER = Logger.getLogger(SchoolDetailsGateway.class.getName());

    private SchoolDetailsGateway() {
    }

    public static SchoolDetailsGateway getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SchoolDetailsGateway();
        }
        return INSTANCE;
    }

    public int upsert(int schoolId, boolean hasPreKindergarten, boolean hasKindergarten, boolean hasGrade1,
                      boolean hasGrade2, boolean hasGrade3, boolean hasGrade4, boolean hasGrade5, boolean hasGrade6,
                      boolean hasGrade7, boolean hasGrade8, boolean hasGrade9, boolean hasGrade10, boolean
                              hasGrade11, boolean hasGrade12) {
        String query = "INSERT INTO school_details(school_id, has_pre_kindergarten, has_kindergarten, has_grade_1, " +
                "has_grade_2, has_grade_3, has_grade_4, has_grade_5, has_grade_6, has_grade_7, has_grade_8, " +
                "has_grade_9, has_grade_10, has_grade_11, has_grade_12) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) on conflict (school_id) do update " +
                "set has_pre_kindergarten = ?, has_kindergarten = ?, has_grade_1 = ?, has_grade_2 = ?, " +
                "has_grade_3 = ?, has_grade_4 = ?, has_grade_5 = ?, has_grade_6 = ?, has_grade_7 = ?, " +
                "has_grade_8 = ?, has_grade_9 = ?, has_grade_10 = ?, has_grade_11 = ?, has_grade_12 = ? returning id";

        try (Connection con = SchoolDatabaseService.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, schoolId);
            pst.setBoolean(2, hasPreKindergarten);
            pst.setBoolean(3, hasKindergarten);
            pst.setBoolean(4, hasGrade1);
            pst.setBoolean(5, hasGrade2);
            pst.setBoolean(6, hasGrade3);
            pst.setBoolean(7, hasGrade4);
            pst.setBoolean(8, hasGrade5);
            pst.setBoolean(9, hasGrade6);
            pst.setBoolean(10, hasGrade7);
            pst.setBoolean(11, hasGrade8);
            pst.setBoolean(12, hasGrade9);
            pst.setBoolean(13, hasGrade10);
            pst.setBoolean(14, hasGrade11);
            pst.setBoolean(15, hasGrade12);
            pst.setBoolean(16, hasPreKindergarten);
            pst.setBoolean(17, hasKindergarten);
            pst.setBoolean(18, hasGrade1);
            pst.setBoolean(19, hasGrade2);
            pst.setBoolean(20, hasGrade3);
            pst.setBoolean(21, hasGrade4);
            pst.setBoolean(22, hasGrade5);
            pst.setBoolean(23, hasGrade6);
            pst.setBoolean(24, hasGrade7);
            pst.setBoolean(25, hasGrade8);
            pst.setBoolean(26, hasGrade9);
            pst.setBoolean(27, hasGrade10);
            pst.setBoolean(28, hasGrade11);
            pst.setBoolean(29, hasGrade12);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            LOGGER.severe("Sql error happened: " + ex);
        }

        return 0;
    }

    public Map<String, Object> getBySchoolId(int schoolId) {
        String query = "SELECT * from school_details where school_id::text = ?";
        List<Map<String, Object>> data = SchoolDatabaseService.getInstance().getDataAsListOfMaps(query, List.of(String.valueOf(schoolId)));
        return data.stream().findFirst().orElse(Collections.emptyMap());
    }

}
