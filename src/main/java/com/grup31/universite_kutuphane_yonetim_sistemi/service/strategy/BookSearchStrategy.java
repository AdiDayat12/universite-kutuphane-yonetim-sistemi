package com.grup31.universite_kutuphane_yonetim_sistemi.service.strategy;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Book;

import java.util.List;

public interface BookSearchStrategy {
    List<Book> search (String criteria);
}
