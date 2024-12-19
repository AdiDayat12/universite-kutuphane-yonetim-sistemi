package com.grup31.universite_kutuphane_yonetim_sistemi.ui.student;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.AvailableState;
import com.grup31.universite_kutuphane_yonetim_sistemi.book.Book;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.*;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.BookService;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.LoanService;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.NotificationService;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.StudentService;
import com.grup31.universite_kutuphane_yonetim_sistemi.session.UserSession;
import com.grup31.universite_kutuphane_yonetim_sistemi.transaction.LoanManager;
import com.grup31.universite_kutuphane_yonetim_sistemi.ui.feature.SearchFeature;
import com.grup31.universite_kutuphane_yonetim_sistemi.user.AbstractUser;
import com.grup31.universite_kutuphane_yonetim_sistemi.util.DBConnection;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ViewBook {
    private JFrame frame;
    private JTable booksTable;
    private int selectedRow = -1; // To track selected row
    private BookService bookService;
    private LoanService loanService;
    private StudentService studentService;
    private NotificationService notificationService;
    private DefaultTableModel tableModel;
    private LoanManager loanManager;

    public ViewBook() {
        Connection connection = DBConnection.getInstance().getConnection();
        BookDAO bookDAO = new BookDAOImpl(connection);
        this.bookService = new BookService(bookDAO);

        LoanDAO loanDAO = new LoanDAOImpl(connection);
        this.loanService = new LoanService(loanDAO);

        StudentDAO studentDAO = new StudentDAOImpl(connection);
        this.studentService = new StudentService(studentDAO);

        NotificationDAO notificationDAO = new NotificationDAOImpl(connection);
        this.notificationService = new NotificationService(notificationDAO);

        loanManager = new LoanManager();

        frame = new JFrame("View All Books");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("View Books", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(70, 130, 180)); // Steel blue background
        titleLabel.setForeground(Color.WHITE); // White text
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Data from database
        List<Book> bookList = bookService.getAllBooks();

        // Table model for books
        String[] columnNames = {"No.", "Book Title", "Author", "Publisher", "Publication Year", "Page", "Edition"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        // Populate table model
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            if (book.getBookState() instanceof AvailableState){
                tableModel.addRow(new Object[]{
                        i + 1,
                        book.getTitle(),
                        book.getAuthor(),
                        book.getPublisher(),
                        book.getPublicationYear(),
                        book.getPage(),
                        book.getEdition()
                });
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
        booksTable.getColumnModel().getColumn(5).setPreferredWidth(50); // Page column
        booksTable.getColumnModel().getColumn(6).setPreferredWidth(50); // Edition column

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

        JButton borrowButton = new JButton("Borrow");
        borrowButton.setBackground(new Color(144, 238, 144)); // Light green
        borrowButton.setForeground(Color.BLACK);
        borrowButton.setFocusPainted(false);
        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRow != -1) {
                    String bookTitle = (String) booksTable.getValueAt(selectedRow, 1); // Column 1 is book title
                    int response = JOptionPane.showConfirmDialog(frame,
                            "Are you sure you want to borrow the book \"" + bookTitle + "\"?",
                            "Borrow Book",
                            JOptionPane.YES_NO_OPTION);

                    UserSession userSession = UserSession.getInstance();
                    AbstractUser student;
                    try {
                        student = userSession.getCurrentUser();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    Book book = bookService.findBookByTitle(bookTitle);
                    System.out.println("Buku sebelum dipinjam  di view book: " + book.getAvailableBook());

                    LocalDate today = LocalDate.now();
                    LocalDate dueDate = today.plusDays(7);
                    if (response == JOptionPane.YES_OPTION){
                        if (loanManager.issueLoan(book)){
                            loanService.insertLoan(student.getId(), book.getBookId(), today, dueDate);
                            bookService.updateBookCount(book.getTitle(), -1, +1, 0);
                            System.out.println("Buku setelah dipinjam  di view book: " + book.getAvailableBook());
                        } else {
                            int responseForNotify = JOptionPane.showConfirmDialog(frame,
                                    "The book is out of stock. Would you like to be notified when the book is available?",
                                    "Book Not Available",
                                    JOptionPane.YES_NO_OPTION);

                            if (responseForNotify == JOptionPane.YES_OPTION) {
                                notificationService.addWaitingUserForNotification(student.getId(), book.getBookId());
                                System.out.println("User will be notified when the book is available.");
                            } else {
                                // Handle the action if the user chooses "No"
                                System.out.println("User does not want to be notified.");
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a row first!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Close button
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(220, 20, 60)); // Crimson red
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new StudentDashboardWindow();
            }
        });
        JButton searchButton = SearchFeature.addSearchButton(frame, tableModel, bookDAO);

        actionPanel.add(searchButton);
        actionPanel.add(borrowButton);
        actionPanel.add(backButton);

        frame.add(actionPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

}
