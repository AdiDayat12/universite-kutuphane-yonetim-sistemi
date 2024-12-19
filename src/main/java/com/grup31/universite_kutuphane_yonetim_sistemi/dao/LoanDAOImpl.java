package com.grup31.universite_kutuphane_yonetim_sistemi.dao;


import java.sql.*;
import java.time.LocalDate;


public class LoanDAOImpl implements LoanDAO{
    private Connection connection;

    public LoanDAOImpl (Connection connection){
        this.connection = connection;
    }
    @Override
    public void insertLoan(int userId, int bookId, LocalDate borrowDate, LocalDate dueDate) {
        String sql = "INSERT INTO loans (user_id, book_id, borrow_date, due_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, userId);
            statement.setInt(2, bookId);
            statement.setDate(3, Date.valueOf(borrowDate));
            statement.setDate(4, Date.valueOf(dueDate));
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertReturnDate(int loanId, LocalDate returnDate) {
        String sql = "UPDATE loans SET return_date = ? WHERE loan_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setDate(1, Date.valueOf(returnDate));
            statement.setInt(2, loanId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateStatus(int loanId, boolean status) {
        String sql = "UPDATE loans SET is_returned = TRUE where loan_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, loanId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
