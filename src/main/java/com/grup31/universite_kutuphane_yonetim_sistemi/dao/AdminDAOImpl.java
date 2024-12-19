package com.grup31.universite_kutuphane_yonetim_sistemi.dao;

import com.grup31.universite_kutuphane_yonetim_sistemi.user.Admin;
import com.grup31.universite_kutuphane_yonetim_sistemi.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOImpl implements AdminDAO {
    private final Connection connection;

    public AdminDAOImpl(Connection connection) {
        this.connection = DBConnection.getInstance().getConnection();
    }


    @Override
    public void insertAdmin(Admin librarian) throws SQLException {
        String sql = "INSERT INTO admins (name, username, password, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, librarian.getName());
            statement.setString(2, librarian.getUsername());
            statement.setString(3, librarian.getPassword());
            statement.setString(4, librarian.getEmail());
            statement.executeUpdate();
        }
    }


    @Override
    public Admin getAdminById(int id) throws SQLException {
        String sql = "SELECT * FROM admins WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createLibrarian(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public Admin getLibrarianByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM admins WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createLibrarian(resultSet);
                }
            }
        }
        return null;
    }


    @Override
    public void updateAdmin(Admin librarian) throws SQLException {
        String sql = "UPDATE admins " +
                "SET name = ?, username = ?, password = ?, email = ? " +
                "WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, librarian.getName());
            statement.setString(2, librarian.getUsername());
            statement.setString(3, librarian.getPassword());
            statement.setString(4, librarian.getEmail());
            statement.setInt(5, librarian.getId());
            statement.executeUpdate();
        }
    }



    @Override
    public boolean validateLoginAdmin(String username, String password) {
        String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";

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


    private Admin createLibrarian (ResultSet set) throws SQLException {
        Admin librarian = new Admin();
        librarian.setId(set.getInt("id"));
        librarian.setName(set.getString("name"));
        librarian.setUsername(set.getString("username"));
        librarian.setPassword(set.getString("password"));
        librarian.setEmail(set.getString("email"));

        return librarian;
    }
}
