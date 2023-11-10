package com.learning.implement;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthJwt implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,AuthenticationException authException) throws IOException, ServletException {
       logger.error("Unauthorize Error", authException.getMessage());
       response.setContentType(MediaType.APPLICATION_JSON_VALUE);
       response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

       final Map<String, Object> data = new HashMap<>();
       data.put("status", HttpServletResponse.SC_UNAUTHORIZED);
       data.put("error", "Unauthorize");
       data.put("message", authException.getMessage());

       final ObjectMapper mapper = new ObjectMapper();
       mapper.writeValue(response.getOutputStream(), data);
    };
}
