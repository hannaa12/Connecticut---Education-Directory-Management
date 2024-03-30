package com.app.project.service.datafetcher.schooldataserviceimpl;

import com.app.project.model.SchoolAPIData;
import com.app.project.service.api.impl.BasicAPIService;
import com.app.project.service.datafetcher.SchoolDataFetcher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.logging.Logger;

public class SchoolDataAPIFetcher implements SchoolDataFetcher {
    private static SchoolDataAPIFetcher INSTANCE;
    private static final Logger LOGGER = Logger.getLogger(SchoolDataAPIFetcher.class.getName());

    private SchoolDataAPIFetcher() {
    }

    public static SchoolDataAPIFetcher getInstance() {
        LOGGER.info("Initialized School Data API Fetcher.");
        if (INSTANCE == null) {
            INSTANCE = new SchoolDataAPIFetcher();
        }
        return INSTANCE;
    }

    @Override
    public List<SchoolAPIData> getSchoolData() {
        String responseData = BasicAPIService.getInstance().get("https://data.ct.gov/resource/9k2y-kqxn.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(responseData, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
