package com.grup31.universite_kutuphane_yonetim_sistemi.book;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testFunction () {
        Book book = new Book.Builder()
                .setTitle("Java")
                .setAuthor("Adi")
                .setPublisher("Nseva")
                .setPublicationYear(2005)
                .setEdition(1)
                .setPage(200)
                .setStock(5)
                .build();
        book.borrowBook();
        book.borrowBook();

        assertEquals(2, book.getBorrowedBook());
        assertEquals(3, book.getAvailableBook());

        book.lostBook();
        assertEquals(1, book.getLostBook());
        assertEquals(2, book.getAvailableBook());

        book.returnBook();
        assertEquals(3, book.getAvailableBook());
        assertEquals(1, book.getBorrowedBook());


        System.out.println(book);



    }

}