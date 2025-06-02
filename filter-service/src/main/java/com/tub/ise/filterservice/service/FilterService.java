package com.tub.ise.filterservice.service;

import com.tub.ise.commondtos.FilterRequest;
import com.tub.ise.commondtos.ServiceRequest;
import com.tub.ise.commondtos.ServiceResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FilterService {

    public ServiceResponse filter (ServiceRequest request){
        List<Map<String, Object>> inputData = request.getData();
        Map<String, Object> config = request.getConfig();

        List<Map<String, Object>> filteredData = new ArrayList<>();

        for (Map<String, Object> item : inputData) {
            if (evaluateCondition(item, config)) {
                filteredData.add(item);
            }
        }

        return new ServiceResponse(filteredData);
    }

    private boolean evaluateCondition(Map<String, Object> item, Map<String, Object> config) {
        String field = (String) config.get("field");
        String operator = (String) config.get("operator");
        Object value = config.get("value");
        Comparable<Double> fieldValue = null;

        if (!item.containsKey(field)) {
            return false;
        }
        Object rawValue = item.get(field);
        if (rawValue == null || rawValue.equals("") ||(rawValue instanceof String && ((String) rawValue).isEmpty())) {
            return false;
        }
        if((rawValue instanceof String && isNumeric((String)rawValue))|| isNumeric(rawValue.toString())){
           fieldValue = Double.parseDouble(rawValue.toString());
        }
        if(fieldValue==null && (operator.equalsIgnoreCase(">"))||operator.equalsIgnoreCase("<")) {
            return false;
        }

        switch (operator) {
            case ">":
                return fieldValue.compareTo(Double.parseDouble(value.toString())) > 0;
            case "<":
                return fieldValue.compareTo(Double.parseDouble(value.toString())) < 0;
            case "==":
                return (item.get(field)).equals(value);
            case "contains":
                return ((String) item.get(field)).contains((String) value);
            case "equals":
                return ((String) item.get(field)).equalsIgnoreCase((String) value);
            default:
                return false;
        }
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
