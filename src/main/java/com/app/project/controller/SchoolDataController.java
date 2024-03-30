package com.app.project.controller;

import com.app.project.model.School;
import com.app.project.model.SchoolOrganizationMapping;
import com.app.project.service.SchoolDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/school")
public class SchoolDataController {

    @GetMapping("sync-data")
    public String insert() {
        SchoolDataService.getInstance().syncData();
        return "Data synced successfully";
    }

    @GetMapping("all")
    public List<School> getAllSchools(@RequestParam(defaultValue = "DEFAULT") String ordering) {
        return SchoolDataService.getInstance().getAllSchools(ordering);
    }

    @GetMapping("search-by-phone")
    public List<School> schoolsByPhonePrefix(@RequestParam String phonePrefix,
                                             @RequestParam(defaultValue = "DEFAULT") String ordering) {
        return SchoolDataService.getInstance().schoolsByPhonePrefix(ordering, phonePrefix);
    }

    @GetMapping("search-by-organization-type")
    public List<School> schoolsByOrganizationType(@RequestParam String organizationType) {
        return SchoolDataService.getInstance().schoolsByOrganizationType(organizationType);
    }

    @GetMapping("search-by-year-opened")
    public List<School> schoolsByYearOpened(@RequestParam String yearOpened) {
        return SchoolDataService.getInstance().schoolsByYearOpened(yearOpened);
    }

    @GetMapping("search-by-town")
    public List<School> schoolsByTown(@RequestParam String town) {
        return SchoolDataService.getInstance().schoolsByTown(town);
    }

    @GetMapping("search-grade-8-by-organization-type")
    public List<School> grade8SchoolsByOrganizationType(@RequestParam String organizationType) {
        return SchoolDataService.getInstance().grade8SchoolsByOrganizationType(organizationType);
    }

    @GetMapping("school-organization-mapping")
    public SchoolOrganizationMapping getSchoolOrganizationMappingBySchoolName(@RequestParam String schoolName) {
        return SchoolDataService.getInstance().getSchoolOrganizationMappingBySchoolName(schoolName);
    }

}
