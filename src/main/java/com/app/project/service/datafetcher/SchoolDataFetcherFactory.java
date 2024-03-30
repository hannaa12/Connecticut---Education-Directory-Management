package com.app.project.service.datafetcher;

import com.app.project.service.datafetcher.schooldataserviceimpl.SchoolDataAPIFetcher;
import com.app.project.service.datafetcher.schooldataserviceimpl.SchoolDataMockFetcher;

public class SchoolDataFetcherFactory {

    public static SchoolDataFetcher getService(String environment) {
        switch (environment) {
            case "PRODUCTION":
                return SchoolDataAPIFetcher.getInstance();
//            Refactoring Pattern Used: Consolidate Duplicate Conditional Fragments
            case "STAGING":
            case "LOCAL":
                return SchoolDataMockFetcher.getInstance();
        }
        throw new RuntimeException("Unhandled Environment: " + environment);
    }

}
