package com.tub.ise.anonymizationservice.service.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StrategyFactory {
    private final Map<String, AnonymizationStrategy> strategies;

    @Autowired
    public StrategyFactory(Map<String, AnonymizationStrategy> strategies) {
        this.strategies = strategies;
    }

    public AnonymizationStrategy getStrategy(String method) {
        return strategies.getOrDefault(method.toLowerCase(),
                value -> value.toString() // Default: return original
        );
    }
}
