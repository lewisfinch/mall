package service;

import model.CartItem;
import model.Item;

import java.util.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class CartService {
    private final List<CartItem> cartItems = new ArrayList<>();

    public void addToCart(Item item, int quantity) {
        for (CartItem ci : cartItems) {
            if (ci.getItem().getId() == item.getId()) {
                ci.setQuantity(Math.min(item.getStock(), ci.getQuantity() + quantity));
                syncCartToBackend();
                return;
            }
        }
        cartItems.add(new CartItem(item, quantity));
        syncCartToBackend();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public double getTotalPrice() {
        double total = 0.0;
        for (CartItem ci : cartItems) {
            total += ci.getQuantity() * ci.getItem().getPrice();
        }
        return total;
    }

    private void syncCartToBackend() {
        try {
            URL url = new URL("http://localhost:8080/cart");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONArray jsonCart = new JSONArray();
            for (CartItem ci : cartItems) {
                JSONObject jsonItem = new JSONObject();
                jsonItem.put("itemId", ci.getItem().getId());
                jsonItem.put("quantity", ci.getQuantity());
                jsonCart.put(jsonItem);
            }

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonCart.toString().getBytes());
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.err.println("Failed to sync cart: " + responseCode);
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
