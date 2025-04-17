package ui;

import model.CartItem;
import service.CartService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CartPage extends JFrame {
    private final CartService cartService;
    private final JPanel itemsPanel;
    private final JLabel totalLabel;

    public CartPage(CartService cartService) {
        this.cartService = cartService;

        setTitle("Your Cart");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        add(scrollPane, BorderLayout.CENTER);

        totalLabel = new JLabel();
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(totalLabel, BorderLayout.SOUTH);

        loadCartItems();

        setVisible(true);
    }

    private void loadCartItems() {
        itemsPanel.removeAll();
        List<CartItem> items = cartService.getCartItems();

        for (CartItem ci : items) {
            JPanel panel = new JPanel(new GridLayout(1, 4));
            panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            panel.add(new JLabel(ci.getItem().getName()));
            panel.add(new JLabel("Price: $" + ci.getItem().getPrice()));
            panel.add(new JLabel("Qty: " + ci.getQuantity()));
            panel.add(new JLabel("Subtotal: $" + (ci.getItem().getPrice() * ci.getQuantity())));
            itemsPanel.add(panel);
        }

        totalLabel.setText("Total: $" + cartService.getTotalPrice());
        itemsPanel.revalidate();
        itemsPanel.repaint();
    }
}

