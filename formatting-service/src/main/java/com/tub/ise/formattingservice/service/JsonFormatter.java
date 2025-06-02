package com.tub.ise.formattingservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("json")
public class JsonFormatter implements Formatter {

    private final ObjectMapper objectMapper;

    public JsonFormatter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Object format(List<Map<String, Object>> data, List<String> fields) {
        try {

            if(data== null ||data.isEmpty()) {
                return null;
            }
            if (fields == null || fields.isEmpty()) {
                // Return all fields if none specified
                return objectMapper.writeValueAsString(data);
            }

            // Filter and format with selected fields
            List<Map<String, Object>> filteredData = data.stream()
                    .map(item -> fields.stream()
                            .filter(item::containsKey)
                            .collect(Collectors.toMap(
                                    field -> field,
                                    item::get
                            )))
                    .collect(Collectors.toList());

            return objectMapper.writeValueAsString(filteredData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to format JSON", e);
        }
    }
}