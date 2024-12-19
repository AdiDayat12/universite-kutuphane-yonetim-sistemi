package com.grup31.universite_kutuphane_yonetim_sistemi.ui.auth;

import com.grup31.universite_kutuphane_yonetim_sistemi.session.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

public class RegisterWindow {
    private JFrame frame;
    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JComboBox<String> roleComboBox;
    private JTextField adminCodeField;
    private JLabel messageLabel;

    public RegisterWindow() {
        frame = new JFrame("Library Management System - Register");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title label
        JLabel titleLabel = new JLabel("Register New Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        // Name Field
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Username Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Password Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Email Field
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        // Role Selection
        JLabel roleLabel = new JLabel("Role:");
        roleComboBox = new JComboBox<>(new String[]{"USER", "ADMIN"});
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(roleLabel, gbc);
        gbc.gridx = 1;
        panel.add(roleComboBox, gbc);

        // Admin Code Field (initially hidden)
        JLabel adminCodeLabel = new JLabel("Admin Code:");
        adminCodeField = new JTextField(20);
        adminCodeField.setVisible(false); // Initially not visible
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(adminCodeLabel, gbc);
        gbc.gridx = 1;
        panel.add(adminCodeField, gbc);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(0, 123, 255));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(registerButton, gbc);
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.addActionListener(ev -> {
            frame.dispose();
            new LoginWindow(); // Return to login screen
        });
        gbc.gridy = 8;
        panel.add(backButton, gbc);

        // Message Label
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(new Color(255, 0, 0));
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        panel.add(messageLabel, gbc);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);


        // Action listener for Register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();
                String role = (String) roleComboBox.getSelectedItem();
                String adminCode = adminCodeField.isVisible() ? adminCodeField.getText() : null; // Only fetch if visible

                UserSession userSession = UserSession.getInstance();

                try {
                    // Call register method and get message
                    String resultMessage = userSession.register(name, username, password, email, role, adminCode);

                    // Handle the result message
                    if (resultMessage.equals("Registration successful!")) {
                        messageLabel.setText(resultMessage);
                        messageLabel.setForeground(new Color(0, 128, 0)); // Green color for success
                        panel.remove(backButton);

                        // Add Login Button after successful registration
                        JButton loginButton = new JButton("Login");
                        loginButton.setBackground(new Color(0, 123, 255));
                        loginButton.setForeground(Color.WHITE);
                        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
                        loginButton.setFocusPainted(false);
                        loginButton.setBorderPainted(false);
                        loginButton.addActionListener(ev -> {
                            frame.dispose();
                            new LoginWindow(); // Return to login screen
                        });
                        gbc.gridy = 8;
                        panel.add(loginButton, gbc);
                        frame.validate();
                        frame.repaint();
                    } else {
                        messageLabel.setText(resultMessage);
                        messageLabel.setForeground(new Color(255, 0, 0)); // Red color for errors
                    }
                } catch (SQLException ex) {
                    messageLabel.setText("An error occurred while registering. Please try again.");
                    messageLabel.setForeground(new Color(255, 0, 0)); // Red color for errors
                    ex.printStackTrace();
                }
            }
        });


        // Item listener for role selection (to show/hide admin code field)
        roleComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (roleComboBox.getSelectedItem().equals("ADMIN")) {
                    adminCodeField.setVisible(true); // Show Admin Code field
                } else {
                    adminCodeField.setVisible(false); // Hide Admin Code field
                }
            }
        });

        // Add panel to frame
        frame.add(panel, BorderLayout.CENTER);

        // Set window to be visible
        frame.setVisible(true);
    }
}
