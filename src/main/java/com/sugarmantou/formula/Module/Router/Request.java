package com.sugarmantou.formula.Module.Router;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public class Request {
    private final HttpServletRequest request;
    private Map<String, Object> jsonBody;  // 用來存儲解析後的 JSON 主體

    public Request(HttpServletRequest request) {
        this.request = request;
    }

    public String getParam(String name) {
        return request.getParameter(name);
    }

    public String getPath() {
        return request.getRequestURI();
    }

    // 讀取請求體，返回原始的 JSON 字符串
    public String getBody() throws IOException {
        return new String(request.getInputStream().readAllBytes());
    }

    // 設置解析後的 JSON 物件
    public void setJsonBody(Map<String, Object> jsonBody) {
        this.jsonBody = jsonBody;
    }

    // 獲取解析後的 JSON 物件
    public Map<String, Object> getJsonBody() {
        return jsonBody;
    }
}
