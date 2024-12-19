package com.grup31.universite_kutuphane_yonetim_sistemi.transaction;

import java.time.LocalDate;

public class LoanDetail {
    private int loanId;
    private String studentName;
    private String bookTitle;
    private String bookAuthor;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private boolean isReturned;
    private LocalDate returningDate;

    public LoanDetail (){}
    public LoanDetail(int loanId, String studentName, String bookTitle, String bookAuthor, LocalDate loanDate, LocalDate dueDate) {
        this.loanId = loanId;
        this.studentName = studentName;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.isReturned = false;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    public LocalDate getReturningDate() {
        return returningDate;
    }

    public void setReturningDate(LocalDate returningDate) {
        this.returningDate = returningDate;
    }
}
