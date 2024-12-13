package com.java.project.FinFlow.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.java.project.FinFlow.models.Records;
import com.java.project.FinFlow.models.Categories;

import java.util.List;

public class mappers {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());  // Configure this only once
    }

    public static List<Records> parseRecordList(String jsonData) throws Exception {
        return mapper.readValue(jsonData, new TypeReference<List<Records>>() {});
    }

    public static List<Categories> parseCategories(String json) throws Exception {
        return mapper.readValue(json, new TypeReference<List<Categories>>() {});
    }
}
