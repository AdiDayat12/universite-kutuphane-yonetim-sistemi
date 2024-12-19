package com.grup31.universite_kutuphane_yonetim_sistemi.dao;

import com.grup31.universite_kutuphane_yonetim_sistemi.transaction.LoanDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanDetailImpl implements LoanDetailDAO{
    private Connection connection;

    public LoanDetailImpl (Connection connection){
        this.connection = connection;
    }

    @Override
    public List<LoanDetail> getAllLoans() {
        List<LoanDetail> loanDetails = new ArrayList<>();
        String sql = "SELECT l.loan_id, s.name, b.title, b.author, " +
                "l.borrow_date, l.due_date, l.return_date, l.is_returned " +
                "FROM loans l " +
                "JOIN Students s ON s.id = l.user_id " +
                "JOIN Books b ON b.id = l.book_id";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet set = statement.executeQuery();


            while (set.next()){
                LoanDetail loanDetail = new LoanDetail();
                loanDetail.setLoanId(set.getInt("loan_id"));
                loanDetail.setStudentName(set.getString("name"));
                loanDetail.setBookTitle(set.getString("title"));
                loanDetail.setBookAuthor(set.getString("author"));
                loanDetail.setLoanDate(set.getDate("borrow_date").toLocalDate());
                loanDetail.setDueDate(set.getDate("due_date").toLocalDate());
                loanDetail.setReturned(set.getBoolean("is_returned"));
                loanDetails.add(loanDetail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loanDetails;
    }

    @Override
    public List<LoanDetail> getAllLoansByUserId(int userId) {
        List<LoanDetail> loanDetails = new ArrayList<>();
        String sql = "SELECT l.loan_id, s.name, b.title, b.author, " +
                "l.borrow_date, l.due_date, l.return_date, l.is_returned " +
                "FROM loans l " +
                "JOIN Students s ON s.id = l.user_id " +
                "JOIN Books b ON b.id = l.book_id " +
                "WHERE s.id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);

            ResultSet set = statement.executeQuery();


            while (set.next()) {
                LoanDetail loanDetail = new LoanDetail();
                loanDetail.setLoanId(set.getInt("loan_id"));
                loanDetail.setStudentName(set.getString("name"));
                loanDetail.setBookTitle(set.getString("title"));
                loanDetail.setBookAuthor(set.getString("author"));
                loanDetail.setLoanDate(set.getDate("borrow_date").toLocalDate());
                loanDetail.setDueDate(set.getDate("due_date").toLocalDate());

                // Pengecekan null untuk return_date
                if (set.getDate("return_date") != null) {
                    loanDetail.setReturningDate(set.getDate("return_date").toLocalDate());
                }

                loanDetail.setReturned(set.getBoolean("is_returned"));
                loanDetails.add(loanDetail);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loanDetails;
    }
}
