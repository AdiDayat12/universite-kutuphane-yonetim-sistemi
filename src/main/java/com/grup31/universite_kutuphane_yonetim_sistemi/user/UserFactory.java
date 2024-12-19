package com.grup31.universite_kutuphane_yonetim_sistemi.user;

public class UserFactory {

    // Factory method to create User objects (Student or Librarian)
    public static AbstractUser createUser(String type, String name, String username, String password, String email) {
        if (type.equalsIgnoreCase("student")) {
            return new Student(name, username, password,  email);  // Create a Student object
        } else if (type.equalsIgnoreCase("librarian")) {
            return new Admin(name, username, password, email);  // Create a Librarian object
        } else {
            throw new IllegalArgumentException("Invalid user type: " + type);  // Handle invalid user type
        }
    }
}
