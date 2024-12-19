package com.grup31.universite_kutuphane_yonetim_sistemi.dao;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Notification;
import com.grup31.universite_kutuphane_yonetim_sistemi.user.Student;
import com.grup31.universite_kutuphane_yonetim_sistemi.user.StudentObserver;

import java.sql.SQLException;
import java.util.List;

public interface StudentDAO {
    void addStudent(Student student) throws SQLException;
    void updateStudent (Student student);

    void updateFine(int studentId, double fine) throws SQLException;

    List<Student> getAllStudents() throws SQLException;

    Student getStudentById(int id) throws SQLException;

    Student getStudentByName(String name) throws SQLException;
    Student getStudentByUsername(String username) throws SQLException;

    void deleteStudent(int id) throws SQLException;

    boolean validateLoginStudent (String username, String password);

//    void saveStudentObserver(int studentId, int bookId);
//
//    List<StudentObserver> getObserversForBook(int bookId);
//
//    List<Notification> getNotificationsForStudent(int studentId);
}
