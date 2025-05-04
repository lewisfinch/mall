package ui;

import model.Order;
import service.OrderService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class OrderPage extends JFrame {
    private final int userId;
    private final JPanel contentPanel;

    public OrderPage(int userId) {
        this.userId = userId;

        setTitle("My Orders");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);

        loadOrders();
        setVisible(true);
    }

    private void loadOrders() {
        contentPanel.removeAll();

        List<Order> orders = OrderService.getMyOrders(userId);

        orders.sort(Comparator.comparingInt(order -> {
            if (order.getStatus() == 1) return -1;
            else if (order.getStatus() == 2) return 0;
            else return 1;
        }));

        for (Order order : orders) {
            JPanel orderPanel = createOrderPanel(order);
            contentPanel.add(orderPanel);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createOrderPanel(Order order) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(600, 100));
        panel.setMaximumSize(new Dimension(600, 100));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(Color.WHITE);

        JPanel left = new JPanel(new GridLayout(4, 1));
        left.setOpaque(false);
        left.add(new JLabel("Order ID: " + order.getId()));
        left.add(new JLabel("Total Fee: $" + (order.getTotalFee())));
        left.add(new JLabel("Status: " + mapStatus(order.getStatus())));
        left.add(new JLabel("Order Time: " + order.getCreateTime()));

        panel.add(left, BorderLayout.CENTER);

        if (shouldShowCancelButton(order)) {
            JButton cancelButton = new JButton("Cancel & Refund");
            cancelButton.setPreferredSize(new Dimension(120, 30));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 12));

            cancelButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure to cancel this order?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    cancelButton.setEnabled(false);
                    cancelButton.setText("Canceling...");

                    SwingWorker<Void, Void> worker = new SwingWorker<>() {
                        @Override
                        protected Void doInBackground() {
                            try {
                                OrderService.cancelOrder(order.getId(),userId);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(OrderPage.this, "Cancel failed!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            return null;
                        }

                        @Override
                        protected void done() {
                            JOptionPane.showMessageDialog(OrderPage.this, "Order canceled and refunded successfully!");
                            loadOrders();
                        }
                    };
                    worker.execute();
                }
            });

            JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            buttonWrapper.setPreferredSize(new Dimension(150, 80));
            buttonWrapper.setOpaque(false);
            buttonWrapper.add(cancelButton);

            panel.add(buttonWrapper, BorderLayout.EAST);
        }

        return panel;
    }

    private boolean shouldShowCancelButton(Order order) {
        return (order.getStatus() == 1) || (order.getStatus() == 2 && withinSevenDays(order.getCreateTime()));
    }

    private boolean withinSevenDays(String createTimeStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime createTime = LocalDateTime.parse(createTimeStr, formatter);
            LocalDateTime now = LocalDateTime.now();
            return !createTime.plusDays(7).isBefore(now);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String mapStatus(int status) {
        return switch (status) {
            case 1 -> "Paid, Waiting for Delivery";
            case 2 -> "Shipped (7 days refund)";
            case 3-> "Canceled and Refunded";
            default -> "Closed";
        };
    }
}


