package ui;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class TopBar {
    private JPanel panel;
    private JButton cartButton;
    private JButton cancelButton;
    private JButton mainButton;
    private JButton exitButton;
    private JLabel userInfoLabel;
    private UserPage userPage;

    public TopBar(String username,int userId,UserPage userPage) {
        this.userPage = userPage;
        panel = new JPanel(new BorderLayout());

        mainButton = new JButton("Main Page");
        panel.add(mainButton, BorderLayout.WEST);

        JPanel rightTopBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartButton = new JButton("Cart");
        cancelButton = new JButton("Order & Cancel");
        cancelButton.addActionListener(e -> new OrderPage(userId, userPage));
        exitButton = new JButton("Logout");

        rightTopBar.add(new JLabel("Hi, " + username + " "));
        rightTopBar.add(cartButton);
        rightTopBar.add(cancelButton);
        rightTopBar.add(exitButton);

        panel.add(rightTopBar, BorderLayout.EAST);
    }
}
