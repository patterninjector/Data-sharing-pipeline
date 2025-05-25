package com.tub.ise.anonymizationservice.service;


import com.tub.ise.anonymizationservice.service.strategies.StrategyFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AnonymizationService {

    private final StrategyFactory strategyFactory;

    public AnonymizationService(StrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public List<Map<String, Object>> anonymize(List<Map<String, Object>> data, Map<String, Object> config) {
        List<String> fields = (List<String>) config.get("fields");
        String method = (String) config.get("method");

        return data.stream()
                .map(item -> anonymizeItem(item, fields, method))
                .toList();
    }

    private Map<String, Object> anonymizeItem(Map<String, Object> item, List<String> fields, String method) {
        Map<String, Object> anonymizedItem = new java.util.HashMap<>(item);

        fields.forEach(field -> {
            if (anonymizedItem.containsKey(field)) {
                anonymizedItem.put(field,
                        strategyFactory.getStrategy(method)
                                .anonymize(anonymizedItem.get(field).toString())
                );
            }
        });

        return anonymizedItem;
    }
}
