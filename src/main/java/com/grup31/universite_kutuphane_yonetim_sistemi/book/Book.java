package com.grup31.universite_kutuphane_yonetim_sistemi.book;

import com.grup31.universite_kutuphane_yonetim_sistemi.user.StudentObserver;

import java.util.ArrayList;
import java.util.List;

public class Book implements BookSubject{
    private int bookId;
    private String title;
    private String author;
    private String publisher;
    private int publicationYear;
    private int edition;
    private int page;
    private int stock;
    private BookState bookState;
    private int availableBook;
    private int borrowedBook;
    private int lostBook;
    private List<StudentObserver> studentObservers = new ArrayList<>();

    // Private constructor
    private Book (Builder builder) {
        this.bookId = builder.bookId;
        this.title = builder.title;
        this.author = builder.author;
        this.publisher = builder.publisher;
        this.publicationYear = builder.publicationYear;
        this.edition = builder.edition;
        this.page = builder.page;
        this.stock = builder.stock;
        this.availableBook = builder.availableBook;
        this.borrowedBook = builder.borrowedBook;
        this.lostBook = builder.lostBook;
        this.bookState = new AvailableState(this);
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public int getEdition() {
        return edition;
    }

    public int getPage() {
        return page;
    }

    public void setStock (int stock){
        this.stock = stock;
    }
    public int getStock() {
        return stock;
    }

    public void setAvailableBook(int availableBook) {
        this.availableBook = availableBook;
    }

    public void setBorrowedBook(int borrowedBook) {
        this.borrowedBook = borrowedBook;
    }

    public void setLostBook(int lostBook) {
        this.lostBook = lostBook;
    }

    public BookState getBookState() {
        return bookState;
    }


    public void setBookState (BookState bookState){
        this.bookState = bookState;
    }

    public int getAvailableBook() {
        return availableBook;
    }


    // states changer
    public void borrowBook () {
        bookState.borrowBook();
    }

    public void returnBook () {
        bookState.returnBook();
    }

    public void lostBook () {
        bookState.lostBook();
    }

    public int getLostBook() {
        return lostBook;
    }

    public int getBorrowedBook() {
        return borrowedBook;
    }

    protected void increaseBorrowedBook () {
        this.availableBook --;
        this.borrowedBook ++;
    }

    protected void decreaseBorrowedBook () {
        this.availableBook ++;
        this.borrowedBook --;
    }

    protected void increaseLostBook () {
        this.lostBook ++;
        this.availableBook --;
    }

    @Override
    public String toString() {
        return "Book{" +
                "\ntitle ='" + title + "'\n" +
                "author ='" + author + "'\n" +
                "publisher ='" + publisher + "'\n" +
                "publicationYear =" + publicationYear + "\n" +
                "edition =" + edition + "\n" +
                "page =" + page + "\n" +
                "stock =" + stock + "\n" +
                "borrowedBook =" + borrowedBook + "\n" +
                "lostBook =" + lostBook + "\n" +
                '}';
    }

    @Override
    public void addObserver(StudentObserver studentObserver) {
        studentObservers.add(studentObserver);
    }

    @Override
    public void removeObserver(StudentObserver studentObserver) {
        studentObservers.remove(studentObserver);
    }

    @Override
    public void notifyObserver() {
        if (bookState instanceof AvailableState) {
            for (StudentObserver observer : studentObservers){
                observer.update(title);
            }
        }
    }

    public List<StudentObserver> getStudentObservers (){
        return studentObservers;
    }

    public static class Builder {
        private int bookId;
        private String title;
        private String author;
        private String publisher;
        private int publicationYear;
        private int edition;
        private int page;
        private int stock;
        private int availableBook;
        private int borrowedBook;
        private int lostBook;

        public Builder setId (int bookId){
            this.bookId = bookId;
            return this;
        }

        public Builder setTitle (String title){
            this.title = title;
            return this;
        }
        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setPublisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder setPublicationYear(int publicationYear) {
            this.publicationYear = publicationYear;
            return this;
        }

        public Builder setEdition(int edition) {
            this.edition = edition;
            return this;
        }

        public Builder setPage(int page) {
            this.page = page;
            return this;
        }

        public Builder setStock(int stock) {
            this.stock = stock;
            return this;
        }

        public Builder setAvailableBook (int availableBook){
            this.availableBook = availableBook;
            return this;
        }

        public Builder setBorrowedBook (int borrowedBook){
            this.borrowedBook = borrowedBook;
            return this;
        }

        public Builder setLostBook (int lostBook){
            this.lostBook = lostBook;
            return this;
        }

        public Book build () {
            return new Book(this);
        }
    }

}
