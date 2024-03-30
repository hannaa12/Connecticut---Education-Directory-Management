package com.app.project.service;

import com.app.project.model.School;
import com.app.project.model.SchoolAPIData;
import com.app.project.model.SchoolOrganizationMapping;
import com.app.project.service.database.SchoolDatabaseService;
import com.app.project.util.FileDataUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SchoolDataServiceTest {
    private static final Logger LOGGER = Logger.getLogger(SchoolDataServiceTest.class.getName());

    private SchoolDataService schoolDataService;
    private List<SchoolAPIData> baseTestData;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        PropertiesService.propertiesFilePath = "src/test/resources/application.properties";
        String databaseInitializationSql = FileDataUtil.readFileData("database.sql");
        try (Connection con = SchoolDatabaseService.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(databaseInitializationSql)) {
            pst.execute();
        } catch (SQLException ex) {
            LOGGER.severe("Sql error happened: " + ex);
        }
        this.schoolDataService = SchoolDataService.getInstance();

        String testData = FileDataUtil.readFileData("schools.json");
        this.baseTestData = new ObjectMapper().readValue(testData, new TypeReference<>() {
        });

        this.schoolDataService.syncData();
    }

    @AfterEach
    void tearDown() {
        String databaseCleanupSql = FileDataUtil.readFileData("database_cleanup.sql");
        try (Connection con = SchoolDatabaseService.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(databaseCleanupSql)) {
            pst.execute();
        } catch (SQLException ex) {
            LOGGER.severe("Sql error happened: " + ex);
        }
    }

    @Test
    void getInstance() {
        assertNotNull(this.schoolDataService);
    }

    @Test
    void syncData() {
        List<School> schoolsFromDatabase = this.schoolDataService.getAllSchools("DEFAULT");
        assertEquals(baseTestData.size(), schoolsFromDatabase.size(), "Data not synced as expected");
    }

    @Test
    void getAllSchools() {
        List<School> schoolsFromDatabase = this.schoolDataService.getAllSchools("DEFAULT");
        assertEquals(baseTestData.size(), schoolsFromDatabase.size(), "Data not synced as expected");
        for (int i = 0; i < baseTestData.size(); i++) {
            School schoolFromDatabase = schoolsFromDatabase.get(i);
            SchoolAPIData schoolAPIData = baseTestData.get(i);
            assertEquals(i + 1, schoolFromDatabase.getId(), "Expected values do not match");
            assertEquals(schoolAPIData.getName(), schoolFromDatabase.getName(), "Expected values do not match");
            assertEquals(schoolAPIData.getPhone(), schoolFromDatabase.getPhone(), "Expected values do not match");
            assertEquals(schoolAPIData.getStudentOpenDate(), schoolFromDatabase.getStudentOpenDate(), "Expected values do not match");
        }
    }

    @Test
    void getAllSchoolsAscendingStrategy() {
        List<School> schoolsFromDatabase = this.schoolDataService.getAllSchools("ASC");
        assertEquals(baseTestData.size(), schoolsFromDatabase.size(), "Data not synced as expected");
        List<SchoolAPIData> sortedData = baseTestData.stream()
                .sorted(Comparator.comparing(SchoolAPIData::getName))
                .collect(Collectors.toList());
        for (int i = 0; i < baseTestData.size(); i++) {
            School schoolFromDatabase = schoolsFromDatabase.get(i);
            SchoolAPIData schoolAPIData = sortedData.get(i);
            assertEquals(schoolAPIData.getName(), schoolFromDatabase.getName(), "Expected values do not match");
            assertEquals(schoolAPIData.getPhone(), schoolFromDatabase.getPhone(), "Expected values do not match");
            assertEquals(schoolAPIData.getStudentOpenDate(), schoolFromDatabase.getStudentOpenDate(), "Expected values do not match");
        }
    }

    @Test
    void getAllSchoolsDescendingStrategy() {
        List<School> schoolsFromDatabase = this.schoolDataService.getAllSchools("DESC");
        assertEquals(baseTestData.size(), schoolsFromDatabase.size(), "Data not synced as expected");
        List<SchoolAPIData> sortedData = baseTestData.stream()
                .sorted(Comparator.comparing(SchoolAPIData::getName).reversed())
                .collect(Collectors.toList());
        for (int i = 0; i < baseTestData.size(); i++) {
            School schoolFromDatabase = schoolsFromDatabase.get(i);
            SchoolAPIData schoolAPIData = sortedData.get(i);
            assertEquals(schoolAPIData.getName(), schoolFromDatabase.getName(), "Expected values do not match");
            assertEquals(schoolAPIData.getPhone(), schoolFromDatabase.getPhone(), "Expected values do not match");
            assertEquals(schoolAPIData.getStudentOpenDate(), schoolFromDatabase.getStudentOpenDate(), "Expected values do not match");
        }
    }

    @Test
    void schoolsByPhonePrefix() {
        List<School> schoolsFromDatabase = this.schoolDataService.schoolsByPhonePrefix("DEFAULT", "860");
        Set<String> expectedSchoolNames = Set.of("Canton School District", "Governor William Pitkin School", "John Lyman School", "Northeast School");
        List<SchoolAPIData> filteredData = baseTestData.stream()
                .filter(school -> expectedSchoolNames.contains(school.getName()))
                .collect(Collectors.toList());
        for (int i = 0; i < filteredData.size(); i++) {
            School schoolFromDatabase = schoolsFromDatabase.get(i);
            SchoolAPIData schoolAPIData = filteredData.get(i);
            assertEquals(schoolAPIData.getName(), schoolFromDatabase.getName(), "Expected values do not match");
            assertEquals(schoolAPIData.getPhone(), schoolFromDatabase.getPhone(), "Expected values do not match");
            assertEquals(schoolAPIData.getStudentOpenDate(), schoolFromDatabase.getStudentOpenDate(), "Expected values do not match");
        }
    }

    @Test
    void schoolsByOrganizationType() {
        List<School> schoolsFromDatabase = this.schoolDataService.schoolsByOrganizationType("Regional Schools");
        List<String> schoolNames = schoolsFromDatabase.stream().map(School::getName).collect(Collectors.toList());
        List<String> expectedSchoolNames = List.of("Amity Middle School: Bethany", "John Lyman School", "Mitchell Elementary School");
        assertEquals(expectedSchoolNames, schoolNames, "schoolsByOrganizationType invalid output");
    }

    @Test
    void schoolsByYearOpened() {
        List<School> schoolsFromDatabase = this.schoolDataService.schoolsByYearOpened("1984");
        List<String> expectedSchoolNames = List.of("Park City Magnet School", "David Wooster Middle School",
                "Jettie S. Tisdale School", "Stamford High School", "Brookside Elementary School",
                "Wolcott High School", "Governor William Pitkin School", "Middlebrook School",
                "New Lebanon School", "Northeast School");
        List<SchoolAPIData> filteredData = baseTestData.stream()
                .filter(school -> expectedSchoolNames.contains(school.getName()))
                .collect(Collectors.toList());
        for (int i = 0; i < filteredData.size(); i++) {
            School schoolFromDatabase = schoolsFromDatabase.get(i);
            SchoolAPIData schoolAPIData = filteredData.get(i);
            assertEquals(schoolAPIData.getName(), schoolFromDatabase.getName(), "Expected values do not match");
            assertEquals(schoolAPIData.getPhone(), schoolFromDatabase.getPhone(), "Expected values do not match");
            assertEquals(schoolAPIData.getStudentOpenDate(), schoolFromDatabase.getStudentOpenDate(), "Expected values do not match");
        }
    }

    @Test
    void schoolsByTown() {
        List<School> schoolsFromDatabase = this.schoolDataService.schoolsByTown("Bridgeport");
        List<String> expectedSchoolNames = List.of("Park City Magnet School", "Jettie S. Tisdale School");
        List<SchoolAPIData> filteredData = baseTestData.stream()
                .filter(school -> expectedSchoolNames.contains(school.getName()))
                .collect(Collectors.toList());
        for (int i = 0; i < filteredData.size(); i++) {
            School schoolFromDatabase = schoolsFromDatabase.get(i);
            SchoolAPIData schoolAPIData = filteredData.get(i);
            assertEquals(schoolAPIData.getName(), schoolFromDatabase.getName(), "Expected values do not match");
            assertEquals(schoolAPIData.getPhone(), schoolFromDatabase.getPhone(), "Expected values do not match");
            assertEquals(schoolAPIData.getStudentOpenDate(), schoolFromDatabase.getStudentOpenDate(), "Expected values do not match");
        }
    }


    @Test
    void schoolsByTownCaseInsensitive() {
        List<School> schoolsFromDatabase = this.schoolDataService.schoolsByTown("bridgeport");
        List<String> expectedSchoolNames = List.of("Park City Magnet School", "Jettie S. Tisdale School");
        List<SchoolAPIData> filteredData = baseTestData.stream()
                .filter(school -> expectedSchoolNames.contains(school.getName()))
                .collect(Collectors.toList());
        for (int i = 0; i < filteredData.size(); i++) {
            School schoolFromDatabase = schoolsFromDatabase.get(i);
            SchoolAPIData schoolAPIData = filteredData.get(i);
            assertEquals(schoolAPIData.getName(), schoolFromDatabase.getName(), "Expected values do not match");
            assertEquals(schoolAPIData.getPhone(), schoolFromDatabase.getPhone(), "Expected values do not match");
            assertEquals(schoolAPIData.getStudentOpenDate(), schoolFromDatabase.getStudentOpenDate(), "Expected values do not match");
        }
    }

    @Test
    void grade8SchoolsByOrganizationType() {
        List<School> schoolsFromDatabase = this.schoolDataService.grade8SchoolsByOrganizationType("Public Schools");
        List<String> expectedSchoolNames = List.of("Park City Magnet School", "David Wooster Middle School",
                "Jettie S. Tisdale School", "Roger Ludlowe Middle School", "Middlebrook School",
                "Ansonia Middle School");
        List<SchoolAPIData> filteredData = baseTestData.stream()
                .filter(school -> expectedSchoolNames.contains(school.getName()))
                .collect(Collectors.toList());
        for (int i = 0; i < filteredData.size(); i++) {
            School schoolFromDatabase = schoolsFromDatabase.get(i);
            SchoolAPIData schoolAPIData = filteredData.get(i);
            assertEquals(schoolAPIData.getName(), schoolFromDatabase.getName(), "Expected values do not match");
            assertEquals(schoolAPIData.getPhone(), schoolFromDatabase.getPhone(), "Expected values do not match");
            assertEquals(schoolAPIData.getStudentOpenDate(), schoolFromDatabase.getStudentOpenDate(), "Expected values do not match");
        }
    }

    @Test
    void getSchoolOrganizationMappingBySchoolName() {
        SchoolOrganizationMapping schoolFromDatabase = this.schoolDataService.getSchoolOrganizationMappingBySchoolName("New Lebanon School");
        assertEquals(14, schoolFromDatabase.getId(), "Expected values do not match");
        assertEquals(14, schoolFromDatabase.getSchoolId(), "Expected values do not match");
        assertEquals(1, schoolFromDatabase.getOrganizationId(), "Expected values do not match");
        assertEquals("0570611", schoolFromDatabase.getOrganizationCode(), "Expected values do not match");
    }
}