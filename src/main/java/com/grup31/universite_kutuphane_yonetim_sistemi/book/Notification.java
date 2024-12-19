package com.grup31.universite_kutuphane_yonetim_sistemi.book;

import java.time.LocalDateTime;

public class Notification {
    private int userId;
    private int bookId;
    private boolean isRead;
    private boolean isSent;
    private LocalDateTime createdAt;
    private String bookTitle;
    private String user;

    // Constructor
    public Notification (){}


    // Getters and Setters
    public int getUserId() { return userId; }
    public int getBookId() { return bookId; }
    public boolean isRead() { return isRead; }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setRead(boolean read) {
        isRead = read;
    }


    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}


