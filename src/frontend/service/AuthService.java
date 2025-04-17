package service;

import model.User;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AuthService {

    public static String login(User user) {
        String json = String.format(
                "{\"username\":\"%s\", \"password\":\"%s\"}",
                user.getUsername(), user.getPassword()
        );
        return sendPostRequest("http://localhost:8080/login", json);
    }

    public static String signUp(String username, String password, String firstName, String lastName,String phone) {
        String json = String.format(
                "{\"username\":\"%s\", \"password\":\"%s\", \"firstName\":\"%s\",\"lastName\":\"%s\",\"phone\":\"%s\"}",
                username, password, firstName, lastName, phone
        );
        return sendPostRequest("http://localhost:8080/signup", json);
    }

    private static String sendPostRequest(String urlString, String json) {
        HttpURLConnection conn = null;

        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            try (InputStream is = (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST)
                    ? conn.getInputStream()
                    : conn.getErrorStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
                return response.toString();
            }

        } catch (Exception e) {
            return "Request error: " + e.getMessage();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}

