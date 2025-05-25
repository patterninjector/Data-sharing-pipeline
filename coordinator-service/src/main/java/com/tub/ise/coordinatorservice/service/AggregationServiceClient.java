package com.tub.ise.coordinatorservice.service;



import com.tub.ise.commondtos.ServiceRequest;
import com.tub.ise.commondtos.ServiceResponse;
import com.tub.ise.coordinatorservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "aggregation-service",
        url = "${service.aggregation.url}",
        configuration = FeignConfig.class
)
public interface AggregationServiceClient extends ServiceClient {

    @Override
    @PostMapping("/aggregate")
    ServiceResponse processNormal(ServiceRequest request);

    @Override
    @PostMapping("/flaky-aggregate")
    ServiceResponse processFlaky(ServiceRequest request);

    @Override
    @PostMapping("/slow-aggregate")
    ServiceResponse processSlow(ServiceRequest request);

    //for non-valid path test scenario
    @Override
    @PostMapping("/block-aggregate")
    ServiceResponse processBlock(ServiceRequest request);
}
