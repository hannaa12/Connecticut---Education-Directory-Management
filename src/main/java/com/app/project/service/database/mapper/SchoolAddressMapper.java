package com.app.project.service.database.mapper;

import com.app.project.model.SchoolAddress;
import com.app.project.service.database.gateway.SchoolAddressGateway;

import java.util.List;
import java.util.stream.Collectors;

public class SchoolAddressMapper {

    private static SchoolAddressMapper INSTANCE;

    private SchoolAddressMapper() {
    }

    public static SchoolAddressMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SchoolAddressMapper();
        }
        return INSTANCE;
    }

    public SchoolAddress insert(SchoolAddress schoolAddress) {
        SchoolAddress.Coordinate coordinate = schoolAddress.getCoordinate();
        int id = SchoolAddressGateway.getInstance().upsert(
                schoolAddress.getSchoolId(),
                schoolAddress.getDistrictName(), schoolAddress.getAddress(), schoolAddress.getTown(), schoolAddress.getZipcode(), coordinate != null ? coordinate.getLatitude() : "", coordinate != null ? coordinate.getLongitude() : "");
        schoolAddress.setId(id);
        return schoolAddress;
    }

    public List<SchoolAddress> getByTown(String town) {
        return SchoolAddressGateway.getInstance().getByTown(town).stream().map(data -> new SchoolAddress((int) data.get("id"), (int) data.get("school_id"), (String) data.get("district_name"), (String) data.get("address"), (String) data.get("town"), (String) data.get("zipcode"), new SchoolAddress.Coordinate((String) data.get("latitude"), (String) data.get("longitude")))).collect(Collectors.toList());
    }

}
