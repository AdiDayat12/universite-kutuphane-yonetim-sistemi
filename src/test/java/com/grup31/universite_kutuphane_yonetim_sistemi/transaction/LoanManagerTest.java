//package com.grup31.universite_kutuphane_yonetim_sistemi.transaction;
//
//import com.grup31.universite_kutuphane_yonetim_sistemi.book.Book;
//import com.grup31.universite_kutuphane_yonetim_sistemi.user.AbstractUser;
//import com.grup31.universite_kutuphane_yonetim_sistemi.user.UserFactory;
//import org.junit.jupiter.api.Test;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class LoanManagerTest {
//
//    @Test
//    void testOne () throws ParseException {
//        Book book = new Book.Builder().setTitle("Clean code").setStock(3).build();
//
//        UserFactory userFactory = new UserFactory();
//        AbstractUser student = userFactory.createUser("student", "adiHidayat", "adi@gmail.com");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date dueDate = sdf.parse("2024-12-01");
//
//        AbstractUser student2 = userFactory.createUser("student", "adiHidayat", "adi@gmail.com");
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
//        Date dueDate2 = sdf2.parse("2024-10-01");
//
//        LoanManager loanManager = new LoanManager();
//        loanManager.issueLoan(book, student, dueDate);
//        LoanManager loanManager2 = new LoanManager();
//        loanManager2.issueLoan(book, student2, dueDate2);
//
//        assertEquals(1, book.getAvailableBook()); // Check stock is decreased
//
//        loanManager.returnLoan(book, student);
//
//
////        assertTrue(loanManager.hasActiveLoan(book, student)); // Check loan exists
////        assertEquals(issueDate, loanManager.getLoan(book, student).getIssueDate()); // Check issue date
////        assertEquals(dueDate, loanManager.getLoan(book, student).getDueDate()); // Check due date
//
//    }
//
//}