package com.tub.ise.formattingservice.service;


import java.util.List;
import java.util.Map;

public interface Formatter {
    Object format(List<Map<String, Object>> data, List<String> fields);
}
