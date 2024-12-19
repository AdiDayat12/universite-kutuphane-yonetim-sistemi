package com.grup31.universite_kutuphane_yonetim_sistemi.ui.student;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.AvailableState;
import com.grup31.universite_kutuphane_yonetim_sistemi.book.Book;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.*;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.BookService;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.LoanDetailService;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.LoanService;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.StudentService;
import com.grup31.universite_kutuphane_yonetim_sistemi.session.UserSession;
import com.grup31.universite_kutuphane_yonetim_sistemi.transaction.LoanDetail;
import com.grup31.universite_kutuphane_yonetim_sistemi.util.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TrackRecord {
    private JFrame frame;
    private JTable booksTable;
    private LoanDetailService loanDetailService;
    private DefaultTableModel tableModel;

    public TrackRecord() throws SQLException {
        frame = new JFrame("View All Records");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("View Borrowing and Returning Books", SwingConstants.CENTER);
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
        String[] columnNames = {"No.", "Book Title", "Author", "Borrowing Date", "Due Date", "Returning Date"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        UserSession userSession = UserSession.getInstance();

        List<LoanDetail> loanDetails = loanDetailService.getAllLoansByUserId(userSession.getCurrentUser().getId());

        // Populate table model
        for (int i = 0; i < loanDetails.size(); i++) {
            LoanDetail loanDetail = loanDetails.get(i);
            tableModel.addRow(new Object[]{
                    i + 1,
                    loanDetail.getBookTitle(),
                    loanDetail.getBookAuthor(),
                    loanDetail.getLoanDate(),
                    loanDetail.getDueDate(),
                    loanDetail.getReturningDate()
            });
        }

        // Create JTable with model
        booksTable = new JTable(tableModel);
        booksTable.setRowHeight(30);
        // Set column widths
        booksTable.getColumnModel().getColumn(0).setPreferredWidth(50); // No. column
        booksTable.getColumnModel().getColumn(1).setPreferredWidth(400); // Book Title column
        booksTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Author column
        booksTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Publisher column
        booksTable.getColumnModel().getColumn(4).setPreferredWidth(150); // Publication Year column
        booksTable.getColumnModel().getColumn(5).setPreferredWidth(150); // Publication Year column


        JScrollPane scrollPane = new JScrollPane(booksTable);
        booksTable.setFillsViewportHeight(true);


        // Panel for action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Close button
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(220, 20, 60)); // Crimson red
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new SettingPanel();
            }
        });
        actionPanel.add(backButton);
        frame.add(actionPanel, BorderLayout.SOUTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
