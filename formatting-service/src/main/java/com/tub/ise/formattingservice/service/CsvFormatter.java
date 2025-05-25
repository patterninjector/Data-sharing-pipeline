package com.tub.ise.formattingservice.service;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

@Component("csv")
public class CsvFormatter implements Formatter {

    @Override
    public Object format(List<Map<String, Object>> data, List<String> fields) {
        if (data == null || data.isEmpty()) {
            return "";
        }

        if (fields == null || fields.isEmpty()) {
            fields = List.copyOf(data.get(0).keySet());
        }

        try (StringWriter writer = new StringWriter();
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            // Write header
            csvPrinter.printRecord(fields);

            // Write data
            for (Map<String, Object> item : data) {
                List<Object> record = fields.stream()
                        .map(field -> item.getOrDefault(field, ""))
                        .toList();
                csvPrinter.printRecord(record);
            }

            return writer.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate CSV", e);
        }
    }
}
