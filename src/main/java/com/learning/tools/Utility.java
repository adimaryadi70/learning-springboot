package com.learning.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public class Utility {
    private static final Logger logger = LoggerFactory.getLogger(Utility.class);

    public Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        }  
        if (direction.equals("desc")){
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    public Map<String, Object> responseSuccesGet(List<?> data, Page<?> pagination) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", "00");
        response.put("data", data);
        response.put("message", "Success");
        response.put("currentPage", pagination.getNumber()+1);
        response.put("totalItems", pagination.getTotalElements());
        response.put("totalPages", pagination.getTotalPages());
        return response;
    }

    public Map<String, Object> responseError(Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", "99");
        response.put("message", e.getMessage());
        return response;
    }

    public Map<String, Object> responseInfo(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", "1A");
        response.put("message", message);
        return response;
    }

    public Map<String, Object> responseSuccess(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", "00");
        response.put("data", data);
        response.put("message", "Success");
        return response;
    }
    
    public String generateUser(String name) {
        int limitText = 5;
        if (name.length() > limitText) {
            name = name.substring(0, limitText);
        }
        String nameUppercase = name.toUpperCase();
        return nameUppercase+generateNumber();
    }

    public String generateNumber() {
        Random rand = new Random();
        int restRandom = 0;
        for (int i = 1; i < 10; i++) {
            restRandom = rand.nextInt((9999) + 1) + 10;
        }
        String numberToString = Integer.toString(restRandom);
        String numberZero = "00000000000000000000000000000000";
        String limitRandom = numberToString;
        if (numberToString.length() < 4) {
            numberToString = numberToString+numberZero;
            limitRandom = numberToString.substring(0, 4);
        }
        return limitRandom;
    }
}
