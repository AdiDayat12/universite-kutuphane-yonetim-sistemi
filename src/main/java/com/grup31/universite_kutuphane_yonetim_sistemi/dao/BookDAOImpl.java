package com.grup31.universite_kutuphane_yonetim_sistemi.dao;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Book;
import com.grup31.universite_kutuphane_yonetim_sistemi.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO{
    private final Connection connection;

    public BookDAOImpl(Connection connection) {
        this.connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public void addBook(Book book) {
        String sql = """
                INSERT INTO books (title, author, publisher,
                publication_year, edition, page, stock, available_book_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setInt(4, book.getPublicationYear());
            statement.setInt(5, book.getEdition());
            statement.setInt(6, book.getPage());
            statement.setInt(7, book.getStock());
            statement.setInt(8, book.getStock());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateBook(Book book) {
        String sql = """
                UPDATE books SET title = ?, author = ?, publisher = ?, publication_year = ?,
                edition = ?, page = ?, stock = ?, available_book_count = ? WHERE id = ?
               """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setInt(4, book.getPublicationYear());
            statement.setInt(5, book.getEdition());
            statement.setInt(6, book.getPage());
            statement.setInt(7, book.getStock());
            statement.setInt(8, book.getStock());
            statement.setInt(9, book.getBookId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteBook(String title) {
        String sql = "DELETE FROM books WHERE title = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, title);
            statement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book findById(int bookId) {
        Book book = null;
        String sql = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                book = createBook(resultSet);
            }
            return book;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book findBookByTitle(String title) {
        Book book = null;
        String sql = "SELECT * FROM books WHERE title = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                book = createBook(resultSet);
            }
            return book;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> searchBooks(String keyword) {
        List<Book> existingBooks = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title ILIKE ? OR author ILIKE ? OR publisher ILIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            statement.setString(3, "%" + keyword + "%");

            ResultSet set = statement.executeQuery();
            while (set.next()){
                existingBooks.add(createBook(set));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return existingBooks;
    }

    @Override
    public List<Book> searchBookByTitle(String title) {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title ILIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, title);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                bookList.add(createBook(set));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookList;
    }

    @Override
    public List<Book> searchBookByAuthor(String author) {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE author ILIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, author);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                bookList.add(createBook(set));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookList;
    }

    @Override
    public List<Book> searchBookPublisher(String publisher) {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE publisher ILIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, publisher);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                bookList.add(createBook(set));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookList;
    }

    @Override
    public List<Book> searchBookPublicationYear(int year) {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE publication_year = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, year);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                bookList.add(createBook(set));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookList;
    }


    @Override
    public List<Book> getAllBooks() {
        List<Book> allBooks = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                allBooks.add(createBook(resultSet));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return allBooks;
    }

    @Override
    public void updateBookCount (String title, int availableBook, int borrowedBook, int lostBook) {
        String sql = "UPDATE books " +
                "SET available_book_count = available_book_count + ?," +
                "borrowed_book_count = borrowed_book_count + ?," +
                "lost_book_count = lost_book_count + ? " +  // Correct the query
                "WHERE title = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, availableBook);
            statement.setInt(2, borrowedBook);
            statement.setInt(3, lostBook);
            statement.setString(4, title);
            statement.executeUpdate();
            System.out.println("perintah ini dieksekusi");
        } catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @Override
    public int getAvailableBook(String title) {
        return getCount(title, "available_book_count");
    }

    @Override
    public int getBorrowedBook(String title) {
        return getCount(title, "borrowed_book_count");
    }

    @Override
    public int getLostBook(String title) {
        return getCount(title, "lost_book_count");
    }


    private Book createBook (ResultSet bookFromDB) throws SQLException {
        return new Book.Builder()
                .setId(bookFromDB.getInt("id"))
                .setTitle(bookFromDB.getString("title"))
                .setAuthor(bookFromDB.getString("author"))
                .setPublisher(bookFromDB.getString("publisher"))
                .setPublicationYear(bookFromDB.getInt("publication_year"))
                .setEdition(bookFromDB.getInt("edition"))
                .setPage(bookFromDB.getInt("page"))
                .setStock(bookFromDB.getInt("stock"))
                .setAvailableBook(bookFromDB.getInt("available_book_count"))
                .setBorrowedBook(bookFromDB.getInt("borrowed_book_count"))
                .setLostBook(bookFromDB.getInt("lost_book_count"))
                .build();
    }

    private int getCount (String title, String column){
        String sql = "SELECT " + column + " FROM books WHERE title = ?";
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, title);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                result += set.getInt(column);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


}
