package com.app.project.service.datafetcher.schooldataserviceimpl;

import com.app.project.model.SchoolAPIData;
import com.app.project.service.datafetcher.SchoolDataFetcher;
import com.app.project.util.FileDataUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.logging.Logger;

public class SchoolDataMockFetcher implements SchoolDataFetcher {
    /**
     * Service that reads the API response from the File with fewer records to avoid making unnecessary API calls to avoid being blocked.
     */
    private static SchoolDataMockFetcher INSTANCE;
    private static final Logger LOGGER = Logger.getLogger(SchoolDataMockFetcher.class.getName());

    private SchoolDataMockFetcher() {
    }

    public static SchoolDataMockFetcher getInstance() {
        LOGGER.info("Initialized School Data Mock Fetcher.");
        if (INSTANCE == null) {
            INSTANCE = new SchoolDataMockFetcher();
        }
        return INSTANCE;
    }

    @Override
    public List<SchoolAPIData> getSchoolData() {
        String fileData = FileDataUtil.readFileData("schools.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(fileData, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
