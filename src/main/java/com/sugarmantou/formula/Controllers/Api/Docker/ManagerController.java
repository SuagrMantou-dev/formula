package com.sugarmantou.formula.Controllers.Api.Docker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugarmantou.formula.Module.Docker.SugarDockerSkCore;
import com.sugarmantou.formula.Module.Router.Request;
import com.sugarmantou.formula.Module.Router.Response;
import com.sugarmantou.formula.Utility.sLogger.Logger;

public class ManagerController {

    public static void List(Request req, Response res) throws Exception {

        Map<String, Object> responseData = new HashMap<>();
        List<Map<String, Object>> containersList = new ArrayList<>();
        String listData;
        SugarDockerSkCore sugarDockerSdk = new SugarDockerSkCore();

        try {
            listData = sugarDockerSdk.listContainers(); // Get container list data
            ObjectMapper objectMapper = new ObjectMapper();
            // 解析为JsonNode数组
            JsonNode arrayNode = objectMapper.readTree(listData);

            // 遍历数组中的每个对象
            for (JsonNode jsonObject : arrayNode) {
                Map<String, Object> containerData = new HashMap<>();
                containerData.put("ID", jsonObject.get("Id").asText());
                containerData.put("Name", jsonObject.get("Names").get(0).asText());
                containersList.add(containerData);
            }

            // System.out.println(listData);
            responseData.put("Data", containersList);

        } catch (IOException e) {
            responseData.put("Error", "無法獲取容器列表");
            Logger.log("Error while listing containers: " + e.getMessage());
            res.code(500).jsonBody(responseData); // Return 500 error
            return;
        }

        // Populate response data with the list of containers
        res.code(200).jsonBody(responseData); // Return the 200 response with container list
    }
}
