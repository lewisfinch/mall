package ui;

import model.Item;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TopBar {
    private JPanel panel;
    private JButton cartButton;

    public TopBar(String username) {
        panel = new JPanel(new BorderLayout());

        JButton mainPageButton = new JButton("Main Page");
        panel.add(mainPageButton, BorderLayout.WEST);

        JPanel rightTopBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartButton = new JButton("Cart");
        JButton returnButton = new JButton("Return/Cancel");

        String[] accountOptions = {"Account Info", "Logout"};
        JComboBox<String> accountDropdown = new JComboBox<>(accountOptions);
        rightTopBar.add(new JLabel("Hi, " + username + " "));
        rightTopBar.add(accountDropdown);
        rightTopBar.add(cartButton);
        rightTopBar.add(returnButton);

        panel.add(rightTopBar, BorderLayout.EAST);
    }

    public JPanel getPanel() {
        return panel;
    }

    public JButton getCartButton() {
        return cartButton;
    }
    public static List<Item> getSampleItems() {
        List<Item> items = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            String imageText = "[Image of Item " + i + "]"; // Placeholder for no image
            items.add(new Item(i, "Item " + i, Math.round((10 + Math.random() * 90) * 100.0) / 100.0,
                    1 + (int)(Math.random() * 10), imageText));
        }
        return items;
    }
}