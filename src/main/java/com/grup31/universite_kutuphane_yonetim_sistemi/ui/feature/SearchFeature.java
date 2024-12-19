package com.grup31.universite_kutuphane_yonetim_sistemi.ui.feature;

import com.grup31.universite_kutuphane_yonetim_sistemi.book.Book;
import com.grup31.universite_kutuphane_yonetim_sistemi.dao.BookDAO;
import com.grup31.universite_kutuphane_yonetim_sistemi.service.strategy.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchFeature {
    public static JButton addSearchButton  (JFrame frame, DefaultTableModel tableModel, BookDAO bookDAO){
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(255, 215, 0)); // Gold color
        searchButton.setForeground(Color.BLACK);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show a dialog with search options
                String[] options = {"Title", "Author", "Publisher", "Publication Year"};
                String selectedOption = (String) JOptionPane.showInputDialog(
                        frame,
                        "Select search criteria:",
                        "Search Book",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (selectedOption != null) {
                    // Prompt user for input based on selected criteria
                    String searchTerm = JOptionPane.showInputDialog(frame,
                            "Enter " + selectedOption + ":");

                    if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                        // Create SearchContext and set the strategy based on the selected option
                        BookSearchContext searchContext = new BookSearchContext();

                        BookSearchStrategy searchStrategy = getSearchStrategy(selectedOption, bookDAO);
                        searchContext.setSearchStrategy(searchStrategy);

                        // Execute the search
                        List<Book> searchResults = searchContext.searchBooks(searchTerm.trim());

                        // Clear existing table rows
                        tableModel.setRowCount(0);

                        // Populate table with search results
                        for (int i = 0; i < searchResults.size(); i++) {
                            Book book = searchResults.get(i);
                            tableModel.addRow(new Object[]{
                                    i + 1,
                                    book.getTitle(),
                                    book.getAvailableBook(),
                                    book.getBorrowedBook(),
                                    book.getLostBook()
                            });
                        }

                        if (searchResults.isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "No books found for the given search criteria.",
                                    "Search Results", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });
        return searchButton;
    }
    private static BookSearchStrategy getSearchStrategy(String selectedOption, BookDAO bookDAO) {
        return switch (selectedOption) {
            case "Title" -> new SearchByTitle(bookDAO);
            case "Author" -> new SearchByAuthor(bookDAO);
            case "Publisher" -> new SearchByPublisher(bookDAO);
            case "Publication Year" -> new SearchByPublicationYear(bookDAO);
            default -> null; // This should never happen
        };
    }
}
