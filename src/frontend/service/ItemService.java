package service;

import model.Item;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ItemService {

    private static final String BASE_URL = "http://localhost:8080";

    //display all the item in the mall system
    public static List<Item> fetchItemsFromAPI() {
        return fetchItems("/list");
    }

    // for category search
    public static List<Item> fetchItemsByCategory(String category) {
        return fetchItems("/getItemByCategory/" + category);
    }
    // for price search
    public static List<Item> fetchItemsByPriceRange(int low, int high) {
        return fetchItems("/getItemByPrices?low=" + low + "&high=" + high);
    }
    // for category with price search
    public static List<Item> fetchItemsByCategoryAndPrice(String category, int low, int high) {
        return fetchItems("/getItemByCategoryAndPrices?category=" + category + "&low=" + low + "&high=" + high);
    }

    // single item information (which deal with stock mainly)
    public static Item fetchItemById(int id) {
        try {
            String response = sendGetRequest("/getItemById/" + id);
            if (response != null && !response.isEmpty()) {
                JSONObject obj = new JSONObject(response.trim());
                return parseItem(obj);
            }
        } catch (Exception e) {
            System.err.println("Error parsing item by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private static List<Item> fetchItems(String endpoint) {
        List<Item> items = new ArrayList<>();
        try {
            String response = sendGetRequest(endpoint);
            if (response == null || response.isEmpty()) {
                return items;
            }

            String jsonText = response.trim();
            if (!jsonText.startsWith("[")) {
                jsonText = "[" + jsonText + "]";
            }

            JSONArray jsonArray = new JSONArray(jsonText);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                items.add(parseItem(obj));
            }
        } catch (Exception e) {
            System.err.println("Error parsing item list: " + e.getMessage());
            e.printStackTrace();
        }
        return items;
    }

    private static String sendGetRequest(String endpoint) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(BASE_URL + endpoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("User-Agent", "Java Client");
            conn.setRequestProperty("userId", "1");

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();
            System.out.println("GET " + endpoint + " Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    return response.toString();
                }
            } else {
                System.err.println("GET request failed: " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Error during GET request: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    private static Item parseItem(JSONObject obj) {
        return new Item(
                obj.getInt("id"),
                obj.getString("name"),
                obj.getDouble("price"),
                obj.getInt("stock"),
                obj.optString("image", ""),
                obj.optString("category", "")
        );
    }
}
