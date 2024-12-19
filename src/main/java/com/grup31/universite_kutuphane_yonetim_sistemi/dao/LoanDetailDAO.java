package com.grup31.universite_kutuphane_yonetim_sistemi.dao;

import com.grup31.universite_kutuphane_yonetim_sistemi.transaction.LoanDetail;

import java.util.List;

public interface LoanDetailDAO {
    List<LoanDetail> getAllLoans ();
    List<LoanDetail> getAllLoansByUserId (int userId);
}
