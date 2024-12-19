package com.grup31.universite_kutuphane_yonetim_sistemi.ui.auth;

import com.grup31.universite_kutuphane_yonetim_sistemi.session.UserSession;
import com.grup31.universite_kutuphane_yonetim_sistemi.ui.admin.AdminDashboardWindow;
import com.grup31.universite_kutuphane_yonetim_sistemi.ui.student.StudentDashboardWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;

    public LoginWindow() {
        // Initialize the JFrame
        frame = new JFrame("Library Management System - Login");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(245, 245, 245));

        // Panel for the login form with rounded corners and shadow
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setPreferredSize(new Dimension(300, 250));
        loginPanel.setBackground(new Color(245, 245, 245));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title Label
        JLabel titleLabel = new JLabel("Login to Library System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 50));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(titleLabel, gbc);

        // Username Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        // Password Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(120, 35));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 10, 10); // Add top and bottom margin for spacing
        loginPanel.add(loginButton, gbc);

// Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(new Color(0, 123, 255));
        registerButton.setForeground(Color.WHITE);
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setPreferredSize(new Dimension(120, 35));

// Adjust placement so the buttons are spaced apart
        gbc.gridx = 0; // Reset to first column
        gbc.gridy = 4; // Move to the next row for better separation
        loginPanel.add(registerButton, gbc);


        // Message Label for feedback
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        messageLabel.setForeground(new Color(255, 0, 0)); // Red by default
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        loginPanel.add(messageLabel, gbc);

        // Action listener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Attempt to login using UserSession
                UserSession userSession = UserSession.getInstance();
                if (userSession.login(username, password)) {
                    messageLabel.setText("Login successful!");
                    messageLabel.setForeground(new Color(0, 128, 0)); // Green for success
                    frame.dispose(); // Close the login window

                    // Redirect based on role
                    if ("ADMIN".equals(userSession.getRole())) {
                        new AdminDashboardWindow(); // Open admin dashboard
                    } else if ("STUDENT".equals(userSession.getRole())) {
                        new StudentDashboardWindow(); // Open student dashboard
                    }
                } else {
                    messageLabel = new JLabel("Invalid credentials, try again.", SwingConstants.CENTER);
                    messageLabel.setForeground(new Color(255, 0, 0)); // Red for error
                    messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));

                    // Position the message label below the register button
                    gbc.gridx = 0;
                    gbc.gridy = 5; // Set to 5, assuming register button is at row 4
                    gbc.insets = new Insets(10, 10, 10, 10); // Add margin around the label for better spacing

                    loginPanel.add(messageLabel, gbc);
                    loginPanel.revalidate(); // Revalidate layout after adding the new label
                    loginPanel.repaint();


                }
            }
        });

        // Register button action to open the Register window
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close login window
                new RegisterWindow(); // Open register window
            }
        });

        // Logout button
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        footerPanel.setBackground(Color.WHITE);

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(100, 35)); // Set a smaller size
        exitButton.setBackground(new Color(220, 20, 60)); // Crimson color
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();  // Close the current window
                System.exit(0);    // Exit the application
            }

        });

        footerPanel.add(exitButton);
        frame.add(footerPanel, BorderLayout.SOUTH);

        // Add the login panel to the frame
        frame.add(loginPanel, BorderLayout.CENTER);

        // Center the window on the screen
        frame.setLocationRelativeTo(null);

        // Show the login window
        frame.setVisible(true);
    }
}
