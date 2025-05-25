package com.tub.ise.coordinatorservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceClientFactory {

    @Autowired
    private FilterServiceClient filterServiceClient;

    @Autowired
    private AggregationServiceClient aggregationServiceClient;

    @Autowired
    private AnonymizationServiceClient anonymizationServiceClient;

    @Autowired
    private FormattingServiceClient formattingServiceClient;

    public ServiceClient getClient(String serviceName) {
        return switch (serviceName.toLowerCase()) {
            case "filter" -> filterServiceClient;
            case "aggregation" -> aggregationServiceClient;
            case "anonymization" -> anonymizationServiceClient;
            case "formatting" -> formattingServiceClient;
            default -> throw new IllegalArgumentException("Unknown service: " + serviceName);
        };
    }
}
