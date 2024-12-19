package com.grup31.universite_kutuphane_yonetim_sistemi.util;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {
    @Test
    void testDatabase() throws SQLException {
//        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        Connection connection = DBConnection.getInstance().getConnection();
        assertNotNull(connection);
    }
}