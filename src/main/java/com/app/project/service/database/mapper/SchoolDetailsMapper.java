package com.app.project.service.database.mapper;

import com.app.project.model.SchoolDetails;
import com.app.project.service.database.gateway.SchoolDetailsGateway;

import java.util.Map;
import java.util.Optional;

public class SchoolDetailsMapper {

    private static SchoolDetailsMapper INSTANCE;

    private SchoolDetailsMapper() {
    }

    public static SchoolDetailsMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SchoolDetailsMapper();
        }
        return INSTANCE;
    }

    public SchoolDetails insert(SchoolDetails schoolDetails) {
        int id = SchoolDetailsGateway.getInstance().upsert(schoolDetails.getSchoolId(), schoolDetails.getHasPreKindergarten(), schoolDetails.getHasKindergarten(), schoolDetails.getHasGrade1(), schoolDetails.getHasGrade2(), schoolDetails.getHasGrade3(), schoolDetails.getHasGrade4(), schoolDetails.getHasGrade5(), schoolDetails.getHasGrade6(), schoolDetails.getHasGrade7(), schoolDetails.getHasGrade8(), schoolDetails.getHasGrade9(), schoolDetails.getHasGrade10(), schoolDetails.getHasGrade11(), schoolDetails.getHasGrade12());
        schoolDetails.setId(id);
        return schoolDetails;
    }

    public Optional<SchoolDetails> getBySchoolId(int schoolId) {
        Map<String, Object> data = SchoolDetailsGateway.getInstance().getBySchoolId(schoolId);
        if (data.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new SchoolDetails((int) data.get("id"), (int) data.get("school_id"),
                (Boolean) data.get("has_pre_kindergarten"), (Boolean) data.get("has_kindergarten"),
                (Boolean) data.get("has_grade_1"), (Boolean) data.get("has_grade_2"),
                (Boolean) data.get("has_grade_3"), (Boolean) data.get("has_grade_4"),
                (Boolean) data.get("has_grade_5"), (Boolean) data.get("has_grade_6"),
                (Boolean) data.get("has_grade_7"), (Boolean) data.get("has_grade_8"),
                (Boolean) data.get("has_grade_9"), (Boolean) data.get("has_grade_10"),
                (Boolean) data.get("has_grade_1"), (Boolean) data.get("has_grade_12")));
    }

}
