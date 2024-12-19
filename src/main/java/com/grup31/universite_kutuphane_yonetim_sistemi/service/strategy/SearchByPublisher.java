package com.grup31.universite_kutuphane_yonetim_sistemi.service.strategy;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Book;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.BookDAO;

import java.util.List;

public class SearchByPublisher implements BookSearchStrategy{
    private BookDAO bookDAO;

    public SearchByPublisher(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public List<Book> search(String criteria) {
        return bookDAO.searchBookPublisher(criteria);
    }

}
