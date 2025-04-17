package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import model.Item;
import org.json.*;

public class ItemService {
    public static List<Item> fetchItemsFromAPI() {
        List<Item> items = new ArrayList<>();
        try {
            URL url = new URL("http://localhost:8080/items");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Item item = new Item(
                        obj.getInt("id"),
                        obj.getString("name"),
                        obj.getDouble("price"),
                        obj.getInt("stock"),
                        obj.getString("image")
                );
                items.add(item);
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
}

