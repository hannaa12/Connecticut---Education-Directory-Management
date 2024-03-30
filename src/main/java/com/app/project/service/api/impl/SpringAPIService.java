package com.app.project.service.api.impl;

import com.app.project.service.api.RestAPIService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SpringAPIService extends RestAPIService {

    private static SpringAPIService INSTANCE;

    private final RestTemplate restTemplate;
    private SpringAPIService() {
        super();
        this.restTemplate = new RestTemplate();
    }

    public static SpringAPIService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SpringAPIService();
        }
        return INSTANCE;
    }

    @Override
    public String get(String url) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("HTTP Response is not 200. Response Code: " + responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }

}
