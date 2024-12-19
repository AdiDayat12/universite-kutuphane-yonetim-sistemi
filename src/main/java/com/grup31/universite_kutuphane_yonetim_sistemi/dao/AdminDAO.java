package com.grup31.universite_kutuphane_yonetim_sistemi.dao;

import com.grup31.universite_kutuphane_yonetim_sistemi.user.Admin;

import java.sql.SQLException;
import java.util.List;

public interface AdminDAO {
    void insertAdmin(Admin librarian) throws SQLException;
    Admin getAdminById(int id) throws SQLException;
    Admin getLibrarianByUsername(String username) throws SQLException;
    void updateAdmin(Admin librarian) throws SQLException;
    boolean validateLoginAdmin (String username, String password);

}
