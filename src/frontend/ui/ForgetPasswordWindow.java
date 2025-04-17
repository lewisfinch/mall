package ui;

import javax.swing.*;

public class ForgetPasswordWindow {

    public ForgetPasswordWindow() {
        JFrame frame = new JFrame("Forgot Password");
        frame.setSize(400, 220);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 100, 25);
        frame.add(userLabel);

        JTextField userText = new JTextField();
        userText.setBounds(140, 30, 200, 25);
        frame.add(userText);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 70, 100, 25);
        frame.add(emailLabel);

        JTextField emailText = new JTextField();
        emailText.setBounds(140, 70, 200, 25);
        frame.add(emailText);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(60, 130, 100, 25);
        frame.add(submitButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(200, 130, 100, 25);
        frame.add(cancelButton);

        cancelButton.addActionListener(e -> {
            frame.dispose();
            new LoginWindow();
        });

        submitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Password reset requested! (not implemented yet)");
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
