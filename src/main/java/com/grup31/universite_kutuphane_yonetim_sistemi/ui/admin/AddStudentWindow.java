package com.grup31.universite_kutuphane_yonetim_sistemi.ui.admin;

import com.grup31.universite_kutuphane_yonetim_sistemi.session.BookSession;
import com.grup31.universite_kutuphane_yonetim_sistemi.session.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddStudentWindow {
    private JFrame frame;
    private JTextField nameField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField emailField;
    private JLabel messageLabel;

    public AddStudentWindow() {
        frame = new JFrame("Admin - Add User");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title label
        JLabel titleLabel = new JLabel("Add New User");
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

        // Publisher Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Publication Year Field
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);


        // Add Button
        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(addButton, gbc);

        // Message Label
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(new Color(255, 0, 0));
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        gbc.gridx = 0;
        gbc.gridy = 9; // Diposisikan di bawah tombol "Add"
        gbc.gridwidth = 2;
        panel.add(messageLabel, gbc);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(192, 192, 192));
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 10; // Diposisikan di bawah message label
        panel.add(backButton, gbc);


        // Action listener for Add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    String username = usernameField.getText();
                    String password = passwordField.getText();
                    String email = emailField.getText();
                    String role = "user";


                    UserSession userSession = UserSession.getInstance();
                    String resultMessage = userSession.register(name, username, password, email, role, null);
                    if (resultMessage.equalsIgnoreCase("Registration successful!")) {
                        messageLabel.setText(resultMessage);
                        messageLabel.setForeground(new Color(0, 128, 0));
                    } else {
                        messageLabel.setText(resultMessage);
                        messageLabel.setForeground(new Color(255, 0, 0));
                    }
                } catch (NumberFormatException | SQLException ex) {
                    messageLabel.setText("An error occurred: " + ex.getMessage());
                    messageLabel.setForeground(new Color(255, 0, 0));
                }

            }
        });

        // Back button listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    new ManageUsersWindow();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);




        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

}
