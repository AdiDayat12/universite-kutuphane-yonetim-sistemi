package com.grup31.universite_kutuphane_yonetim_sistemi.book;


public class OutOfStock implements BookState{
    private Book book;

    public OutOfStock(Book book) {
        this.book = book;
    }

    @Override
    public void borrowBook() {
        System.out.println("No book left");
    }

    @Override
    public void returnBook() {
        book.decreaseBorrowedBook();
        book.setBookState(new AvailableState(book));
        System.out.println("ini dikelas outofstock");
        book.notifyObserver();
    }

    @Override
    public void lostBook() {
        if (book.getAvailableBook() == 0){
            book.setBookState(new OutOfStock(book));
            System.out.println("No book left");
        } else {
            book.increaseLostBook();
            book.setStock(book.getStock() - 1);
        }
    }
}
