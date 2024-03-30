package com.app.project.model;

public class SchoolOrganizationMapping {
    private int id;
    private int schoolId;
    private int organizationId;
    private String organizationCode;

    public SchoolOrganizationMapping() {
    }

    public SchoolOrganizationMapping(int id, int schoolId, int organizationId, String organizationCode) {
        this.id = id;
        this.schoolId = schoolId;
        this.organizationId = organizationId;
        this.organizationCode = organizationCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }
}
