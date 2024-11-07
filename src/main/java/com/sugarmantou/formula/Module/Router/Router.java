package com.sugarmantou.formula.Module.Router;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.databind.ObjectMapper;  // 引入 Jackson ObjectMapper

import jakarta.servlet.http.HttpServletRequest;

public class Router {

    private final RequestMappingHandlerMapping handlerMapping;
    private final Map<RequestMethod, Map<String, RouteInfo>> routes;
    private static final ObjectMapper objectMapper = new ObjectMapper();  // 初始化 ObjectMapper

    public Router(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
        this.routes = new HashMap<>();
        for (RequestMethod method : RequestMethod.values()) {
            routes.put(method, new HashMap<>());
        }
    }

    private static class RouteInfo {
        final BiConsumer<Request, Response> handler;
        final Pattern pattern;

        RouteInfo(BiConsumer<Request, Response> handler, Pattern pattern) {
            this.handler = handler;
            this.pattern = pattern;
        }
    }

    public void get(String path, BiConsumer<Request, Response> handler) {
        registerMapping(path, handler, RequestMethod.GET);
    }

    public void post(String path, BiConsumer<Request, Response> handler) {
        registerMapping(path, handler, RequestMethod.POST);
    }

    public void delete(String path, BiConsumer<Request, Response> handler) {
        registerMapping(path, handler, RequestMethod.DELETE);
    }

    private void registerMapping(String path, BiConsumer<Request, Response> handler, RequestMethod method) {
        System.out.println("=====================================");
        if (handler == null) {
            throw new IllegalArgumentException("Handler cannot be null");
        }
    
        try {
            // Convert path variables to regex
            String patternString = path.replaceAll("\\{\\w+}", "([^/]+)");
            Pattern pattern = Pattern.compile("^" + patternString + "$");
            routes.get(method).put(path, new RouteInfo(handler, pattern));
            System.out.println("Registered [" + method + "] " + path);
    
            RequestMappingInfo mappingInfo = RequestMappingInfo
                    .paths(path)
                    .methods(method)
                    .build();
    
            handlerMapping.registerMapping(
                    mappingInfo,
                    this,
                    Router.class.getMethod("handleRequest", HttpServletRequest.class)
            );
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Failed to register mapping", e);
        }
    }

    @RequestMapping
    public ResponseEntity<String> handleRequest(HttpServletRequest request) {
        RequestMethod method = RequestMethod.valueOf(request.getMethod());
        String path = request.getRequestURI();
        System.out.println("[" + method + "] " + path);

        for (Map.Entry<String, RouteInfo> entry : routes.get(method).entrySet()) {
            RouteInfo routeInfo = entry.getValue();
            Matcher matcher = routeInfo.pattern.matcher(path);

            if (matcher.matches()) {
                Request customRequest = new Request(request);
                Response customResponse = new Response();

                // 如果是 POST 請求，提取並解析 JSON 主體
                if (method == RequestMethod.POST) {
                    try {
                        // 讀取並解析請求體
                        String jsonBody = customRequest.getBody();
                        Map<String, Object> jsonMap = objectMapper.readValue(jsonBody, Map.class);
                        customRequest.setJsonBody(jsonMap);  // 將解析後的 JSON 存入 Request 物件
                    } catch (IOException e) {
                        customResponse.code(400).jsonBody(Map.of("error", "Invalid JSON body"));
                        return customResponse.buildResponse();  // 返回錯誤響應
                    }
                }

                // 執行對應的處理器
                routeInfo.handler.accept(customRequest, customResponse);

                // 返回響應
                return customResponse.buildResponse();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
