package com.grup31.universite_kutuphane_yonetim_sistemi.ui.student;

import com.grup31.universite_kutuphane_yonetim_sistemi.session.UserSession;
import com.grup31.universite_kutuphane_yonetim_sistemi.ui.auth.LoginWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class StudentDashboardWindow {
    private JFrame frame;

    public StudentDashboardWindow() {
        frame = new JFrame("Library Management System - Student Dashboard");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(245, 245, 245)); // Light background color

        // Welcome label
        UserSession userSession = UserSession.getInstance();
        JLabel welcomeLabel = new JLabel("Welcome, " + userSession.getUsername(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(0, 123, 255)); // Blue color for the title
        frame.add(welcomeLabel, BorderLayout.NORTH);

        // Panel for student options
        JPanel studentPanel = new JPanel(new GridLayout(2, 2, 20, 20)); // Increased spacing between buttons
        studentPanel.setBackground(new Color(245, 245, 245)); // Match the background color
        studentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding to the panel

        // Create buttons with stylish design
        JButton viewBooksButton = createStyledButton("View Books");
        JButton notificationButton = createStyledButton("Notification");
        JButton viewBorrowedBooksButton = createStyledButton("View Borrowed Books");
        JButton accountSettingsButton = createStyledButton("Account Settings");

        viewBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new ViewBook();
            }
        });
        notificationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    new NotificationWindow();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        viewBorrowedBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    new BorrowedBooks();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        accountSettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new SettingPanel();
            }
        });
        studentPanel.add(viewBooksButton);
        studentPanel.add(notificationButton);
        studentPanel.add(viewBorrowedBooksButton);
        studentPanel.add(accountSettingsButton);

        frame.add(studentPanel, BorderLayout.CENTER);

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setBackground(new Color(220, 53, 69)); // Red color for logout
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setPreferredSize(new Dimension(30, 30)); // Ukuran lebih kecil
        logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor on hover

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginWindow();
            }
        });
        frame.add(logoutButton, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setVisible(true);
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 123, 255)); // Blue color for buttons
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 50));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor on hover
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding inside buttons

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 102, 204)); // Darker blue on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 123, 255)); // Original blue color
            }
        });

        return button;
    }

}
