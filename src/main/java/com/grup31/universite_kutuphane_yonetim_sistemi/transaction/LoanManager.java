package com.grup31.universite_kutuphane_yonetim_sistemi.transaction;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.AvailableState;
import com.grup31.universite_kutuphane_yonetim_sistemi.book.Book;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.*;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.BookService;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.NotificationService;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.StudentService;
import com.grup31.universite_kutuphane_yonetim_sistemi.user.StudentObserver;
import com.grup31.universite_kutuphane_yonetim_sistemi.util.DBConnection;

import java.sql.Connection;
import java.util.List;

public class LoanManager {
    private BookService bookService;
    private StudentService studentService;
    private NotificationService notificationService;
    public LoanManager (){
        Connection connection = DBConnection.getInstance().getConnection();
        BookDAO bookDAO = new BookDAOImpl(connection);
        bookService = new BookService(bookDAO);

        StudentDAO studentDAO = new StudentDAOImpl(connection);
        studentService = new StudentService(studentDAO);

        NotificationDAO notificationDAO = new NotificationDAOImpl(connection);
        notificationService = new NotificationService(notificationDAO);
    }

    public boolean issueLoan (Book book){
        Book book1 = bookService.findBookById(book.getBookId());
        setCountBooks(book);
        book.borrowBook();
        return book1.getAvailableBook() > 0;
    }

    public void returnLoan(Book book) {
        setCountBooks(book);
        book.returnBook();


        if (book.getBookState() instanceof AvailableState) {
            System.out.println("ini notify dari loan");
            notificationService.updateSentStatus(book.getBookId());
        }
    }



    public void lostBookReport (Book book){
        setCountBooks(book);
        book.lostBook();

    }

    private void setCountBooks (Book book){
        book.setAvailableBook(bookService.getAvailableBook(book.getTitle()));
        book.setBorrowedBook(bookService.getBorrowedBook(book.getTitle()));
        book.setLostBook(bookService.getLostBook(book.getTitle()));
    }




}


