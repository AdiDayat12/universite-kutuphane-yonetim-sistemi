package com.grup31.universite_kutuphane_yonetim_sistemi.session;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Notification;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.AdminDAO;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.AdminDAOImpl;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.StudentDAO;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.StudentDAOImpl;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.AdminService;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.StudentService;
import com.grup31.universite_kutuphane_yonetim_sistemi.user.AbstractUser;
import com.grup31.universite_kutuphane_yonetim_sistemi.user.Admin;
import com.grup31.universite_kutuphane_yonetim_sistemi.user.Student;
import com.grup31.universite_kutuphane_yonetim_sistemi.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class UserSession {
    private static UserSession instance;
    private String username;
    private String role; // ADMIN or USER
    private StudentService studentService;
    private AdminService adminService;


    public UserSession() {
        Connection connection = DBConnection.getInstance().getConnection();
        StudentDAO studentDAO = new StudentDAOImpl(connection);
        AdminDAO adminDAO = new AdminDAOImpl(connection); // Pastikan AdminDAO ini valid

        this.studentService = new StudentService(studentDAO);
        this.adminService = new AdminService(adminDAO);
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String register(String name, String username, String password, String email, String role, String adminCode) throws SQLException {

        if (isNullOrEmpty(name) || isNullOrEmpty(username) || isNullOrEmpty(password) || isNullOrEmpty(email) || isNullOrEmpty(role)) {
            return "All fields except adminCode must be filled!";
        }


        switch (role.toLowerCase()) {
            case "user":
                return registerStudent(name, username, password, email);

            case "admin":
                if (isNullOrEmpty(adminCode)) {
                    return "Admin Code is required.";
                }
                return registerAdmin(name, username, password, email, adminCode);

            default:
                return "Invalid role: " + role;
        }
    }

    // Method untuk pendaftaran Student
    private String registerStudent(String name, String username, String password, String email) throws SQLException {
        if (studentService.getStudentByUsername(username) != null) {
            return "Username already exists!";
        }
        Student student = new Student(name, username, password, email);
        System.out.println("name = " + student.getName());
        System.out.println("username = " + student.getUsername());
        System.out.println("pass = " + student.getPassword());
        System.out.println("email = " + student.getEmail());
        studentService.addStudent(student);
        return "Registration successful!";
    }


    private String registerAdmin(String name, String username, String password, String email, String adminCode) throws SQLException {
        final String VALID_ADMIN_CODE = "admin123"; // Konstanta untuk admin code

        if (adminService.getLibrarianByUsername(username) != null) {
            return "Username already exists!";
        }
        if (!VALID_ADMIN_CODE.equals(adminCode)) {
            return "Invalid admin code!";
        }
        Admin librarian = new Admin(name, username, password, email);
        adminService.insertAdmin(librarian);
        return "Registration successful!";
    }


    public String updateStudentByAdmin(int id, String name, String username, String password, String email) throws SQLException {
        Student existingStudent = studentService.getStudentById(id);
        if (existingStudent == null) {
            return "Student not found";
        }

        Student student = new Student(name, username, password, email);
        student.setId(id);
        studentService.updateStudent(student);
        return "Updated successfully!";
    }

    public String updateStudent (int id, String name, String username, String password, String newPassword, String email) throws SQLException {
        Student existingStudent = studentService.getStudentById(id);
        if (!existingStudent.getPassword().equals(password)) {
            return "Wrong password";
        }

        Student student = new Student(name, username, password, email);
        student.setId(id);
        studentService.updateStudent(student);
        return "Updated successfully!";
    }

    public String updateAdmin (int id, String name, String username, String password, String newPassword, String email) throws SQLException {
        Admin existingAdmin = adminService.getAdminById(id);
        if (!existingAdmin.getPassword().equals(password)) {
            return "Wrong password";
        }

        Admin admin = new Admin(name, username, password, email);
        admin.setId(id);
        adminService.updateAdmin(admin);
        return "Updated successfully!";
    }
    // Util untuk validasi string kosong/null
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }



    public boolean login(String username, String password) {
        if (adminService.validateLoginAdmin(username, password)) {
            this.username = username;
            this.role = "ADMIN";
            return true;
        }
        if (studentService.validateLoginStudent(username, password)) {
            this.username = username;
            this.role = "STUDENT";
            return true;
        }
        return false;
    }



    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public AbstractUser getCurrentUser() throws SQLException {
        if ("ADMIN".equalsIgnoreCase(role)) {
            return adminService.getLibrarianByUsername(username);
        } else if ("STUDENT".equalsIgnoreCase(role)) {
            return studentService.getStudentByUsername(username);
        }
        return null;
    }


    public void logout() {
        username = null;
        role = null;
    }
}
