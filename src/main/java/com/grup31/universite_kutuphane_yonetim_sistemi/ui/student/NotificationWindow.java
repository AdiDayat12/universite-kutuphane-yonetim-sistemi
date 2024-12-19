package com.grup31.universite_kutuphane_yonetim_sistemi.ui.student;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Notification;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.NotificationDAO;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.NotificationDAOImpl;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.NotificationService;
import com.grup31.universite_kutuphane_yonetim_sistemi.session.UserSession;
import com.grup31.universite_kutuphane_yonetim_sistemi.user.AbstractUser;
import com.grup31.universite_kutuphane_yonetim_sistemi.util.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class NotificationWindow {
    private UserSession userSession;
    private JTable notificationsTable;
    private JScrollPane scrollPane;
    private JFrame frame; // Membuat objek JFrame secara manual
    private NotificationService notificationService;

    public NotificationWindow() throws SQLException {

        frame = new JFrame("Notifications");

        userSession = UserSession.getInstance();
        Connection connection = DBConnection.getInstance().getConnection();
        NotificationDAO notificationDAO = new NotificationDAOImpl(connection);
        notificationService = new NotificationService(notificationDAO);

        AbstractUser abstractUser = userSession.getCurrentUser();
        int studentId = abstractUser.getId();
        List<Notification> notifications = notificationService.fetchNotificationByUserId(studentId);


        frame.setLayout(new BorderLayout());
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        if (notifications.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No notifications available.", "No Notifications", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            new StudentDashboardWindow();
        } else {

            String[] columnNames = {"Notifications"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

            for (Notification notification : notifications) {
                if (notification.isSent() && !notification.isRead()){
                    String[] rowData = {notification.getBookTitle() + " is available now"};
                    tableModel.addRow(rowData);
                    // update read status
                    notificationService.updateReadStatus(notification.getBookId(), notification.getUserId());
                }

            }

            notificationsTable = new JTable(tableModel);
            notificationsTable.setFillsViewportHeight(true);

            scrollPane = new JScrollPane(notificationsTable);
            frame.add(scrollPane, BorderLayout.CENTER);

            frame.setVisible(true);
        }

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

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

        actionPanel.add(backButton);
        frame.add(actionPanel, BorderLayout.SOUTH);
    }
}
