package com.grup31.universite_kutuphane_yonetim_sistemi.ui.admin;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Book;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.BookDAO;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.BookDAOImpl;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.BookService;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.strategy.*;
import com.grup31.universite_kutuphane_yonetim_sistemi.ui.book.BookInfo;
import com.grup31.universite_kutuphane_yonetim_sistemi.ui.feature.SearchFeature;
import com.grup31.universite_kutuphane_yonetim_sistemi.util.DBConnection;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class ManageBooksWindow {
    private JFrame frame;
    private JTable booksTable;
    private int selectedRow = -1; // To track selected row
    private BookService bookService;
    private DefaultTableModel tableModel;

    public ManageBooksWindow() {
        frame = new JFrame("Library Management System - Manage Books");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Manage Books", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(70, 130, 180)); // Steel blue background
        titleLabel.setForeground(Color.WHITE); // White text
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Data from database
        Connection connection = DBConnection.getInstance().getConnection();
        BookDAO bookDAO = new BookDAOImpl(connection);
        this.bookService = new BookService(bookDAO);

        List<Book> bookList = bookService.getAllBooks();

        // Table model for books
        String[] columnNames = {"No.", "Book Title", "Available", "Borrowed", "Lost"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        // Populate table model
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            tableModel.addRow(new Object[]{
                    i + 1,
                    book.getTitle(),
                    book.getAvailableBook(),
                    book.getBorrowedBook(),
                    book.getLostBook()
            });
        }

        // Create JTable with model
        booksTable = new JTable(tableModel);
        booksTable.setRowHeight(30);

        // Set column widths
        booksTable.getColumnModel().getColumn(0).setPreferredWidth(50); // No. column
        booksTable.getColumnModel().getColumn(1).setPreferredWidth(400); // Book Title column
        booksTable.getColumnModel().getColumn(2).setPreferredWidth(50); // Available column
        booksTable.getColumnModel().getColumn(3).setPreferredWidth(50); // Borrowed column
        booksTable.getColumnModel().getColumn(4).setPreferredWidth(50); // Lost column

        JScrollPane scrollPane = new JScrollPane(booksTable);
        booksTable.setFillsViewportHeight(true);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add listener to handle row selection
        booksTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRow = booksTable.getSelectedRow();
            }
        });

        // Panel for action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton infoButton = new JButton("More Info");
        infoButton.setBackground(new Color(144, 238, 144)); // Light green
        infoButton.setForeground(Color.BLACK);
        infoButton.setFocusPainted(false);
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRow != -1) {
                    // Ambil judul buku dari baris yang dipilih
                    String bookTitle = (String) booksTable.getValueAt(selectedRow, 1);

                    // Gunakan service untuk mendapatkan detail lengkap buku berdasarkan judul
                    Book book = bookService.findBookByTitle(bookTitle);

                    if (book != null) {
                        // Buka jendela BookInfo dengan detail buku
                        new BookInfo(book);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Book details not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a row first!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add
        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(50, 205, 50));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new AddBookWindow();
            }
        });
        // Edit
        JButton editButton = new JButton("Edit");
        editButton.setBackground(new Color(70, 130, 180));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRow != -1) {
                    String bookTitle = (String) booksTable.getValueAt(selectedRow, 1); // Column 1 is book title
                    Book book = bookService.findBookByTitle(bookTitle);
                        frame.dispose();
                        new UpdateBookWindow(book);
                        tableModel.removeRow(selectedRow); // Remove row from table
                        selectedRow = -1; // Reset selected row

                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a row first!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Delete
        JButton deleteButton = new JButton("Delete");
        deleteButton.setBackground(new Color(220, 20, 60)); // Crimson red
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRow != -1) {
                    String bookTitle = (String) booksTable.getValueAt(selectedRow, 1); // Column 1 is book title
                    int response = JOptionPane.showConfirmDialog(frame,
                            "Are you sure you want to delete the book \"" + bookTitle + "\"?",
                            "Delete Book",
                            JOptionPane.YES_NO_OPTION);

                    if (response == JOptionPane.YES_OPTION) {
                        bookService.deleteBook(bookTitle); // Delete book from database
                        tableModel.removeRow(selectedRow); // Remove row from table
                        selectedRow = -1; // Reset selected row
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a row first!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Back
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(169, 169, 169)); // Dark gray
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current window
                new AdminDashboardWindow(); // Open Admin Dashboard
            }
        });

        JButton searchButton = SearchFeature.addSearchButton(frame, tableModel, bookDAO);

// Add the search button to the action panel
        actionPanel.add(searchButton);


        // Add buttons to the action panel
        actionPanel.add(infoButton);
        actionPanel.add(addButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);
        actionPanel.add(backButton);

        frame.add(actionPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }



}
