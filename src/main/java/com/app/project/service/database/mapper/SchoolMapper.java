package com.app.project.service.database.mapper;

import com.app.project.model.School;
import com.app.project.service.database.gateway.SchoolGateway;
import com.app.project.service.database.gateway.strategy.SchoolDataAscendingNameQueryStrategy;
import com.app.project.service.database.gateway.strategy.SchoolDataDefaultQueryStrategy;
import com.app.project.service.database.gateway.strategy.SchoolDataDescendingNameQueryStrategy;
import com.app.project.service.database.gateway.strategy.SchoolDataQueryStrategy;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SchoolMapper {

    private static SchoolMapper INSTANCE;

    private SchoolMapper() {
    }

    public static SchoolMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SchoolMapper();
        }
        return INSTANCE;
    }

    public School insert(School school) {
        int id = SchoolGateway.getInstance().upsert(
                school.getName(),
                school.getPhone(),
                school.getStudentOpenDate()
        );
        school.setId(id);
        return school;
    }

    public Optional<School> get(int schoolId) {
        Map<String, Object> data = SchoolGateway.getInstance().get(schoolId);
        if (data.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new School((int) data.get("id"), (String) data.get("name"),
                (String) data.get("phone"), (String) data.get("student_open_date")));
    }

    public Optional<School> getByName(String schoolName) {
        Map<String, Object> data = SchoolGateway.getInstance().getByName(schoolName);
        if (data.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new School((int) data.get("id"), (String) data.get("name"),
                (String) data.get("phone"), (String) data.get("student_open_date")));
    }

    public List<School> getAll(String ordering, String phonePrefix) {
        SchoolDataQueryStrategy strategy;
        switch (ordering) {
            case "ASC":
                strategy = new SchoolDataAscendingNameQueryStrategy();
                break;
            case "DESC":
                strategy = new SchoolDataDescendingNameQueryStrategy();
                break;
            default:
                strategy = new SchoolDataDefaultQueryStrategy();
        }
        List<Map<String, Object>> schoolData;
//        Refactoring Pattern Used: Parameterize Method
        if (!"".equals(phonePrefix)) {
            schoolData = SchoolGateway.getInstance().getAll(strategy, phonePrefix);
        } else {
            schoolData = SchoolGateway.getInstance().getAll(strategy);
        }
        return schoolData.stream()
                .map(data -> new School((int) data.get("id"), (String) data.get("name"),
                        (String) data.get("phone"), (String) data.get("student_open_date")))
                .collect(Collectors.toList());
    }

    public List<School> getByYearOpened(String yearOpened) {
        return SchoolGateway.getInstance().getByYearOpened(yearOpened).stream()
                .map(data -> new School((int) data.get("id"), (String) data.get("name"),
                        (String) data.get("phone"), (String) data.get("student_open_date")))
                .collect(Collectors.toList());
    }

}
