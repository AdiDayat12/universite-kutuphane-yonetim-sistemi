package com.grup31.universite_kutuphane_yonetim_sistemi.ui.student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SettingPanel {
    private JFrame frame;

    public SettingPanel() {
        frame = new JFrame("Student Settings");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Welcome label
        JLabel welcomeLabel = new JLabel("Student Settings", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setOpaque(true);
        welcomeLabel.setBackground(new Color(70, 130, 180)); // Steel blue background
        welcomeLabel.setForeground(Color.WHITE); // White text
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        frame.add(welcomeLabel, BorderLayout.NORTH);

        // Panel for buttons (Edit Profile and Track Record)
        JPanel settingsPanel = new JPanel(new GridLayout(2, 1, 20, 20)); // 2 buttons for main actions
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        settingsPanel.setBackground(new Color(245, 245, 245)); // Light gray background

        // Create buttons
        JButton editProfileButton = createStyledButton("Edit Profile");
        JButton trackRecordButton = createStyledButton("Track Record");

        // Action listeners for buttons
        editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new UpdateProfile();
            }
        });

        trackRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Track Record screen or dialog
                frame.dispose();  // Close current window (optional)
                try {
                    new TrackRecord();  // Assuming you have a TrackRecord class
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Add buttons to the settings panel
        settingsPanel.add(editProfileButton);
        settingsPanel.add(trackRecordButton);

        // Panel for the logout/back button (small size)
        JPanel logoutPanel = new JPanel();
        logoutPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller font size for the back/logout button
        backButton.setFocusPainted(false);
        backButton.setBackground(new Color(220, 20, 60)); // Crimson color for logout
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Lighter border
        backButton.setPreferredSize(new Dimension(80, 30)); // Smaller size for logout button
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Action listener for the logout button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                // Assuming you want to return to a previous screen or main menu
                new StudentDashboardWindow(); // Replace with the actual previous screen class
            }
        });

        // Add the small logout button to the logout panel
        logoutPanel.add(backButton);

        // Add panels to the frame
        frame.add(settingsPanel, BorderLayout.CENTER);
        frame.add(logoutPanel, BorderLayout.SOUTH); // Place logout button at the bottom

        // Make the frame visible
        frame.setVisible(true);
    }

    // Helper method to create styled buttons (larger size)
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

}
