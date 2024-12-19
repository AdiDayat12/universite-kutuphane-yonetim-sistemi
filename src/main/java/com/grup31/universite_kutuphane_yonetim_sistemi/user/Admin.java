package com.grup31.universite_kutuphane_yonetim_sistemi.user;

public class Admin extends AbstractUser {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public Admin(String name, String username , String password, String email) {
        super(name, username, password, email);
    }

    public Admin(){}

}
