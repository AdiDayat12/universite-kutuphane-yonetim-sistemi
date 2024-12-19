package com.grup31.universite_kutuphane_yonetim_sistemi.service;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Notification;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.StudentDAO;
import com.grup31.universite_kutuphane_yonetim_sistemi.user.Student;
import com.grup31.universite_kutuphane_yonetim_sistemi.user.StudentObserver;

import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private final StudentDAO studentDAO;

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public void addStudent(Student student) throws  SQLException {
        studentDAO.addStudent(student);
    }

    public void updateStudent (Student student){
        studentDAO.updateStudent(student);
    }
    public void updateFine(int studentId, double fine) throws SQLException {
        studentDAO.updateFine(studentId, fine);
    }

    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.getAllStudents();
    }

    public Student getStudentById(int studentId) throws SQLException {
        return studentDAO.getStudentById(studentId);
    }

    public Student getStudentByName(String name) throws SQLException {
        return studentDAO.getStudentByName(name);
    }

    public void deleteStudent(int studentId) throws SQLException {
        studentDAO.deleteStudent(studentId);
    }

    public Student getStudentByUsername (String username) throws SQLException {
        return studentDAO.getStudentByUsername(username);
    }

    public boolean validateLoginStudent (String username, String password){
        return studentDAO.validateLoginStudent(username, password);
    }

}

