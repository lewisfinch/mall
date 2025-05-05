package ui;

import model.Item;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ItemPanel extends JPanel {
    private final JCheckBox selectBox;
    private final JLabel nameLabel, priceLabel, stockLabel, categoryLabel;
    private final JSpinner quantity;
    private final Item item;

    public ItemPanel(Item item) {
        this.item = item;
        setLayout(new BorderLayout(10, 5));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        // Info panel: Name, Price, Stock, Category
        JPanel infoPanel = new JPanel(new GridLayout(4, 1));
        nameLabel = new JLabel(item.getName());
        priceLabel = new JLabel("Price: $" + item.getPrice());
        stockLabel = new JLabel("Stock: " + item.getStock());
        categoryLabel = new JLabel("Category: " + item.getCategory());
        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(stockLabel);
        infoPanel.add(categoryLabel);

        // Image panel
        JPanel imagePanel = new JPanel();
        JLabel imageLabel = new JLabel();
        imagePanel.add(imageLabel);

        try {
            ImageIcon icon;
            if (item.getImage() != null && item.getImage().startsWith("http")) {
                icon = new ImageIcon(new URL(item.getImage()));
            } else {
                icon = new ImageIcon(item.getImage());
            }
            Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            imageLabel.setText("Image load failed");
        }

        // Bottom panel: Select box + quantity
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectBox = new JCheckBox("Select");

        if (item.getStock() > 0) {
            quantity = new JSpinner(new SpinnerNumberModel(1, 1, item.getStock(), 1));
        } else {
            quantity = new JSpinner(new SpinnerNumberModel(0, 0, 0, 0));
            quantity.setEnabled(false);
        }
        JFormattedTextField spinnerField = ((JSpinner.DefaultEditor) quantity.getEditor()).getTextField();
        spinnerField.setColumns(3);

        bottomPanel.add(selectBox);
        bottomPanel.add(new JLabel("Qty:"));
        bottomPanel.add(quantity);

        add(imagePanel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public boolean isSelected() {
        return selectBox.isSelected();
    }

    public int getSelectedQuantity() {
        try {
            return ((Number) quantity.getValue()).intValue();
        } catch (Exception e) {
            return 1;
        }
    }

    public Item getItem() {
        return item;
    }
}
