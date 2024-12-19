package com.grup31.universite_kutuphane_yonetim_sistemi.ui.student;

import com.grup31.universite_kutuphane_yonetim_sistemi.session.UserSession;
import com.grup31.universite_kutuphane_yonetim_sistemi.ui.admin.AdminDashboardWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdateAdminProfile {
    private JFrame frame;
    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField newPasswordField;
    private JTextField emailField;
    private JLabel messageLabel;

    public UpdateAdminProfile() {
        frame = new JFrame("Library Management System - Update Profile");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title label
        JLabel titleLabel = new JLabel("Update Profile");
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

        // New Password Field
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordField = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(newPasswordLabel, gbc);
        gbc.gridx = 1;
        panel.add(newPasswordField, gbc);

        // Email Field
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        // Message Label
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(new Color(255, 0, 0));
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(messageLabel, gbc);

        // Button Panel for Back and Update Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton updateButton = new JButton("Update");
        updateButton.setBackground(new Color(0, 123, 255));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.setFocusPainted(false);
        updateButton.setBorderPainted(false);
        buttonPanel.add(updateButton);

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(220, 20, 60)); // Crimson red
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Action listener for Update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserSession userSession = UserSession.getInstance();
                int id;
                try {
                    id =  userSession.getCurrentUser().getId();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String name = nameField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                String email = emailField.getText();

                try {
                    String message = userSession.updateAdmin(id, name, username, password, newPassword, email);
                    if (message.equalsIgnoreCase("Updated successfully!")) {
                        messageLabel.setText(message);
                        messageLabel.setForeground(new Color(0, 128, 0)); // Green color for success
                    } else {
                        messageLabel.setText(message);
                        messageLabel.setForeground(new Color(255, 0, 0));
                    }
                } catch (SQLException ex) {
                    messageLabel.setText("An error occurred while updating. Please try again.");
                    messageLabel.setForeground(new Color(255, 0, 0)); // Red color for errors
                    ex.printStackTrace();
                }
            }
        });

        // Action listener for Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new AdminDashboardWindow();
            }
        });
    }

}
