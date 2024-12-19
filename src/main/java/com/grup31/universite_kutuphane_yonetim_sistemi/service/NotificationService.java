package com.grup31.universite_kutuphane_yonetim_sistemi.service;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Notification;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.NotificationDAO;

import java.util.List;

public class NotificationService {
    private NotificationDAO notificationDAO;

    public NotificationService(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    public void addWaitingUserForNotification (int userId, int bookId){
        notificationDAO.addWaitingUserForNotification(userId, bookId);
    }

    public void updateSentStatus (int bookId){
        notificationDAO.updateSentStatus(bookId);
    }

    public List<Notification> fetchNotificationByUserId(int userId){
        return notificationDAO.fetchNotificationByUserId(userId);
    }

    public void updateReadStatus (int bookId, int userId){
        notificationDAO.updateReadStatus(bookId, userId);
    }
}
