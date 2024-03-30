package com.app.project.model;

public class Organization {
    private int id;
    private String organizationType;

    public Organization() {
    }

    public Organization(int id, String organizationType) {
        this.id = id;
        this.organizationType = organizationType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }
}
