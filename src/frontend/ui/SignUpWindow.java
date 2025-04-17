package ui;

import service.AuthService;

import javax.swing.*;

public class SignUpWindow {

    public SignUpWindow() {
        JFrame frame = new JFrame("Sign Up");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 100, 25);
        frame.add(userLabel);

        JTextField userText = new JTextField();
        userText.setBounds(140, 30, 200, 25);
        frame.add(userText);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 100, 25);
        frame.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(140, 70, 200, 25);
        frame.add(passField);

        JLabel firstNameLabel = new JLabel("First Name :");
        firstNameLabel.setBounds(30, 110, 100, 25);
        frame.add(firstNameLabel);

        JTextField firstNameText = new JTextField();
        firstNameText.setBounds(140, 110, 200, 25);
        frame.add(firstNameText);

        JLabel lastNameLabel = new JLabel("Last Name :");
        lastNameLabel.setBounds(30, 150, 100, 25);
        frame.add(lastNameLabel);

        JTextField lastNameText = new JTextField();
        lastNameText.setBounds(140, 150, 200, 25);
        frame.add(lastNameText);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(30, 190, 100, 25);
        frame.add(phoneLabel);

        JTextField phoneText = new JTextField();
        phoneText.setBounds(140, 190, 200, 25);
        frame.add(phoneText);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(60, 240, 100, 25);
        frame.add(submitButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(200, 240, 100, 25);
        frame.add(cancelButton);

        cancelButton.addActionListener(e -> {
            frame.dispose();
            new LoginWindow();
        });

        submitButton.addActionListener(e -> {
            String username = userText.getText();
            String password = String.valueOf(passField.getPassword());
            String firstName = firstNameText.getText();
            String lastName = lastNameText.getText();
            String phone = phoneText.getText();

            if(username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all the fields");
            }
            String response= AuthService.signUp(username, password, firstName, lastName, phone);
            if ("Signup successful.".equalsIgnoreCase(response.trim())) {
                JOptionPane.showMessageDialog(frame, "Signup successful! Redirecting to login.");
                frame.dispose();
                new LoginWindow();
            } else {
                JOptionPane.showMessageDialog(frame, response);
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
