package com.sugarmantou.formula.test.Docker;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import jnr.unixsocket.UnixSocket;
import jnr.unixsocket.UnixSocketAddress;
import jnr.unixsocket.UnixSocketChannel;

public class DockerApiCallTest {

    public String listContainers() throws IOException {
        try (UnixSocket unixSocket = createUnixSocket()) {
            PrintWriter writer = new PrintWriter(unixSocket.getOutputStream());
            writer.println("GET /v1.24/containers/json HTTP/1.1");
            writer.println("Host: http");
            writer.println("Accept: application/json");
            writer.println("");
            writer.flush();
            unixSocket.shutdownOutput();

            return parseJsonResponse(unixSocket);
        }
    }

    // Method to create a container and return JSON response
    public String createContainer(String config, String containerName) throws IOException {
        try (UnixSocket unixSocket = createUnixSocket()) {
            PrintWriter writer = new PrintWriter(unixSocket.getOutputStream());
            writer.println("POST /v1.24/containers/create?name=" + containerName + " HTTP/1.1");
            writer.println("Host: http");
            writer.println("Accept: application/json");
            writer.println("Content-Type: application/json");
            writer.println("Content-Length: " + config.getBytes().length);
            writer.println("");
            writer.write(config);
            writer.flush();
            unixSocket.shutdownOutput();

            return parseJsonResponse(unixSocket);
        }
    }

    // Method to create UnixSocket for Docker connection
    private UnixSocket createUnixSocket() throws IOException {
        File sockFile = new File("/var/run/docker.sock");
        UnixSocketAddress address = new UnixSocketAddress(sockFile);
        UnixSocketChannel channel = UnixSocketChannel.open(address);
        return new UnixSocket(channel);
    }

    private String parseJsonResponse(UnixSocket unixSocket) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(unixSocket.getInputStream()))) {
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            boolean isJsonContent = false;

            while ((line = reader.readLine()) != null) {
                // Check for the start of JSON content (array)
                if (line.startsWith("[")) {
                    isJsonContent = true;
                }
                if (isJsonContent) {
                    responseBuilder.append(line.trim());
                }
                // Check for the end of JSON content (array)
                if (line.endsWith("]") && isJsonContent) {
                    break;
                }
            }

            // Remove escape sequences like \"
            String cleanJson = responseBuilder.toString().replaceAll("\\\\", "");

            // If the response is wrapped in an array, remove the array brackets
            // if (cleanJson.startsWith("[") && cleanJson.endsWith("]")) {
            //     cleanJson = cleanJson.substring(1, cleanJson.length() - 1); // Remove the surrounding brackets
            // }

            return cleanJson;
        }
    }

}
