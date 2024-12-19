package com.grup31.universite_kutuphane_yonetim_sistemi.book;

// rakta durumu
public class AvailableState implements BookState{
    private Book book;

    public AvailableState(Book book) {
        this.book = book;
    }

    @Override
    public void borrowBook() {

        if (book.getAvailableBook() == 0){
            book.setBookState(new OutOfStock(book));
            System.out.println("No book left");
        } else {
            book.notifyObserver();
            book.increaseBorrowedBook();
        }
    }

    @Override
    public void returnBook() {
        if (book.getAvailableBook() == book.getStock()){
            System.out.println(book);
            System.out.println("No book to return");
        } else {
            book.decreaseBorrowedBook();
        }
    }

    @Override
    public void lostBook() {
        if (book.getAvailableBook() == 0){
            book.setBookState(new OutOfStock(book));
            System.out.println("No book ready to borrow ");
        } else {
            book.increaseLostBook();
            book.setStock(book.getStock() - 1);
        }
    }

}
