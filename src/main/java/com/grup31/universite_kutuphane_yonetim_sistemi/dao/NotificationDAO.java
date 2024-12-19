package com.grup31.universite_kutuphane_yonetim_sistemi.dao;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Notification;

import java.util.List;

public interface NotificationDAO {
    void addWaitingUserForNotification (int userId, int bookId);
    List<Notification> fetchNotificationByUserId (int userId);
    void updateSentStatus (int bookId);
    void updateReadStatus (int bookId, int userId);
}
