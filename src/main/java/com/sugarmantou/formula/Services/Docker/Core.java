package com.sugarmantou.formula.Services.Docker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Core {

    public static String checkDockerStatus() throws IOException, InterruptedException {
        // Command to check Docker status
        String command = "sudo systemctl is-active docker";
    
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
        Process process = processBuilder.start();
    
        // Get the command output
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
    
        // Read the command output
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line);
        }
    
        // Wait for the command to complete
        int exitCode = process.waitFor();
    
        // Determine the status based on the command output
        if (exitCode == 0) {
            String status = output.toString().trim();
            System.out.println("Docker status: " + status);
            return "active".equalsIgnoreCase(status) ? "Running" : "Stopped";
        } else {
            return "Failed to check Docker status. Exit code: " + exitCode;
        }
    }
    
}
