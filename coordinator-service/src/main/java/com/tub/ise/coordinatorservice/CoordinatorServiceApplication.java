package com.tub.ise.coordinatorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.tub.ise.coordinatorservice.service")
public class CoordinatorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoordinatorServiceApplication.class, args);
    }

//    @Bean
//    CommandLineRunner run(PipelineRunner pipelineRunner) {
//        return args -> {
//            if (args.length > 0) {
//                pipelineRunner.runPipelinesFromDirectory(args[0]);
//            } else {
//                pipelineRunner.runPipelinesFromDirectory("classpath:pipelines");
//            }
//        };
//    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }



}
