package ui;

import model.Item;
import service.CartService;
import service.ItemService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserPage extends JFrame {
    private final CartService cartService;
    private final JPanel contentPanel;
    private final int ITEMS_PER_PAGE = 10;
    private int currentPage = 0;
    private List<Item> allItems;
    private JComboBox<String> categoryDropdown;
    private JTextField lowPriceField;
    private JTextField highPriceField;

    public UserPage(int userId, String username) {
        super("Shopping Page");
        this.cartService = new CartService(userId);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top bar
        JPanel topPanel = new JPanel(new BorderLayout());
        TopBar topBar = new TopBar(username,userId);
        topPanel.add(topBar.getPanel(), BorderLayout.NORTH);
        // Top bar button action listener
        topBar.getCartButton().addActionListener(e -> new CartPage(cartService,userId,this));
        topBar.getMainButton().addActionListener(e -> {
            currentPage = 0;
            categoryDropdown.setSelectedItem("All");
            lowPriceField.setText("");
            highPriceField.setText("");
            allItems = ItemService.fetchItemsFromAPI();
            updateItems();
        });
        topBar.getExitButton().addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(ui.LoginWindow::new);
        });

        // Category combobox
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        categoryDropdown = new JComboBox<>(new String[]{"All", "Electronics", "Clothing", "Books", "Tools"});
        filterPanel.add(new JLabel("Category:"));
        filterPanel.add(categoryDropdown);

        // action for the category
        categoryDropdown.addActionListener(e -> {
            String selectedItem = (String) categoryDropdown.getSelectedItem(); // Fixed
            if (!selectedItem.equals("All")) {
                allItems = ItemService.fetchItemsByCategory(selectedItem);
            } else {
                allItems = ItemService.fetchItemsFromAPI();
            }
            currentPage = 0;
            updateItems();
        });

        // Search price area
        lowPriceField = new JTextField(6);
        highPriceField = new JTextField(6);
        filterPanel.add(new JLabel("Low Price:"));
        filterPanel.add(lowPriceField);

        filterPanel.add(new JLabel("to High Price:"));
        filterPanel.add(highPriceField);

        //search button implementation
        JButton searchButton = new JButton("Search");
        filterPanel.add(searchButton);
        searchButton.addActionListener(e -> {
            try {
                String categoryFilter = (String) categoryDropdown.getSelectedItem();
                int lowPriceFilter = Integer.parseInt(lowPriceField.getText().trim());
                int highPriceFilter = Integer.parseInt(highPriceField.getText().trim());

                if ("All".equals(categoryFilter)) {
                    allItems = ItemService.fetchItemsByPriceRange(lowPriceFilter, highPriceFilter);
                } else {
                    allItems = ItemService.fetchItemsByCategoryAndPrice(categoryFilter, lowPriceFilter, highPriceFilter);
                }

                currentPage = 0;
                updateItems();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for price.");
            }
        });

        topPanel.add(filterPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // Items grid
        contentPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        add(new JScrollPane(contentPanel), BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel(new BorderLayout());
        JPanel nav = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton prev = new JButton("< Prev"), next = new JButton("Next >");
        prev.addActionListener(e -> showPreviousPage());
        next.addActionListener(e -> showNextPage());
        nav.add(prev);
        nav.add(next);

        JButton addCart = new JButton("Add Selected Items to Cart");
        addCart.addActionListener(e -> {
            for (Component c : contentPanel.getComponents()) {
                // return all the item from current content panel
                // then check item is select or not, if it does, add to the cart
                if (c instanceof ItemPanel ip && ip.isSelected()) {
                    cartService.addToCart(ip.getItem(), ip.getSelectedQuantity());
                }
            }
            JOptionPane.showMessageDialog(this, "Selected items added to cart.");
        });

        footer.add(nav, BorderLayout.WEST);
        footer.add(addCart, BorderLayout.EAST);
        add(footer, BorderLayout.SOUTH);

        // Load items
        SwingUtilities.invokeLater(() -> {
            allItems = ItemService.fetchItemsFromAPI();
            updateItems();
        });

        setVisible(true);
    }

    private void showPreviousPage() {
        if (currentPage > 0) {
            currentPage--;
            updateItems();
        }
    }

    private void showNextPage() {
        int max = (int) Math.ceil(allItems.size() / (double) ITEMS_PER_PAGE) - 1;
        if (currentPage < max) {
            currentPage++;
            updateItems();
        }
    }

    private void updateItems() {
        contentPanel.removeAll();
        int start = currentPage * ITEMS_PER_PAGE,
                end = Math.min(start + ITEMS_PER_PAGE, allItems.size());
        for (int i = start; i < end; i++) {
            contentPanel.add(new ItemPanel(allItems.get(i)));
        }
        contentPanel.revalidate();//recalculate the layout after changes
        contentPanel.repaint(); // refresh the swing
    }

    public void refreshAllItems() {
        allItems = ItemService.fetchItemsFromAPI();
        currentPage = 0;
        updateItems();
    }
}
