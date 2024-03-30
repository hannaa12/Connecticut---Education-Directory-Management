package com.app.project.service.database.gateway.strategy;

public class SchoolDataAscendingNameQueryStrategy implements SchoolDataQueryStrategy {


    @Override
    public String getAll() {
        return "SELECT * FROM school order by name asc";
    }

    @Override
    public String getAll(String phonePrefix) {
        return "SELECT * FROM school where phone like ? order by name asc";
    }

}
