package com.app.project.service.database.mapper;

import com.app.project.model.Organization;
import com.app.project.service.database.gateway.OrganizationGateway;

import java.util.Map;
import java.util.Optional;

public class OrganizationMapper {

    private static OrganizationMapper INSTANCE;

    private OrganizationMapper() {
    }

    public static OrganizationMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrganizationMapper();
        }
        return INSTANCE;
    }

    public Organization insert(Organization organization) {
        int id = OrganizationGateway.getInstance().upsert(organization.getOrganizationType());
        organization.setId(id);
        return organization;
    }

    public Optional<Organization> getByType(String organizationType) {
        Map<String, Object> data = OrganizationGateway.getInstance().getByType(organizationType);
        if (data.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Organization((int) data.get("id"), (String) data.get("organization_type")));
    }

}
