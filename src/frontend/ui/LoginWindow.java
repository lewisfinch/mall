package ui;

import model.User;
import service.AuthService;

import javax.swing.*;

/*
The LoginWindow serves as the entry point for user authentication.
From this window, the user can:
    1. Log in to their account. If authentication is successful, the user
       will be redirected to the main application window (e.g., shopping interface).

    2. Register a new account by clicking the "Sign Up" button, which opens the
       SignUpWindow for user registration.

    3. Recover their account by clicking the "Forgot Password" button, which opens
      the ForgetPasswordWindow. (Note: Password recovery is not implemented in this project;
      the button simply opens the placeholder window.)
*/
public class LoginWindow {

    public LoginWindow() {
        JFrame frame = new JFrame("Login");
        frame.setSize(300, 350);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 50, 80, 25);
        frame.add(userLabel);

        JTextField userText = new JTextField();
        userText.setBounds(120, 50, 160, 25);
        frame.add(userText);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 90, 80, 25);
        frame.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(120, 90, 160, 25);
        frame.add(passField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 140, 100, 25);
        frame.add(loginButton);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(160, 140, 100, 25);
        frame.add(signUpButton);

        JButton forgetPassButton = new JButton("Forgot Password");
        forgetPassButton.setBounds(50, 180, 210, 25);
        frame.add(forgetPassButton);

        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passField.getPassword());
            User user = new User(username, password);
            String response = AuthService.login(user);
            JOptionPane.showMessageDialog(frame, response);
        });

        signUpButton.addActionListener(e -> {
            frame.dispose();
            new SignUpWindow();
        });

        forgetPassButton.addActionListener(e -> {
            frame.dispose();
            new ForgetPasswordWindow();
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
