package ui;

import model.CartItem;
import model.Item;
import model.OrderItem;
import service.CartService;
import service.OrderService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import service.ItemService;


public class CartPage extends JFrame {
    private final CartService cartService;
    private final int userId;
    private final JPanel itemsPanel;
    private final List<JCheckBox> selectBoxes = new ArrayList<>();
    private final List<CartItem> cartItems = new ArrayList<>();
    private final JLabel selectedTotalLabel = new JLabel("Selected Total: $0.0");
    private UserPage userPage;

    public CartPage(CartService cartService, int userId, UserPage userPage) {
        this.cartService = cartService;
        this.userId = userId;
        this.userPage = userPage;
        setTitle("Your Cart");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new BorderLayout());

        JButton submitButton = new JButton("Submit Order");
        submitButton.setPreferredSize(new Dimension(140, 30));
        submitButton.addActionListener(e -> submitSelectedItems());

        selectedTotalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        selectedTotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        selectedTotalLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        footerPanel.add(submitButton, BorderLayout.WEST);
        footerPanel.add(selectedTotalLabel, BorderLayout.EAST);

        add(footerPanel, BorderLayout.SOUTH);
        loadCartItems();
        setVisible(true);
    }

    private void loadCartItems() {
        itemsPanel.removeAll();
        selectBoxes.clear();
        cartItems.clear();
        List<CartItem> items = cartService.getCartItems();
        cartItems.addAll(items);

        for (CartItem ci : items) {
            JPanel rowPanel = new JPanel();
            rowPanel.setPreferredSize(new Dimension(600, 50));
            rowPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));
            rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
            rowPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));

            JCheckBox checkBox = new JCheckBox();
            checkBox.addActionListener(e -> updateSelectedTotal());
            selectBoxes.add(checkBox);
            rowPanel.add(checkBox);
            rowPanel.add(Box.createRigidArea(new Dimension(10, 0)));

            rowPanel.add(new JLabel(ci.getItem().getName()));
            rowPanel.add(Box.createHorizontalGlue());

            rowPanel.add(new JLabel("Price: $" + ci.getItem().getPrice()));
            rowPanel.add(Box.createRigidArea(new Dimension(10, 0)));

            rowPanel.add(new JLabel("Qty: " + ci.getQuantity()));
            rowPanel.add(Box.createRigidArea(new Dimension(10, 0)));

            rowPanel.add(new JLabel("Subtotal: $" + (ci.getItem().getPrice() * ci.getQuantity())));
            rowPanel.add(Box.createRigidArea(new Dimension(10, 0)));

            JButton deleteButton = new JButton("Delete");
            deleteButton.setPreferredSize(new Dimension(80, 25));
            deleteButton.addActionListener(e -> {
                cartService.removeItemFromCart(ci.getCartId());
                loadCartItems(); // Reload after deletion
            });
            rowPanel.add(deleteButton);

            itemsPanel.add(rowPanel);
        }
        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    // submit the order
    private void submitSelectedItems() {
        List<CartItem> selectedItems = new ArrayList<>();
        List<Integer> itemIdsToDelete = new ArrayList<>();

        for (int i = 0; i < selectBoxes.size(); i++) {
            if (selectBoxes.get(i).isSelected()) {
                selectedItems.add(cartItems.get(i));
                itemIdsToDelete.add(cartItems.get(i).getItem().getId());
            }
        }

        if (selectedItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items selected!");
            return;
        }

        try {
            for (CartItem ci : selectedItems) {
                Item latestItem = ItemService.fetchItemById(ci.getItem().getId());
                if (ci.getQuantity() > latestItem.getStock()) {
                    JOptionPane.showMessageDialog(this,
                            "Not enough stock for item: " + ci.getItem().getName() +
                                    ". Available: " + latestItem.getStock() + ", You selected: " + ci.getQuantity());
                    return;
                }
            }

            List<OrderItem> orderItems = new ArrayList<>();
            for (CartItem ci : selectedItems) {
                OrderItem oi = new OrderItem(ci.getItem().getId(), ci.getQuantity());
                orderItems.add(oi);
            }

            int orderId = OrderService.createOrder(orderItems, userId);
            if (orderId != -1) {
                JOptionPane.showMessageDialog(this, "Order created successfully! Order ID: " + orderId);
                CartService.deleteCartItems(userId, itemIdsToDelete);
                userPage.refreshAllItems();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create order!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while creating order!");
        }
    }

    private void updateSelectedTotal() {
        double total = 0.0;
        for (int i = 0; i < selectBoxes.size(); i++) {
            if (selectBoxes.get(i).isSelected()) {
                CartItem ci = cartItems.get(i);
                total += ci.getQuantity() * ci.getItem().getPrice();
            }
        }
        selectedTotalLabel.setText("Selected Total: $" + total);
    }
}

