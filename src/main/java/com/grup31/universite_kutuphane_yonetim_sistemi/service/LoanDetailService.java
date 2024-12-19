package com.grup31.universite_kutuphane_yonetim_sistemi.service;

import com.grup31.universite_kutuphane_yonetim_sistemi.dao.LoanDetailDAO;
import com.grup31.universite_kutuphane_yonetim_sistemi.transaction.LoanDetail;

import java.util.List;

public class LoanDetailService {
    private LoanDetailDAO loanDetailDAO;

    public LoanDetailService (LoanDetailDAO loanDetailDAO){
        this.loanDetailDAO = loanDetailDAO;
    }
    public List<LoanDetail> getAllLoansByUserId (int userId){
        return loanDetailDAO.getAllLoansByUserId(userId);

    }public List<LoanDetail> getAllLoans (){
        return loanDetailDAO.getAllLoans();
    }
}
