package com.grup31.universite_kutuphane_yonetim_sistemi.ui.admin;

import com.grup31.universite_kutuphane_yonetim_sistemi.session.BookSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookWindow {
    private JFrame frame;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField publisherField;
    private JTextField publicationYearField;
    private JTextField editionField;
    private JTextField pageField;
    private JTextField stockField;
    private JLabel messageLabel;

    public AddBookWindow() {
        frame = new JFrame("Admin - Add Book");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title label
        JLabel titleLabel = new JLabel("Add New Book");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        // Name Field
        JLabel titleBookLabel = new JLabel("Title:");
        titleField = new JTextField(20);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(titleBookLabel, gbc);
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        // Username Field
        JLabel authorLabel = new JLabel("Author:");
        authorField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(authorLabel, gbc);
        gbc.gridx = 1;
        panel.add(authorField, gbc);

        // Publisher Field
        JLabel publisherLabel = new JLabel("Publisher:");
        publisherField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(publisherLabel, gbc);
        gbc.gridx = 1;
        panel.add(publisherField, gbc);

        // Publication Year Field
        JLabel publicationYearLabel = new JLabel("Publication Year:");
        publicationYearField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(publicationYearLabel, gbc);
        gbc.gridx = 1;
        panel.add(publicationYearField, gbc);

        // Edition Field
        JLabel editionLabel = new JLabel("Edition:");
        editionField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(editionLabel, gbc);
        gbc.gridx = 1;
        panel.add(editionField, gbc);

        // Page Field
        JLabel pageLabel = new JLabel("Page:");
        pageField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(pageLabel, gbc);
        gbc.gridx = 1;
        panel.add(pageField, gbc);

        // Stock Field
        JLabel stockLabel = new JLabel("Stock:");
        stockField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(stockLabel, gbc);
        gbc.gridx = 1;
        panel.add(stockField, gbc);

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

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new ManageBooksWindow();
            }
        });
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Action listener for Add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String title = titleField.getText();
                    String author = authorField.getText();
                    String publisher = publisherField.getText();
                    int publicationYear = Integer.parseInt(publicationYearField.getText());
                    int edition = Integer.parseInt(editionField.getText());
                    int page = Integer.parseInt(pageField.getText());
                    int stock = Integer.parseInt(stockField.getText());

                    BookSession bookSession = BookSession.getInstance();

                    String resultMessage = bookSession.addBook(title, author, publisher,
                            publicationYear, edition, page, stock);
                    if (resultMessage.equalsIgnoreCase("Added Successfully")) {
                        messageLabel.setText(resultMessage);
                        messageLabel.setForeground(new Color(0, 128, 0));
                    } else {
                        messageLabel.setText(resultMessage);
                        messageLabel.setForeground(new Color(255, 0, 0));
                    }
                } catch (NumberFormatException ex) {
                    messageLabel.setText("Invalid input format. Please check the fields.");
                    messageLabel.setForeground(new Color(255, 0, 0));
                }
            }
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

}
