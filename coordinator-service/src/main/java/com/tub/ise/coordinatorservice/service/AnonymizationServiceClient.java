package com.tub.ise.coordinatorservice.service;



import com.tub.ise.commondtos.ServiceRequest;
import com.tub.ise.commondtos.ServiceResponse;
import com.tub.ise.coordinatorservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "anonymization-service",
        url = "${service.anonymization.url}",
        configuration = FeignConfig.class
)
public interface AnonymizationServiceClient extends ServiceClient {

    @Override
    @PostMapping("/anonymize")
    ServiceResponse processNormal(@RequestBody ServiceRequest request);

    @Override
    @PostMapping("/flaky-anonymize")
    ServiceResponse processFlaky(@RequestBody ServiceRequest request);

    @Override
    @PostMapping("/slow-anonymize")
    ServiceResponse processSlow(@RequestBody ServiceRequest request);

    //for non-valid path test scenario
    @Override
    @PostMapping("/block-anonymize")
    ServiceResponse processBlock(@RequestBody ServiceRequest request);
}
