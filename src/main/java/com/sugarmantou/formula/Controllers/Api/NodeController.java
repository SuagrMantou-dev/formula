package com.sugarmantou.formula.Controllers.Api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sugarmantou.formula.Module.Router.Request;
import com.sugarmantou.formula.Module.Router.Response;
import com.sugarmantou.formula.Services.Docker.Core;
import com.sugarmantou.formula.Services.Node.Infomation;

public class NodeController {

    public static void Status(Request req, Response res) {
        Map<String, Object> responseData = new HashMap<>();
        String formula_Version = Infomation.Version();
        try {
            String docker_Status = Core.checkDockerStatus();
            responseData.put("Docker", docker_Status);
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }

        responseData.put("Status", true);
        responseData.put("Version", formula_Version);
        res.code(200).jsonBody(responseData);
    }

}
