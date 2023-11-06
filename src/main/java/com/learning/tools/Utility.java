package com.learning.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public class Utility {
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
    
}
