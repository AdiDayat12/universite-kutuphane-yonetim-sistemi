package com.grup31.universite_kutuphane_yonetim_sistemi.service.strategy;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Book;

import java.util.List;

public class BookSearchContext {
    private BookSearchStrategy strategy;

    public void setSearchStrategy(BookSearchStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Book> searchBooks(String criteria) {
        if (strategy == null) {
            throw new IllegalStateException("Search strategy is not set");
        }
        return strategy.search(criteria);
    }
}

