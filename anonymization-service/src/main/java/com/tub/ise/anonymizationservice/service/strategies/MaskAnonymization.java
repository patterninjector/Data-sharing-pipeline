package com.tub.ise.anonymizationservice.service.strategies;


import org.springframework.stereotype.Component;

@Component("mask")
public class MaskAnonymization implements AnonymizationStrategy {

    @Override
    public String anonymize(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        return "****" + value.substring(Math.max(0, value.length() - 2));
    }
}
