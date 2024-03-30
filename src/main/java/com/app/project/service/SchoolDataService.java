package com.app.project.service;

import com.app.project.model.*;
import com.app.project.service.database.mapper.*;
import com.app.project.service.datafetcher.SchoolDataFetcherFactory;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SchoolDataService {
    //        Refactoring Pattern Used: Replace Magic String with Symbolic Constant
    public static final String SERVER_ENVIRONMENT_PROPERTY = "server.environment";
    private static SchoolDataService INSTANCE;
    private final String serverEnvironment;

    private SchoolDataService() {
        PropertiesService propertiesService = PropertiesService.getInstance();
        this.serverEnvironment = propertiesService.getProperty(SERVER_ENVIRONMENT_PROPERTY);
    }

    public static SchoolDataService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SchoolDataService();
        }
        return INSTANCE;
    }

    private static Map<String, School> insertSchoolData(List<SchoolAPIData> data) {
        //        Refactoring Pattern Used: Extract Method
        return data.stream()
                .collect(Collectors.toMap(SchoolAPIData::getName, Function.identity(), (a, b) -> a, LinkedHashMap::new))
                .values().stream()
                .map(apiData -> {
                    School school = new School(0, apiData.getName(), apiData.getPhone(), apiData.getStudentOpenDate());
                    return SchoolMapper.getInstance().insert(school);
                })
                .collect(Collectors.toMap(School::getName, Function.identity(), (a, b) -> a, LinkedHashMap::new));
    }

    private static void insertSchoolAddressData(List<SchoolAPIData> data, Map<String, School> schoolNameToSchoolMap) {
        //        Refactoring Pattern Used: Extract Method
        data.stream()
                .collect(Collectors.toMap(SchoolAPIData::getName, Function.identity(), (a, b) -> a, LinkedHashMap::new))
                .values().stream()
                .filter(apiData -> schoolNameToSchoolMap.containsKey(apiData.getName()))
                .forEach(apiData -> {
                    int schoolId = schoolNameToSchoolMap.get(apiData.getName()).getId();
                    SchoolAPIData.GeoCodedData geoCodedData = apiData.getGeoCodedData();
                    SchoolAddress.Coordinate coordinate = null;
                    if (geoCodedData != null) {
                        coordinate = new SchoolAddress.Coordinate(geoCodedData.getLatitude(), geoCodedData.getLongitude());
                    }
                    SchoolAddress schoolAddress = new SchoolAddress(0, schoolId, apiData.getDistrictName(),
                            apiData.getAddress(), apiData.getTown(), apiData.getZipcode(),
                            coordinate);
                    SchoolAddressMapper.getInstance().insert(schoolAddress);
                });
    }

    private static void insertSchoolDetailsData(List<SchoolAPIData> data, Map<String, School> schoolNameToSchoolMap) {
        //        Refactoring Pattern Used: Extract Method
        data.stream()
                .collect(Collectors.toMap(SchoolAPIData::getName, Function.identity(), (a, b) -> a, LinkedHashMap::new))
                .values().stream()
                .filter(apiData -> schoolNameToSchoolMap.containsKey(apiData.getName()))
                .forEach(apiData -> {
                    int schoolId = schoolNameToSchoolMap.get(apiData.getName()).getId();
                    SchoolDetails schoolDetails = new SchoolDetails(0, schoolId, apiData.hasPreKindergarten(),
                            apiData.hasKindergarten(), apiData.hasGrade1(), apiData.hasGrade2(),
                            apiData.hasGrade3(), apiData.hasGrade4(), apiData.hasGrade5(), apiData.hasGrade6(),
                            apiData.hasGrade7(), apiData.hasGrade8(), apiData.hasGrade9(), apiData.hasGrade10(),
                            apiData.hasGrade11(), apiData.hasGrade12());
                    SchoolDetailsMapper.getInstance().insert(schoolDetails);
                });
    }

    private static void insertSchoolOrganizationMappings(List<SchoolAPIData> data,
                                                         Map<String, Organization> organizationTypeToOrganizationMap,
                                                         Map<String, School> schoolNameToSchoolMap) {
        //        Refactoring Pattern Used: Extract Method
        data.stream()
                .collect(Collectors.toMap(SchoolAPIData::getName, Function.identity(), (a, b) -> a, LinkedHashMap::new))
                .values().stream()
                .filter(apiData -> schoolNameToSchoolMap.containsKey(apiData.getName()))
                .filter(apiData -> organizationTypeToOrganizationMap.containsKey(apiData.getOrganizationType()))
                .forEach(apiData -> {
                    int schoolId = schoolNameToSchoolMap.get(apiData.getName()).getId();
                    int organizationId = organizationTypeToOrganizationMap.get(apiData.getOrganizationType()).getId();
                    SchoolOrganizationMapping school = new SchoolOrganizationMapping(0, schoolId, organizationId,
                            apiData.getOrganizationCode());
                    SchoolOrganizationMapper.getInstance().insert(school);
                });
    }

    private Map<String, Organization> insertOrganizationData(List<SchoolAPIData> data) {
//        Refactoring Pattern Used: Extract Method
        return data.stream()
                .map(SchoolAPIData::getOrganizationType)
                .collect(Collectors.toSet()).stream()
                .map(orgType -> {
                    Organization organization = new Organization(0, orgType);
                    return OrganizationMapper.getInstance().insert(organization);
                })
                .collect(Collectors.toMap(Organization::getOrganizationType, Function.identity(), (a, b) -> a, LinkedHashMap::new));
    }

    public void syncData() {
        List<SchoolAPIData> data = SchoolDataFetcherFactory.getService(this.serverEnvironment).getSchoolData();
        Map<String, Organization> organizationTypeToOrganizationMap = insertOrganizationData(data);
        Map<String, School> schoolNameToSchoolMap = insertSchoolData(data);
        insertSchoolAddressData(data, schoolNameToSchoolMap);
        insertSchoolDetailsData(data, schoolNameToSchoolMap);
        insertSchoolOrganizationMappings(data, organizationTypeToOrganizationMap, schoolNameToSchoolMap);
    }

    public List<School> getAllSchools(String ordering) {
        return SchoolMapper.getInstance().getAll(ordering, "");
    }

    public List<School> schoolsByPhonePrefix(String ordering, String phonePrefix) {
        return SchoolMapper.getInstance().getAll(ordering, phonePrefix);
    }

    public List<School> schoolsByOrganizationType(String organizationType) {
        return OrganizationMapper.getInstance().getByType(organizationType)
                .map(a -> SchoolOrganizationMapper.getInstance().getByOrganizationId(a.getId()))
                .orElse(Collections.emptyList()).stream()
                .map(SchoolOrganizationMapping::getSchoolId)
                .map(schoolId -> SchoolMapper.getInstance().get(schoolId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public List<School> schoolsByYearOpened(String yearOpened) {
        return SchoolMapper.getInstance().getByYearOpened(yearOpened);
    }

    public List<School> schoolsByTown(String town) {
        return SchoolAddressMapper.getInstance().getByTown(town).stream()
                .map(SchoolAddress::getSchoolId)
                .map(schoolId -> SchoolMapper.getInstance().get(schoolId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public List<School> grade8SchoolsByOrganizationType(String organizationType) {
        return schoolsByOrganizationType(organizationType).stream()
                .filter(school -> SchoolDetailsMapper.getInstance().getBySchoolId(school.getId())
                        .filter(SchoolDetails::getHasGrade8).isPresent())
                .collect(Collectors.toList());
    }

    public SchoolOrganizationMapping getSchoolOrganizationMappingBySchoolName(String schoolName) {
        return SchoolMapper.getInstance().getByName(schoolName)
                .map(School::getId)
                .flatMap(schoolId -> SchoolOrganizationMapper.getInstance().getBySchoolId(schoolId))
                .orElse(null);
    }


}
