package ui;

import model.Item;
import service.CartService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserPage extends JFrame {
    private JPanel contentPanel;
    private JComboBox<String> sortDropdown;
    private int currentPage = 0;
    private final int ITEMS_PER_PAGE = 10;

    private CartService cartService = new CartService();
    private List<Item> allItems;

    public UserPage(String username) {
        setTitle("Shopping Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Top bar
        TopBar topBar = new TopBar(username);
        add(topBar.getPanel(), BorderLayout.NORTH);
        topBar.getCartButton().addActionListener(e -> new CartPage(cartService));

        // Main content panel with BorderLayout
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Sort dropdown at top-left inside main panel
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sortDropdown = new JComboBox<>(new String[]{"Sort by Price", "Sort by Feature"});
        sortPanel.add(sortDropdown);
        centerPanel.add(sortPanel, BorderLayout.NORTH);

        // Grid of items
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(5, 2, 10, 10));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Footer with Prev/Next and Add to Cart
        JPanel footerPanel = new JPanel(new BorderLayout());

        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton prevButton = new JButton("< Prev");
        JButton nextButton = new JButton("Next >");
        navigationPanel.add(prevButton);
        navigationPanel.add(nextButton);

        prevButton.addActionListener(e -> showPreviousPage());
        nextButton.addActionListener(e -> showNextPage());

        JPanel cartButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addToCartButton = new JButton("Add Selected Items to Cart");
        addToCartButton.addActionListener(e -> {
            for (Component comp : contentPanel.getComponents()) {
                if (comp instanceof ItemPanel itemPanel && itemPanel.isSelected()) {
                    cartService.addToCart(itemPanel.getItem(), itemPanel.getSelectedQuantity());
                }
            }
            JOptionPane.showMessageDialog(this, "Selected items added to cart.");
        });
        cartButtonPanel.add(addToCartButton);

        footerPanel.add(navigationPanel, BorderLayout.WEST);
        footerPanel.add(cartButtonPanel, BorderLayout.EAST);

        add(footerPanel, BorderLayout.SOUTH);

        // Load and display items
        allItems = TopBar.getSampleItems();
        updateItems();

        setVisible(true);
    }

    private void showPreviousPage() {
        if (currentPage > 0) {
            currentPage--;
            updateItems();
        }
    }

    private void showNextPage() {
        int maxPage = (int) Math.ceil((double) allItems.size() / ITEMS_PER_PAGE) - 1;
        if (currentPage < maxPage) {
            currentPage++;
            updateItems();
        }
    }

    private void updateItems() {
        contentPanel.removeAll();
        int start = currentPage * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, allItems.size());
        for (int i = start; i < end; i++) {
            ItemPanel panel = new ItemPanel(allItems.get(i));
            contentPanel.add(panel);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserPage("JohnDoe"));
    }
}