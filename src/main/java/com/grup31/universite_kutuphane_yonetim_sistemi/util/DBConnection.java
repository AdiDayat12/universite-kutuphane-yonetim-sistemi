package com.grup31.universite_kutuphane_yonetim_sistemi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;
    private final String URL = "jdbc:postgresql://localhost:5432/test_for_library";
    private final String USER = "postgres";
    private final String PASS = "my1stpsql";

    private DBConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
        }  catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    public static DBConnection getInstance () {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection () {
        return  connection;
    }

}
