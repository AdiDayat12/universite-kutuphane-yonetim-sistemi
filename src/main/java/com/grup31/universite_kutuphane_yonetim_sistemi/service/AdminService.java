package com.grup31.universite_kutuphane_yonetim_sistemi.service;

import com.grup31.universite_kutuphane_yonetim_sistemi.dao.AdminDAO;
import com.grup31.universite_kutuphane_yonetim_sistemi.user.Admin;

import java.sql.SQLException;
import java.util.List;

public class AdminService {

    private AdminDAO adminDAO;

    public AdminService(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }


    public void insertAdmin(Admin librarian) throws SQLException, SQLException {
        adminDAO.insertAdmin(librarian);
    }

    public Admin getAdminById(int id) throws SQLException {
        return adminDAO.getAdminById(id);
    }

    public Admin getLibrarianByUsername(String username) throws SQLException {
        return adminDAO.getLibrarianByUsername(username);
    }


    public void updateAdmin(Admin librarian) throws SQLException {
        adminDAO.updateAdmin(librarian);
    }


    public boolean validateLoginAdmin (String username, String password){
        return adminDAO.validateLoginAdmin(username, password);
    }

}

