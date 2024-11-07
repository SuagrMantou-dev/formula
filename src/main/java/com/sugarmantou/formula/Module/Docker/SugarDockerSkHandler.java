// package com.sugarmantou.formula.Module.Docker;

// import java.io.BufferedReader;
// import java.io.File;
// import java.io.InputStreamReader;
// import java.io.OutputStream;

// import org.newsclub.net.unix.AFUNIXSocket;
// import org.newsclub.net.unix.AFUNIXSocketAddress;

// public class SugarDockerSkHandler {
//     private static final String SOCKET_PATH = "/var/run/docker.sock"; // Docker socket path
    
//     protected String sendRequest(String endpoint, String method, String body) throws Exception {
//         try (AFUNIXSocket channel = AFUNIXSocket.newInstance()) {
//             channel.connect(new AFUNIXSocketAddress(new File(SOCKET_PATH)));
    
//             // 建立 HTTP 請求
//             String request = method + " " + endpoint + " HTTP/1.1\r\n" +
//                              "Host: localhost\r\n" +
//                              "Content-Type: application/json\r\n" +
//                              "Connection: close\r\n";
    
//             if (body != null) {
//                 request += "Content-Length: " + body.length() + "\r\n\r\n" + body;
//             } else {
//                 request += "\r\n";
//             }
    
//             // 發送請求
//             OutputStream outputStream = channel.getOutputStream();
//             outputStream.write(request.getBytes());
//             outputStream.flush();
    
//             // 讀取回應
//             BufferedReader reader = new BufferedReader(new InputStreamReader(channel.getInputStream()));
//             StringBuilder response = new StringBuilder();
//             String line;
//             boolean bodyStarted = false;

//             // 讀取並過濾只包含 JSON 的回應內容
//             while ((line = reader.readLine()) != null) {
//                 if (bodyStarted) {
//                     response.append(line).append("\n");
//                 } else if (line.isEmpty()) { // 空行標誌著 HTTP header 結束，開始讀取 body
//                     bodyStarted = true;
//                 }
//             }
    
//             // 確保回傳的內容是 JSON 格式
//             String responseBody = response.toString().trim();
//             if (responseBody.matches("^\\d+\\s*\\{.*")) {
//                 // 如果以數字開頭，去除數字部分
//                 responseBody = responseBody.replaceFirst("^\\d+\\s*", "");
//             }
    
//             return responseBody;
//         }
//     }
// }
