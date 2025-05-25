package com.tub.ise.aggrigationservice.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AggregationService {


    public List<Map<String, Object>> aggregate(List<Map<String, Object>> data, Map<String, Object> config) {
        String groupBy = (String) config.get("group_by");
        String operation = (String) config.get("operation");
        String field = (String) config.get("field");

        return data.stream()
                .collect(Collectors.groupingBy(item -> item.get(groupBy)))
                .entrySet().stream()
                .map(entry -> {
                    Map<String, Object> result = Map.of(
                            groupBy, entry.getKey(),
                            getResultKey(operation, field), calculate(entry.getValue(), operation, field)
                    );
                    return result;
                })
                .collect(Collectors.toList());
    }

    private String getResultKey(String operation, String field) {
        return operation + "_" + field;
    }

    private Object calculate(List<Map<String, Object>> group, String operation, String field) {
        switch (operation.toLowerCase()) {
            case "sum":
                return group.stream()
                        .mapToDouble(item -> ((Number) item.get(field)).doubleValue())
                        .sum();
            case "average":
                return group.stream()
                        .mapToDouble(item -> ((Number) item.get(field)).doubleValue())
                        .average()
                        .orElse(0.0);
            case "count":
                return group.size();
            case "max":
                return group.stream()
                        .mapToDouble(item -> ((Number) item.get(field)).doubleValue())
                        .max()
                        .orElse(0.0);
            case "min":
                return group.stream()
                        .mapToDouble(item -> ((Number) item.get(field)).doubleValue())
                        .min()
                        .orElse(0.0);
            default:
                throw new IllegalArgumentException("Unsupported operation: " + operation);
        }
    }
}