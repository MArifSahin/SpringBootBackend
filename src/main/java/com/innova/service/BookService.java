package com.innova.service;

import com.innova.dto.response.BookResponse;
import com.innova.dto.response.DashboardBookResponse;
import com.innova.model.Book;

import java.util.Map;
import java.util.Optional;
import java.util.List;

public interface BookService {
    Book saveBook(Book book);

    BookResponse getBookResponseById(String bookId);

    Optional<Book> findBookById(String bookId);

    boolean checkBookById(String bookId);

    Map<String, DashboardBookResponse> getBooksByEditorScoreDesc();

    Map<String, DashboardBookResponse> getBooksByReviewNumberDesc();

}
