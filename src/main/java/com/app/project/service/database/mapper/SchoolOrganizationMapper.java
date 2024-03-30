package com.app.project.service.database.mapper;

import com.app.project.model.SchoolOrganizationMapping;
import com.app.project.service.database.gateway.SchoolOrganizationMappingGateway;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SchoolOrganizationMapper {

    private static SchoolOrganizationMapper INSTANCE;

    private SchoolOrganizationMapper() {
    }

    public static SchoolOrganizationMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SchoolOrganizationMapper();
        }
        return INSTANCE;
    }

    public SchoolOrganizationMapping insert(SchoolOrganizationMapping schoolOrganizationMapping) {
        int id = SchoolOrganizationMappingGateway.getInstance().upsert(schoolOrganizationMapping.getSchoolId(), schoolOrganizationMapping.getOrganizationId(), schoolOrganizationMapping.getOrganizationCode());
        schoolOrganizationMapping.setId(id);
        return schoolOrganizationMapping;
    }

    public List<SchoolOrganizationMapping> getByOrganizationId(int organizationId) {
        List<Map<String, Object>> data = SchoolOrganizationMappingGateway.getInstance().getByOrganizationId(organizationId);
        return data.stream()
                .map(a -> new SchoolOrganizationMapping((int) a.get("id"),
                        (int) a.get("school_id"),
                        (int) a.get("organization_id"),
                        (String) a.get("organization_code")))
                .collect(Collectors.toList());
    }

    public Optional<SchoolOrganizationMapping> getBySchoolId(int schoolId) {
        Map<String, Object> data = SchoolOrganizationMappingGateway.getInstance().getBySchoolId(schoolId);
        if (data.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new SchoolOrganizationMapping((int) data.get("id"), (int) data.get("school_id"),
                (int) data.get("organization_id"), (String) data.get("organization_code")));
    }

}