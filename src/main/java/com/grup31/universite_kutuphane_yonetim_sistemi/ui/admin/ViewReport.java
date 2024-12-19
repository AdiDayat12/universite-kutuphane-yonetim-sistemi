package com.grup31.universite_kutuphane_yonetim_sistemi.ui.admin;

import com.grup31.universite_kutuphane_yonetim_sistemi.dao.*;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.LoanDetailService;
import com.grup31.universite_kutuphane_yonetim_sistemi.transaction.LoanDetail;
import com.grup31.universite_kutuphane_yonetim_sistemi.util.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class ViewReport {
    private JFrame frame;
    private JTable booksTable;
    private LoanDetailService loanDetailService;
    private DefaultTableModel tableModel;

    public ViewReport () throws SQLException {
        frame = new JFrame("View All Borrowed Books");
        frame.setSize(1040, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("View Borrowed Books", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(70, 130, 180)); // Steel blue background
        titleLabel.setForeground(Color.WHITE); // White text
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Data from database
        Connection connection = DBConnection.getInstance().getConnection();
        LoanDetailDAO loanDetailDAO = new LoanDetailImpl(connection);
        loanDetailService = new LoanDetailService(loanDetailDAO);

        // Table model for books
        String[] columnNames = {"Loan ID", "UserName", "Book Title", "Author", "Borrowing Date", "Due Date", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        java.util.List<LoanDetail> loanDetails = loanDetailService.getAllLoans();

        // Populate table model
        for (int i = 0; i < loanDetails.size(); i++) {
            LoanDetail loanDetail = loanDetails.get(i);
            tableModel.addRow(new Object[]{
                    loanDetail.getLoanId(),
                    loanDetail.getStudentName(),
                    loanDetail.getBookTitle(),
                    loanDetail.getBookAuthor(),
                    loanDetail.getLoanDate(),
                    loanDetail.getDueDate(),
                    loanDetail.isReturned() ? "Returned" : "Not Returned"
            });
        }

        // Create JTable with model
        booksTable = new JTable(tableModel);
        booksTable.setRowHeight(30);
        // Set column widths
        booksTable.getColumnModel().getColumn(0).setPreferredWidth(70); // No. column
        booksTable.getColumnModel().getColumn(1).setPreferredWidth(250); // Book Title column
        booksTable.getColumnModel().getColumn(2).setPreferredWidth(400); // Author column
        booksTable.getColumnModel().getColumn(3).setPreferredWidth(300); // Publisher column
        booksTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Publication Year column
        booksTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Publication Year column
        booksTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Publication Year column


        JScrollPane scrollPane = new JScrollPane(booksTable);
        booksTable.setFillsViewportHeight(true);
        frame.add(scrollPane, BorderLayout.CENTER);


        // Logout button
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        footerPanel.setBackground(Color.WHITE);

        JButton logoutButton = new JButton("Back");
        logoutButton.setPreferredSize(new Dimension(100, 35)); // Set a smaller size
        logoutButton.setBackground(new Color(220, 20, 60)); // Crimson color
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new AdminDashboardWindow();
            }
        });

        footerPanel.add(logoutButton);
        frame.add(footerPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

}
