package service;

import model.CartItem;
import model.Item;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CartService {
    private final List<CartItem> cartItems = new ArrayList<>();
    private static final String BASE_URL = "http://localhost:8080";
    private final int userId; // You need to get this from your auth system

    public CartService(int userId) {
        this.userId = userId;
    }

    public void addToCart(Item item, int quantity) {
        // Check if item already exists in cart
        for (CartItem i : cartItems) {
            if (i.getItem().getId() == item.getId()) {
                i.setQuantity(Math.min(item.getStock(), i.getQuantity() + quantity));
                updateCartItemInBackend(i);
                return;
            }
        }

        // If new item, add to cart
        CartItem newItem = new CartItem(item, quantity);
        int cartId = addItemToBackendCart(newItem);
        newItem.setCartId(cartId);
        cartItems.add(newItem);
    }

    public List<CartItem> getCartItems() {
        fetchCartItemsFromBackend();
        return new ArrayList<>(cartItems);
    }

    public double getTotalPrice() {
        return cartItems.stream()
                .mapToDouble(ci -> ci.getQuantity() * ci.getItem().getPrice())
                .sum();
    }

    private void fetchCartItemsFromBackend() {
        try {
            URL url = new URL(BASE_URL + "/myCart");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("userId", String.valueOf(userId));
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (var reader = new java.io.InputStreamReader(conn.getInputStream());
                     var br = new java.io.BufferedReader(reader)) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }

                    JSONArray jsonArray = new JSONArray(response.toString());
                    cartItems.clear(); // Clear existing items before adding new ones

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Item item = new Item(
                                obj.getInt("itemId"),
                                obj.getString("itemName"),
                                obj.getDouble("price"),
                                0, // stock not needed in cart
                                obj.optString("itemImage", ""),
                                "" // category not needed
                        );
                        cartItems.add(new CartItem(obj.getInt("id"),item, obj.getInt("itemNum")));
                    }
                }
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int addItemToBackendCart(CartItem cartItem) {
        try {
            URL url = new URL(BASE_URL + "/addToCart");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("userId", String.valueOf(userId));
            conn.setDoOutput(true);

            JSONObject cartDto = new JSONObject();
            cartDto.put("itemId", cartItem.getItem().getId());
            cartDto.put("itemName", cartItem.getItem().getName());
            cartDto.put("price", cartItem.getItem().getPrice());
            cartDto.put("itemImage", cartItem.getItem().getImage());
            cartDto.put("itemNum", cartItem.getQuantity());

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = cartDto.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (conn.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    String response = reader.readLine();
                    JSONObject json = new JSONObject(response);
                    return json.getInt("id"); // assume backend returns {"id": 5}
                }
            } else {
                System.err.println("Failed to add item to cart: " + conn.getResponseCode());
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    private void updateCartItemInBackend(CartItem cartItem) {
        try {
            URL url = new URL(BASE_URL + "/updateCart");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("userId", String.valueOf(userId));
            conn.setDoOutput(true);

            JSONObject cartJson = new JSONObject();
            cartJson.put("id", cartItem.getItem().getId());
            cartJson.put("itemId", cartItem.getItem().getId());
            cartJson.put("itemNum", cartItem.getQuantity());
            cartJson.put("itemName", cartItem.getItem().getName());
            cartJson.put("price", cartItem.getItem().getPrice());
            cartJson.put("itemImage", cartItem.getItem().getImage());

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = cartJson.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.err.println("Failed to update cart item: " + responseCode);
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeItemFromCart(int itemId) {
        cartItems.removeIf(ci -> ci.getItem().getId() == itemId);
        removeItemFromBackendCart(itemId);
    }

    private void removeItemFromBackendCart(int itemId) {
        try {
            URL url = new URL(BASE_URL + "/deleteCart/" + itemId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("userId", String.valueOf(userId));

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.err.println("Failed to remove item from cart: " + responseCode);
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void deleteCartItems(int userId, List<Integer> itemIds) {
        try {
            URL url = new URL(BASE_URL + "/deleteCartBatch?userId=" + userId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONArray jsonArray = new JSONArray(itemIds);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonArray.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            if (conn.getResponseCode() != 200) {
                System.err.println("Failed to delete cart items, code: " + conn.getResponseCode());
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
