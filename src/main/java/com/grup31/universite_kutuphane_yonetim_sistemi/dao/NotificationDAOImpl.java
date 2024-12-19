package com.grup31.universite_kutuphane_yonetim_sistemi.dao;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAOImpl implements NotificationDAO{
    private Connection connection;

    public NotificationDAOImpl(Connection connection){
        this.connection = connection;
    }
    @Override
    public void addWaitingUserForNotification(int userId, int bookId) {
        String sql = "INSERT INTO notifications (user_id, book_id) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, userId);
            statement.setInt(2, bookId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Notification> fetchNotificationByUserId(int userId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT b.title, s.name, n.book_id, n.user_id, n.is_sent, n.is_read " +
                "FROM notifications n " +
                "JOIN Books b ON b.id = n.book_id " +
                "JOIN Students s ON s.id = n.user_id " +
                "WHERE user_id = ? AND is_sent = TRUE";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, userId);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                Notification notification = new Notification();
                notification.setBookId(set.getInt("book_id"));
                notification.setUserId(set.getInt("user_id"));
                notification.setBookTitle(set.getString("title"));
                notification.setUser(set.getString("name"));
                notification.setSent(set.getBoolean("is_sent"));
                notification.setRead(set.getBoolean("is_read"));
                list.add(notification);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void updateSentStatus(int bookId) {
        String sql = "UPDATE notifications SET is_sent = TRUE WHERE book_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, bookId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateReadStatus(int bookId, int userId) {
        String sql = "UPDATE notifications SET is_read = TRUE WHERE book_id = ? AND user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, bookId);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
