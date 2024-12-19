package com.grup31.universite_kutuphane_yonetim_sistemi.ui.admin;

import com.grup31.universite_kutuphane_yonetim_sistemi.session.UserSession;
import com.grup31.universite_kutuphane_yonetim_sistemi.ui.auth.LoginWindow;
import com.grup31.universite_kutuphane_yonetim_sistemi.ui.student.UpdateAdminProfile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminDashboardWindow {
    private JFrame frame;

    public AdminDashboardWindow() {
        frame = new JFrame("Library Management System - Admin Dashboard");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        UserSession userSession = UserSession.getInstance();
        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome, Admin ", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setOpaque(true);
        welcomeLabel.setBackground(new Color(70, 130, 180));
        welcomeLabel.setForeground(Color.WHITE); // White text
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        frame.add(welcomeLabel, BorderLayout.NORTH);

        // Panel for admin buttons
        JPanel adminPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        adminPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        adminPanel.setBackground(new Color(245, 245, 245));

        JButton manageBooksButton = createStyledButton("Manage Books");
        JButton manageUsersButton = createStyledButton("Manage Users");
        JButton viewReportsButton = createStyledButton("View Reports");
        JButton settingsButton = createStyledButton("Settings");

        adminPanel.add(manageBooksButton);
        adminPanel.add(manageUsersButton);
        adminPanel.add(viewReportsButton);
        adminPanel.add(settingsButton);

        frame.add(adminPanel, BorderLayout.CENTER);

        // Logout button
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        footerPanel.setBackground(Color.WHITE);

        JButton logoutButton = createSmallLogoutButton("Back"); // Small logout button

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginWindow();
            }
        });

        footerPanel.add(logoutButton);
        frame.add(footerPanel, BorderLayout.SOUTH);

        // Button actions to open new windows
        manageBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current window
                new ManageBooksWindow(); // Open Manage Books window
            }
        });

        manageUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current window
                try {
                    new ManageUsersWindow(); // Open Manage Users window (You need to create this window)
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        viewReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current window
                try {
                    new ViewReport();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current window
                new UpdateAdminProfile(); // Open Settings window (You need to create this window)
            }
        });

        frame.setVisible(true);
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setFocusPainted(false);
        button.setBackground(new Color(100, 149, 237)); // Cornflower blue
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Helper method to create a small logout button (Back button)
    private JButton createSmallLogoutButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14)); // Smaller font size for the back/logout button
        button.setFocusPainted(false);
        button.setBackground(new Color(220, 20, 60)); // Red color for logout
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Lighter border
        button.setPreferredSize(new Dimension(100, 35)); // Set a smaller size
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

}
