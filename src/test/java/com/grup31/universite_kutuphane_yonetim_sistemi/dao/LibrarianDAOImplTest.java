//package com.grup31.universite_kutuphane_yonetim_sistemi.dao;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//
//class LibrarianDAOImplTest {
//
//    @Test
//    void login() {
//        // Mock the AdminDAO
//        AdminDAO librarianDAO = mock(AdminDAOImpl.class);
//
//        // Define the behavior of validateLoginLibrarian
//        when(librarianDAO.validateLoginLibrarian("nseva", "123")).thenReturn(true);
//
//        // Call the method to test
//        boolean result = librarianDAO.validateLoginLibrarian("nseva", "123");
//
//        // Assert the result
//        assertTrue(result);
//
//        // Optionally, verify that the method was called
//        verify(librarianDAO).validateLoginLibrarian("nseva", "123");
//    }
//}
