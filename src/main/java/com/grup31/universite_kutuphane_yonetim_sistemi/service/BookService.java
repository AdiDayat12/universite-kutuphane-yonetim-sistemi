package com.grup31.universite_kutuphane_yonetim_sistemi.service;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Book;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.BookDAO;
import java.util.List;

public class BookService {

    private final BookDAO bookDAO;

    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    // Add a new book to the system
    public void addBook(Book book) {
        bookDAO.addBook(book);
    }

    // Update the book details
    public void updateBook(Book book) {
        bookDAO.updateBook(book);
    }

    // Delete a book by its title
    public void deleteBook(String title) {
        bookDAO.deleteBook(title);
    }

    // Find a book by its title
    public Book findBookByTitle(String title) {
        return bookDAO.findBookByTitle(title);
    }

    public Book findBookById (int bookId) {
        return bookDAO.findById(bookId);
    }

    // Search for books based on a keyword
    public List<Book> searchBooks(String keyword) {
        return bookDAO.searchBooks(keyword);
    }

    // Get all books in the system
    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    // Update the book count (available, borrowed, lost)
    public void updateBookCount(String title, int availableBook, int borrowedBook, int lostBook) {
        bookDAO.updateBookCount(title, availableBook, borrowedBook, lostBook);
    }

    public int getAvailableBook (String title){
        return bookDAO.getAvailableBook(title);
    }
    public int getBorrowedBook (String title){
        return bookDAO.getBorrowedBook(title);
    }
    public int getLostBook (String title){
        return bookDAO.getLostBook(title);
    }
}
