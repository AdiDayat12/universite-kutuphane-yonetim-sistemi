package com.grup31.universite_kutuphane_yonetim_sistemi.user;

public class Student extends AbstractUser implements StudentObserver{

    public Student(String name,  String username, String password, String email) {
        super(name, username, password, email);
    }

    public Student (int id){
        this.id = id;
    }
    public Student (){}



    @Override
    public void update(String bookTitle) {
        System.out.println("Hey " + name + " book " + bookTitle + " is now available");
    }
}
