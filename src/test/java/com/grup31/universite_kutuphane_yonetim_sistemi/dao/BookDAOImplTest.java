package com.grup31.universite_kutuphane_yonetim_sistemi.dao;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Book;
import com.grup31.universite_kutuphane_yonetim_sistemi.util.DBConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class BookDAOImplTest {
    Connection connection = DBConnection.getInstance().getConnection();
    BookDAOImpl bookDAO = new BookDAOImpl(connection);

    @Test
    void testConnection () {
        assertNotNull(connection);
    }
    @Test
    void addBook() {
        Book book = new Book.Builder()
                .setTitle("JAVA FOR BEGINNER")
                .setAuthor("Nseva")
                .setPublisher("nseva")
                .setPublicationYear(2009)
                .setEdition(3)
                .setPage(400)
                .setStock(7)
                .build();
        bookDAO.addBook(book);

    }

    @Test
    void update () {
        Book book = new Book.Builder()
                .setTitle("JAVA FOR BEGINNER")
                .setAuthor("Adi Hidayat")
                .setPublisher("nseva")
                .setPublicationYear(2009)
                .setEdition(3)
                .setPage(400)
                .setStock(7)
                .build();
        bookDAO.updateBook(book);

    }

    @Test
    void delete () {
        String title = "JAVA FOR BEGINNER";
        bookDAO.deleteBook(title);
    }

    @Test
    void findByTitle () {
        Book book = bookDAO.findBookByTitle("JAVA FOR BEGINNER");
        printResult(book);
    }

    @Test
    void search (){
        List<Book> list = bookDAO.searchBooks("linAN");
        for (Book book : list){
            printResult(book);
        }
    }

    @Test
    void getAllBooks () {
        List<Book> list = bookDAO.getAllBooks();
        System.out.println(list.size());
        for (Book book : list){
            book.borrowBook();
            book.lostBook();
            book.lostBook();
            book.lostBook();
            book.lostBook();
            book.returnBook();
            System.out.println("State : " + book.getBookState());
            printResult(book);
            System.out.println("\n");
        }
    }

    void printResult (Book book) {
        System.out.println("Title : " + book.getTitle());
        System.out.println("Author : " + book.getAuthor());
        System.out.println("Publisher : " + book.getPublisher());
        System.out.println("Publication year : " + book.getPublicationYear());
        System.out.println("Edition : " + book.getEdition());
        System.out.println("Page : " + book.getPage());
        System.out.println("Stock : " + book.getStock());
        System.out.println("Available  : " + book.getAvailableBook());
        System.out.println("Borrowed : " + book.getBorrowedBook());
        System.out.println("Lost : " + book.getLostBook());

    }
}