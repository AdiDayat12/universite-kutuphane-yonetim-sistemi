package com.grup31.universite_kutuphane_yonetim_sistemi.dao;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Book;
import com.grup31.universite_kutuphane_yonetim_sistemi.book.Notification;
import com.grup31.universite_kutuphane_yonetim_sistemi.user.Student;
import com.grup31.universite_kutuphane_yonetim_sistemi.user.StudentObserver;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO{

    private final Connection connection;

    public StudentDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO Students (name, username, password, email, fine) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getUsername());
            statement.setString(3, student.getPassword());
            statement.setString(4, student.getEmail());
            statement.setDouble(5, student.getFine());
            statement.executeUpdate();
        }
    }

    @Override
    public void updateFine(int studentId, double fine) throws SQLException {
        String sql = "UPDATE Students SET fine = fine + ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, fine);
            statement.setInt(2, studentId);
            statement.executeUpdate();
        }
    }

    @Override
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Students";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                students.add(createStudent(resultSet));
            }
        }
        return students;
    }

    @Override
    public void updateStudent(Student student) {
        String sql = """
                UPDATE Students SET name = ?, username = ?, password = ?, email = ? WHERE id = ?
               """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getUsername());
            statement.setString(3, student.getPassword());
            statement.setString(4, student.getEmail());
            statement.setInt(5, student.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Student getStudentById(int studentId) throws SQLException {
        String sql = "SELECT * FROM Students WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    return createStudent(set);
                }
            }
        }
        return null;
    }

    @Override
    public Student getStudentByName(String name) throws SQLException {
        String sql = "SELECT * FROM Students WHERE name ILIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    return createStudent(set);
                }
            } catch (SQLException e){
                throw new SQLException();
            }
        }
        return null;
    }

    @Override
    public Student getStudentByUsername(String username) throws SQLException {
        Student student = null;
        String sql = "SELECT * FROM Students WHERE username ILIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                student = createStudent(set);
            }
            return student;
        } catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteStudent(int studentId) throws SQLException {
        String sql = "DELETE FROM Students WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.executeUpdate();
        }
    }

    @Override
    public boolean validateLoginStudent(String username, String password) {
        String sql = "SELECT * FROM Students WHERE username = ? AND password = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return true;  // If there's a row, the login is valid
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }




    private Student createStudent (ResultSet set) throws SQLException {
        Student student = new Student();
        student.setId(set.getInt("id"));
        student.setName(set.getString("name"));
        student.setUsername(set.getString("username"));
        student.setPassword(set.getString("password"));
        student.setEmail(set.getString("email"));
        student.setFine(set.getDouble("fine"));

        return student;
    }
}
