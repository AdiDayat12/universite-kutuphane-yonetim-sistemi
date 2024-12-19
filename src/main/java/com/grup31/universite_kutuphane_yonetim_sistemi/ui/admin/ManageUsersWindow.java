package com.grup31.universite_kutuphane_yonetim_sistemi.ui.admin;

import com.grup31.universite_kutuphane_yonetim_sistemi.dao.StudentDAO;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.StudentDAOImpl;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.StudentService;
import com.grup31.universite_kutuphane_yonetim_sistemi.user.Student;
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
import java.util.List;

public class ManageUsersWindow {
    private JFrame frame;
    private JTable studentsTable;
    private int selectedRow = -1; // To track selected row
    private StudentService studentService;
    private DefaultTableModel tableModel;

    public ManageUsersWindow() throws SQLException {
        frame = new JFrame("Library Management System - Manage Users");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Manage Users", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(70, 130, 180)); // Steel blue background
        titleLabel.setForeground(Color.WHITE); // White text
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Data from database
        Connection connection = DBConnection.getInstance().getConnection();
        StudentDAO studentDAO = new StudentDAOImpl(connection);
        this.studentService = new StudentService(studentDAO);

        List<Student> studentList = studentDAO.getAllStudents();

        // Table model for books
        String[] columnNames = {"No.", "Name", "Username", "Password", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        // Populate table model
        for (int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);
            tableModel.addRow(new Object[]{
                    i + 1,
                    student.getName(),
                    student.getUsername(),
                    student.getPassword(),
                    student.getEmail()
            });
        }

        // Create JTable with model
        studentsTable = new JTable(tableModel);
        studentsTable.setRowHeight(30);

        // Set column widths
        studentsTable.getColumnModel().getColumn(0).setPreferredWidth(50); // No. column
        studentsTable.getColumnModel().getColumn(1).setPreferredWidth(400); // name
        studentsTable.getColumnModel().getColumn(2).setPreferredWidth(100); // username
        studentsTable.getColumnModel().getColumn(3).setPreferredWidth(100); // password
        studentsTable.getColumnModel().getColumn(4).setPreferredWidth(100); // email

        JScrollPane scrollPane = new JScrollPane(studentsTable);
        studentsTable.setFillsViewportHeight(true);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add listener to handle row selection
        studentsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRow = studentsTable.getSelectedRow();
            }
        });

        // Panel for action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Add user
        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(50, 205, 50));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new AddStudentWindow();
            }
        });

        // Edit
        JButton editButton = new JButton("Edit");
        editButton.setBackground(new Color(70, 130, 180));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (selectedRow != -1) {
                    String username = (String) studentsTable.getValueAt(selectedRow, 2); // Column 1 is book title
                    Student student = null;
                    try {
                        student = studentService.getStudentByUsername(username);
                        frame.dispose();
                        new UpdateStudentWindow(student);
                        System.out.println("disni ada user");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a row first!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });




        JButton deleteButton = new JButton("Delete");
        deleteButton.setBackground(new Color(220, 20, 60)); // Crimson red
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRow != -1) {
                    String username = (String) studentsTable.getValueAt(selectedRow, 1); // Column 1 is book title
                    int response = JOptionPane.showConfirmDialog(frame,
                            "Are you sure you want to delete the user \"" + username + "\"?",
                            "Delete Book",
                            JOptionPane.YES_NO_OPTION);

                    if (response == JOptionPane.YES_OPTION) {
                        try {
                            studentService.deleteStudent(2); // Delete book from database
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        tableModel.removeRow(selectedRow); // Remove row from table
                        selectedRow = -1; // Reset selected row
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a row first!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

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

        // Add buttons to the action panel
        actionPanel.add(addButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);
        actionPanel.add(backButton);

        frame.add(actionPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
