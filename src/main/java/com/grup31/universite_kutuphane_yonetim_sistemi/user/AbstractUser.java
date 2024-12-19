package com.grup31.universite_kutuphane_yonetim_sistemi.user;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Notification;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractUser {
    protected int id;
    protected String name;
    protected String username;
    protected String password;
    protected String email;
    protected double fine;
    protected List<Notification> notificationList = new ArrayList<>();

    public void setFine(double fine) {
        this.fine = fine;
    }

    // Constructor
    public AbstractUser(String name, String username, String password, String email) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public AbstractUser (){}

    // Getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getFine() {
        return fine;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }
}
