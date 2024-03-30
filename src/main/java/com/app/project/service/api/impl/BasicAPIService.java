package com.app.project.service.api.impl;

import com.app.project.service.api.RestAPIService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class BasicAPIService extends RestAPIService {
    //        Refactoring Pattern Used: Replace Magic String with Symbolic Constant
    public static final int HTTP_RESPONSE_CODE_OK = 200;
    private static BasicAPIService INSTANCE;
    private static final Logger LOGGER = Logger.getLogger(BasicAPIService.class.getName());

    private BasicAPIService() {
        super();
    }

    public static BasicAPIService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BasicAPIService();
        }
        return INSTANCE;
    }

    @Override
    public String get(String url) {
        StringBuilder output = new StringBuilder();
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != HTTP_RESPONSE_CODE_OK) {
                throw new RuntimeException("HTTP Response is not 200. Response Code: " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String response;
            while ((response = br.readLine()) != null) {
                output.append(response);
            }

            conn.disconnect();
        } catch (IOException e) {
            LOGGER.warning("Some error happened while calling API");
            e.printStackTrace();
        }
        return output.toString();
    }
}
