package com.grup31.universite_kutuphane_yonetim_sistemi.service;

import com.grup31.universite_kutuphane_yonetim_sistemi.dao.LoanDAO;

import java.time.LocalDate;

public class LoanService {
    private LoanDAO loanDAO;

    public LoanService (LoanDAO loanDAO){
        this.loanDAO = loanDAO;
    }

    public void insertLoan (int userId, int bookId, LocalDate borrowDate, LocalDate dueDate){
        loanDAO.insertLoan(userId, bookId, borrowDate, dueDate);
    }

    public void insertReturnDate (int loanId, LocalDate returnDate){
        loanDAO.insertReturnDate(loanId, returnDate);
    }
    public void updateStatus (int loanId, boolean status){
        loanDAO.updateStatus(loanId, status);
    }

}
