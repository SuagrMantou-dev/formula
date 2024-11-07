package com.sugarmantou.formula.Services.Node;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class Infomation {

    private static JSONObject configJson;

    public static String Version() {
        if (configJson == null) {
            loadConfig();
        }

        return configJson != null ? configJson.getString("version") : null;
    }

    private static void loadConfig() {
        try (InputStream inputStream = Infomation.class.getClassLoader().getResourceAsStream("config/formula.json")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: config/formula.json");
            }

            String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            configJson = new JSONObject(json);
        } catch (Exception e) { 
            System.out.println(e);
        }
    }
}
