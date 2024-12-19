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
import com.grup31.universite_kutuphane_yonetim_sistemi.transaction.LoanManager;
import com.grup31.universite_kutuphane_yonetim_sistemi.user.AbstractUser;
import com.grup31.universite_kutuphane_yonetim_sistemi.util.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BorrowedBooks {
    private JFrame frame;
    private JTable booksTable;
    private int selectedRow = -1; // To track selected row
    private LoanDetailService loanDetailService;
    private LoanService loanService;
    private StudentService studentService;
    private DefaultTableModel tableModel;
    private LoanManager loanManager;
    private BookService bookService;

    public BorrowedBooks() throws SQLException {
        frame = new JFrame("View All Borrowed Books");
        frame.setSize(800, 600);
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

        LoanDAO loanDAO = new LoanDAOImpl(connection);
        loanService = new LoanService(loanDAO);

        StudentDAO studentDAO = new StudentDAOImpl(connection);
        studentService = new StudentService(studentDAO);

        BookDAO bookDAO = new BookDAOImpl(connection);
        bookService = new BookService(bookDAO);

        loanManager = new LoanManager();

        // Table model for books
        String[] columnNames = {"No.", "Book Title", "Author", "Borrowing Date", "Due Date"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        UserSession userSession = UserSession.getInstance();

        List<LoanDetail> loanDetails = loanDetailService.getAllLoansByUserId(userSession.getCurrentUser().getId());
        List<LoanDetail> loanDetailList = new ArrayList<>();

        // Populate table model
        for (int i = 0; i < loanDetails.size(); i++) {
            LoanDetail loanDetail = loanDetails.get(i);
            if (!loanDetail.isReturned()){
                tableModel.addRow(new Object[]{
                        i + 1,
                        loanDetail.getBookTitle(),
                        loanDetail.getBookAuthor(),
                        loanDetail.getLoanDate(),
                        loanDetail.getDueDate()
                });
                loanDetailList.add(loanDetail);
            }
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


        JScrollPane scrollPane = new JScrollPane(booksTable);
        booksTable.setFillsViewportHeight(true);


        // Panel for action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton returnButton = new JButton("Return");
        returnButton.setBackground(new Color(70, 130, 180));
        returnButton.setForeground(Color.BLACK);
        returnButton.setFocusPainted(false);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pastikan selectedRow valid
                int selectedRow = booksTable.getSelectedRow();
                if (selectedRow != -1) {
                    LoanDetail selectedLoan = loanDetailList.get(selectedRow);
                    String bookTitle = selectedLoan.getBookTitle(); // Column 1 is book title
                    Book book = bookService.findBookByTitle(bookTitle);
                    System.out.println("stock sebelum dikembalikan kelas borrowed book: " + book.getAvailableBook());
                    int response = JOptionPane.showConfirmDialog(frame,
                            "Are you sure you want to return the book \"" + bookTitle + "\"?",
                            "Borrow Book",
                            JOptionPane.YES_NO_OPTION);

                    if (response == JOptionPane.YES_OPTION) {
                        loanManager.returnLoan(book);
                        bookService.updateBookCount(book.getTitle(), 1, -1, 0);
                        System.out.println("stock setelah dikembalikan borrowed book: " + book.getAvailableBook());
                        int loanId =  selectedLoan.getLoanId();
                        loanService.updateStatus(loanId, true);
                        LocalDate dueDate = selectedLoan.getDueDate();
                        LocalDate today = LocalDate.now();
                        loanService.insertReturnDate(loanId, today);
                        long result = ChronoUnit.DAYS.between(dueDate, today);

                        // Fine is 30 TL per day
                        if (result > 0) {
                            double fine = 30 * result; // Calculate fine
                            try {
                                studentService.updateFine(userSession.getCurrentUser().getId(), fine);
                                JOptionPane.showMessageDialog(frame,
                                        "You are late in returning the book. You have a fine of " + fine + " TL.",
                                        "Late Return",
                                        JOptionPane.WARNING_MESSAGE);
                            } catch (SQLException ex) {
                                throw new RuntimeException("Error updating fine: " + ex.getMessage(), ex);
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame,
                                    "Thank you for returning the book on time!",
                                    "Return Confirmation",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }

        });
        // Lost button
        JButton lostButton = new JButton("Lost");
        lostButton.setBackground(new Color(220, 20, 60));
        lostButton.setForeground(Color.WHITE);
        lostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = booksTable.getSelectedRow();
                if (selectedRow != -1) {
                    LoanDetail selectedLoan = loanDetailList.get(selectedRow);
                    String bookTitle = selectedLoan.getBookTitle(); // Column 1 is book title
                    Book book = bookService.findBookByTitle(bookTitle);
                    System.out.println("buku sebelum dikembalikan lose book: " + book.getAvailableBook());
                    loanManager.lostBookReport(book);
                    bookService.updateBookCount(book.getTitle(), -1, -1, 1);
                    System.out.println("buku setela dikembalikan lose book: " + book.getAvailableBook());
                    double fine = 30;
                    int loanId =  selectedLoan.getLoanId();
                    loanService.updateStatus(loanId, true);
                    try {
                        studentService.updateFine(userSession.getCurrentUser().getId(), fine);
                        JOptionPane.showMessageDialog(frame,
                                "You are late in losing the book. You have a fine of " + fine + " TL.",
                                "Lost Book",
                                JOptionPane.WARNING_MESSAGE);
                    } catch (SQLException ex) {
                        throw new RuntimeException("Error updating fine: " + ex.getMessage(), ex);
                    }
                    }
            }
        });


        // Close button
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(169, 169, 169)); // Crimson red
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new StudentDashboardWindow();
            }
        });


        actionPanel.add(returnButton);
        actionPanel.add(lostButton);
        actionPanel.add(backButton);
        frame.add(actionPanel, BorderLayout.SOUTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
