package service;

import model.Order;
import model.OrderItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private static final String BASE_URL = "http://localhost:8080";

    // === Create a new order ===
    public static int createOrder(List<OrderItem> orderItems, int userId) {
        try {
            JSONArray detailsArray = new JSONArray();
            for (OrderItem oi : orderItems) {
                JSONObject itemObj = new JSONObject();
                itemObj.put("itemId", oi.getItemId());
                itemObj.put("num", oi.getQuantity());
                detailsArray.put(itemObj);
            }
            JSONObject orderObj = new JSONObject();
            orderObj.put("details", detailsArray);

            String response = sendPostRequest("/createOrder", orderObj.toString(), userId);
            if (response != null) {
                JSONObject obj = new JSONObject(response);
                return obj.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // === Get all my orders ===
    public static List<Order> getMyOrders(int userId) {
        List<Order> orders = new ArrayList<>();
        try {
            String response = sendGetRequest("/myOrders", userId);
            if (response != null) {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Order order = new Order();
                    order.setId(obj.getInt("id"));
                    order.setTotalFee(obj.getInt("totalFee"));
                    order.setStatus(obj.getInt("status"));
                    order.setCreateTime(obj.getString("createTime"));
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    // === Cancel an order ===
    public static void cancelOrder(int orderId, int userId) {
        sendPostRequest("/cancelOrder/" + orderId, null, userId);
    }

    // === Confirm receipt ===
    public static void confirmReceipt(int orderId) {
        sendPostRequest("/confirmRecipt/" + orderId, null, null);
    }

    // === Core: unified GET request ===
    private static String sendGetRequest(String endpoint, Integer userId) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(BASE_URL + endpoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (userId != null) {
                conn.setRequestProperty("userId", String.valueOf(userId));
            }

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readResponse(conn);
            } else {
                System.err.println("GET request failed, code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.disconnect();
        }
        return null;
    }

    // === Core: unified POST request ===
    private static String sendPostRequest(String endpoint, String body, Integer userId) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(BASE_URL + endpoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            if (userId != null) {
                conn.setRequestProperty("userId", String.valueOf(userId));
            }

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            if (body != null) {
                conn.setDoOutput(true);
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = body.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readResponse(conn);
            } else {
                System.err.println("POST request failed, code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.disconnect();
        }
        return null;
    }

    // === Core: read response body into String ===
    private static String readResponse(HttpURLConnection conn) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }
}

