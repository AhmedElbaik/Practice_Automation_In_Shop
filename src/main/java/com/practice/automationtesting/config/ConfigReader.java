package com.practice.automationtesting.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;

public class ConfigReader {
    private static TestSettings testSettings;

    public static TestSettings getTestSettings(String filePath) {
        if (testSettings == null) {
            testSettings = loadTestSettings(filePath);
        }
        return testSettings;
    }

    private static TestSettings loadTestSettings(String filePath) {
        ObjectMapper objectMapper = JsonMapper.builder()
                .findAndAddModules() // Automatically register modules, including JavaTimeModule if needed
                .propertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE) // Handle case insensitivity
                .build();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return objectMapper.readValue(new File(filePath), TestSettings.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load configuration file: " + filePath, e);
        }
    }
}
