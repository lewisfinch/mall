package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {
    private int cartId;
    private final Item item;
    private int quantity;

    public CartItem(int cartId,Item item, int quantity) {
        this.cartId = cartId;
        this.item = item;
        this.quantity = quantity;
    }
    // For temporary item before you know the cartId
    public CartItem(Item item, int quantity) {
        this(-1, item, quantity);
    }
}
