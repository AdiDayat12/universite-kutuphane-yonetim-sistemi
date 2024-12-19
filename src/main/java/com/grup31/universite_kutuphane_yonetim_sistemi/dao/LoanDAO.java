package com.grup31.universite_kutuphane_yonetim_sistemi.dao;

import java.time.LocalDate;

public interface LoanDAO {

    void insertLoan(int userId, int bookId, LocalDate borrowDate, LocalDate dueDate);
    void insertReturnDate (int loanId, LocalDate returnDate);
    void updateStatus (int loanId, boolean status);
}
