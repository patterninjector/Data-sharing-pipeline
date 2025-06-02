package com.tub.ise.dataproductservice;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
public class DataProductController {


    @GetMapping("/data-json")
    public ResponseEntity<String> getJsonFile() {
        try {
            ClassPathResource resource = new ClassPathResource("book-all-data.json");
            InputStream inputStream = resource.getInputStream();
            String json = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            return ResponseEntity.ok().body(json);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\": \"Could not read the file.\"}");
        }
    }
}
