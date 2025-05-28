package com.tub.ise.formattingservice.service;


import com.tub.ise.commondtos.ServiceRequest;
import com.tub.ise.commondtos.ServiceResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FormattingService {

    private final FormatterFactory formatterFactory;

    public FormattingService(FormatterFactory formatterFactory) {
        this.formatterFactory = formatterFactory;
    }

    @SuppressWarnings("unchecked")
    public ServiceResponse castAndFormat(ServiceRequest request) {
        // Safely cast the input data
        List<Map<String, Object>> inputData;
        try {
            inputData = (List<Map<String, Object>>) request.getData();
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Invalid input data format", e);
        }

        // Process the data
        Object processedData = format(
                inputData,
                request.getConfig()
        );
        String formatType = (String) request.getConfig().getOrDefault("output_format", "json");
        if(formatType.equalsIgnoreCase("csv")){
            return new ServiceResponse((String) processedData);
        }else {
            return new ServiceResponse((List<Map<String, Object>>) processedData);
        }

    }


        public Object format(List<Map<String, Object>> data, Map<String, Object> config) {
        String formatType = (String) config.getOrDefault("output_format", "json");
        List<String> fields = (List<String>) config.get("fields");

        return formatterFactory.getFormatter(formatType)
                .format(data, fields);
    }
}
