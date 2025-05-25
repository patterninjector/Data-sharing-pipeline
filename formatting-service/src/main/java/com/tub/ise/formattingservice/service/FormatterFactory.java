package com.tub.ise.formattingservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FormatterFactory {

    private final Map<String, Formatter> formatters;

    @Autowired
    public FormatterFactory(Map<String, Formatter> formatters) {
        this.formatters = formatters;
    }

    public Formatter getFormatter(String formatType) {
        return formatters.getOrDefault(formatType.toLowerCase(),
                (data, fields) -> data // Default: return original data
        );
    }
}
