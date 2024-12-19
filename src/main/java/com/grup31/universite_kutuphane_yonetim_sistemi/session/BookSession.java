package com.grup31.universite_kutuphane_yonetim_sistemi.session;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Book;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.BookDAO;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.BookDAOImpl;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.BookService;
import com.grup31.universite_kutuphane_yonetim_sistemi.util.DBConnection;

import java.sql.Connection;

public class BookSession {
    private static BookSession instance;
    private String title;
    private String author;
    private String publisher;
    private int publicationYear;
    private int edition;
    private int page;
    private int stock;
    private BookService bookService;

    private BookSession () {
        Connection connection = DBConnection.getInstance().getConnection();
        BookDAO bookDAO = new BookDAOImpl(connection);
        bookService = new BookService(bookDAO);
    }

    public static BookSession getInstance (){
        if (instance == null){
            instance = new BookSession();
        }
        return instance;
    }

    public String addBook (String title, String author, String publisher,
                           int publicationYear, int edition, int page, int stock) {
        if (isNullOrEmpty(title) || isNullOrEmpty(author) || isNullOrEmpty(publisher)){
            return "All Column Must Be Filled";
        }
        if (bookService.findBookByTitle(title) != null){
            return "Book Already exists";
        }
        Book book = new Book.Builder()
                .setTitle(title)
                .setAuthor(author)
                .setPublisher(publisher)
                .setPublicationYear(publicationYear)
                .setEdition(edition)
                .setPage(page)
                .setStock(stock)
                .build();
        bookService.addBook(book);
        return "Başarıyla eklendi";
    }

    public String updateBook (int bookId, String title, String author, String publisher,
                              int publicationYear, int edition, int page, int stock) {
        if (isNullOrEmpty(title) || isNullOrEmpty(author) || isNullOrEmpty(publisher)){
            return "Tüm alanlar doldurulmalıdırd";
        }

        Book existingBook = bookService.findBookById(bookId);
        if (existingBook == null){
            return "Bu kitap bulunamadı";
        }

        Book updatedBook = new Book.Builder()
                .setId(bookId)
                .setTitle(title)
                .setAuthor(author)
                .setPublisher(publisher)
                .setPublicationYear(publicationYear)
                .setEdition(edition)
                .setPage(page)
                .setStock(stock)
                .build();
        bookService.updateBook(updatedBook);
        return "Başarıyla güncellendi";
    }


    // Util untuk validasi string kosong/null
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
