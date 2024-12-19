package com.grup31.universite_kutuphane_yonetim_sistemi.dao;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Book;

import java.util.List;

public interface BookDAO {
    void addBook (Book book);
    void updateBook (Book book);
    void deleteBook (String title);
    Book findById (int bookId);
    Book findBookByTitle (String title);
    List<Book> searchBooks (String keyword);
    List<Book> searchBookByTitle (String title);
    List<Book> searchBookByAuthor (String author);
    List<Book> searchBookPublisher (String publisher);
    List<Book> searchBookPublicationYear (int year);
    List<Book> getAllBooks();
    void updateBookCount (String title, int availableBook, int borrowedBook, int lostBook);
    int getAvailableBook (String title);
    int getBorrowedBook (String title);
    int getLostBook (String title);
}
