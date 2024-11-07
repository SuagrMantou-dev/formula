package com.sugarmantou.formula.Module.Router;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Response {
    private int statusCode = 200; // default OK
    private String body;
    private final Map<String, String> headers = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper(); // Instantiate ObjectMapper

    public Response code(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Response body(String body) {
        this.body = body;
        return this;
    }

    public Response jsonBody(Object data) {
        try {
            this.body = objectMapper.writeValueAsString(data);
            this.headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        } catch (JsonProcessingException e) {
            this.statusCode = 500;
            this.body = "{\"error\":\"Failed to serialize JSON\"}";
        }
        return this;
    }

    public Response addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public ResponseEntity<String> buildResponse() {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::set);
        return ResponseEntity.status(this.statusCode)
                .headers(httpHeaders)
                .body(this.body);
    }
}
