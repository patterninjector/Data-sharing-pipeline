package com.tub.ise.coordinatorservice.service;


import com.tub.ise.commondtos.ServiceRequest;
import com.tub.ise.commondtos.ServiceResponse;
import com.tub.ise.coordinatorservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "formatting-service",
        url = "${service.formatting.url}",
        configuration = FeignConfig.class
)
public interface FormattingServiceClient extends ServiceClient {

    @Override
    @PostMapping("/format")
    ServiceResponse processNormal(ServiceRequest request);


    @Override
    @PostMapping("/flaky-format")
    ServiceResponse processFlaky(ServiceRequest request);

    @Override
    @PostMapping("/slow-format")
    ServiceResponse processSlow(ServiceRequest request);

    //for non-valid path test scenario
    @Override
    @PostMapping("/block-format")
    ServiceResponse processBlock(ServiceRequest request);
}
