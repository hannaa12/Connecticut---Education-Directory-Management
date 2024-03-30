package com.app.project.service.database.gateway.strategy;

public class SchoolDataDefaultQueryStrategy implements SchoolDataQueryStrategy {


    @Override
    public String getAll() {
        return "SELECT * FROM school";
    }

    @Override
    public String getAll(String phonePrefix) {
        return "SELECT * FROM school where phone like ?";
    }
}
