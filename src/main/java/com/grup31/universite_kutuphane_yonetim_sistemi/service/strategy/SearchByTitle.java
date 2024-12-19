package com.grup31.universite_kutuphane_yonetim_sistemi.service.strategy;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Book;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.BookDAO;

import java.util.List;

public class SearchByTitle implements BookSearchStrategy{
    private BookDAO bookDAO;

    public SearchByTitle(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public List<Book> search(String title) {
        return bookDAO.searchBookByTitle(title);
    }
}
