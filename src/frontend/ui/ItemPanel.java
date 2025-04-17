package ui;

import model.Item;
import javax.swing.*;
import java.awt.*;

public class ItemPanel extends JPanel {
    private JCheckBox selectBox;
    private JLabel nameLabel, priceLabel, stockLabel;
    private JSpinner quantitySpinner;
    private Item item;

    public ItemPanel(Item item) {
        this.item = item;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        nameLabel = new JLabel(item.getName());
        priceLabel = new JLabel("Price: $" + item.getPrice());
        stockLabel = new JLabel("Stock: " + item.getStock());
        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(stockLabel);

        JPanel imagePanel = new JPanel();
        ImageIcon icon = new ImageIcon(item.getImagePath());
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(icon);
        imagePanel.add(imageLabel);

        JPanel bottomPanel = new JPanel();
        selectBox = new JCheckBox("Select");
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, item.getStock(), 1));
        bottomPanel.add(selectBox);
        bottomPanel.add(new JLabel("Qty:"));
        bottomPanel.add(quantitySpinner);

        add(imagePanel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public boolean isSelected() {
        return selectBox.isSelected();
    }

    public int getSelectedQuantity() {
        return (int) quantitySpinner.getValue();
    }

    public Item getItem() {
        return item;
    }
}
