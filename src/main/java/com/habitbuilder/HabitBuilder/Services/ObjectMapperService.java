package com.habitbuilder.HabitBuilder.Services;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
