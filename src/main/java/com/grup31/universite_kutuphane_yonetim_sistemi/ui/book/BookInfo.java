package com.grup31.universite_kutuphane_yonetim_sistemi.ui.book;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.AvailableState;
import com.grup31.universite_kutuphane_yonetim_sistemi.book.Book;

import javax.swing.*;
import java.awt.*;

public class BookInfo {
    private JFrame infoFrame;

    public BookInfo(Book book) {
        // Initialize the frame
        infoFrame = new JFrame("Book Information - " + book.getTitle());
        infoFrame.setSize(500, 400);
        infoFrame.setLayout(new BorderLayout());
        infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Title Label
        JLabel titleLabel = new JLabel("Book Information", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(70, 130, 180)); // Steel blue background
        titleLabel.setForeground(Color.WHITE); // White text
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoFrame.add(titleLabel, BorderLayout.NORTH);

        // Panel for book details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(8, 1, 10, 10));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        detailsPanel.add(new JLabel("Title: " + book.getTitle()));
        detailsPanel.add(new JLabel("Author: " + book.getAuthor()));
        detailsPanel.add(new JLabel("Publisher: " + book.getPublisher()));
        detailsPanel.add(new JLabel("Publisher Year: " + book.getPublicationYear()));
        detailsPanel.add(new JLabel("Available Copies: " + book.getAvailableBook()));
        detailsPanel.add(new JLabel("Borrowed Copies: " + book.getBorrowedBook()));
        detailsPanel.add(new JLabel("Lost Copies: " + book.getLostBook()));

        String status = book.getBookState() instanceof AvailableState ? "Available" : "Out of Stock";
        detailsPanel.add(new JLabel("Status: " + status));

        infoFrame.add(detailsPanel, BorderLayout.CENTER);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(new Color(220, 20, 60)); // Crimson red
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(e -> infoFrame.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        infoFrame.add(buttonPanel, BorderLayout.SOUTH);

        infoFrame.setVisible(true);
    }
}
