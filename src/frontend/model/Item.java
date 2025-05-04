package model;

import lombok.Getter;

@Getter
public class Item {
    // Getters
    private int id;
    private String name;
    private double price;
    private int stock;
    private String image;
    private String category;

    // Constructor
    public Item(int id, String name, double price, int stock, String image, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.image = image;
        this.category = category;
    }

    // Optional: toString() for debugging
    @Override
    public String toString() {
        return String.format("Item{id=%d, name='%s', price=%.2f, stock=%d, category='%s'}",
                id, name, price, stock, category);
    }
}
