package com.sugarmantou.formula.Controllers.Api;

import java.util.HashMap;
import java.util.Map;

import com.sugarmantou.formula.Module.Router.Request;
import com.sugarmantou.formula.Module.Router.Response;

public class MainController {
    public static void Main(Request req, Response res) {
        Map<String, Object> responseData = new HashMap<>();

        responseData.put("error", "Mission: Permission");
        res.code(403).jsonBody(responseData);
    }
}
