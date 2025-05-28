package com.tub.ise.coordinatorservice.service;


import com.tub.ise.commondtos.ServiceRequest;
import com.tub.ise.commondtos.ServiceResponse;
import com.tub.ise.coordinatorservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.retry.annotation.Backoff;
//import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "filter-service",
        url = "${service.filter.url}",
        configuration = FeignConfig.class
        //    fallback = FilterServiceFallback.class
)
//@CircuitBreaker(name = "filter-service" ,fallbackMethod = "")
public interface FilterServiceClient extends ServiceClient {

    @Override
    @PostMapping("/filter")
//    @Retryable(
//            maxAttempts = 3,
//            backoff = @Backoff(delay = 1000, multiplier = 2)
//    )
    ServiceResponse processNormal(@RequestBody ServiceRequest request);

    @Override
    @PostMapping("/flaky-filter")
    ServiceResponse processFlaky(@RequestBody ServiceRequest request);

    @Override
    @PostMapping("/slow-filter")
    ServiceResponse processSlow(@RequestBody ServiceRequest request);

    //for non-valid path test scenario
    @Override
    @PostMapping("/block-filter")
    ServiceResponse processBlock(@RequestBody ServiceRequest request);
}
