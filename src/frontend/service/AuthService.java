package service;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

    public static Map<String, String> login(model.User user) {
        String json = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\"}",
                user.getUsername(),
                user.getPassword()
        );
        return sendPostRequest("http://localhost:8080/login", json);
    }

    public static Map<String, String> signUp(String username,
                                             String password,
                                             String firstName,
                                             String lastName,
                                             String email) {
        String json = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\",\"fname\":\"%s\",\"lname\":\"%s\",\"email\":\"%s\"}",
                username, password, firstName, lastName, email
        );
        return sendPostRequest("http://localhost:8080/signUp", json);
    }

    private static Map<String, String> sendPostRequest(String urlString, String json) {
        HttpURLConnection conn = null;
        // create the URL object
        Map<String, String> responseMap = new HashMap<>();

        try {
            // 1) open connection
            URL url = new URL(urlString);
            // open connection to the url
            conn = (HttpURLConnection) url.openConnection();
            //down cast for specific method (such as post,get)
            conn.setRequestMethod("POST"); // set http method
            conn.setRequestProperty("Content-Type", "application/json"); // http header
            conn.setDoOutput(true); // means I intend to send a request body

            // 2) write request JSON: conn.getOutputStream()=>opens a channel to send request body
            // using try to autoclose the connection after finished prevent resource leak
            try (OutputStream os = conn.getOutputStream()) {
                // convert the json to byte[] since outputStream only deal with byte[]
                // then send to server
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            InputStream is = conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                //convert inputStream (raw byte) to the UTF-8 encoded text
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line.trim());
                }
            }

            // 4) parse JSON
            JSONObject jr = new JSONObject(sb.toString());
            String status = jr.optString("status", "error");
            String message = jr.optString("message", "Unknown response from server");
            responseMap.put("status", status);
            responseMap.put("message", message);

            if (jr.has("username")) {
                responseMap.put("username", jr.getString("username"));
            }
            if (jr.has("userId")) {
                String userId = String.valueOf(jr.getInt("userId"));
                responseMap.put("userId", userId);
            }

        } catch (Exception e) {
            responseMap.put("status", "error");
            responseMap.put("message", "Connection error: " + e.getMessage());
        } finally {
            if (conn != null) conn.disconnect();
        }

        return responseMap;
    }
}
