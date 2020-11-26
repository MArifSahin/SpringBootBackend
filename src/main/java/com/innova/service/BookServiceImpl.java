package com.innova.service;

import com.innova.dto.response.BookResponse;
import com.innova.dto.response.DashboardBookResponse;
import com.innova.model.Book;
import com.innova.model.BookReview;
import com.innova.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public BookResponse getBookResponseById(String bookId) {
        Book book = bookRepository.findById(bookId).get();
        Iterator<BookReview> itr = book.getBookReviews().iterator();
        Map<String, String> userReviews = new HashMap<>();
        String editorReview = null;
        String editor = null;
        while (itr.hasNext()) {
            BookReview itrReview = itr.next();
            if (itrReview.isEditorReview()) {
                editorReview = itrReview.getReviewText();
                editor = itrReview.getUser().getUsername();
            } else {
                userReviews.put(itrReview.getUser().getUsername(), itrReview.getReviewText());
            }
        }
        BookResponse bookResponse = new BookResponse(
                book.getId(), book.getName(), book.getEditorScore(), book.getUserScore(), editorReview, editor, userReviews, book.getModes().createMap()
        );
        return bookResponse;
    }

    @Override
    public Optional<Book> findBookById(String bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public boolean checkBookById(String bookId) {
        return bookRepository.existsById(bookId);
    }

    @Override
    public Map<String, DashboardBookResponse> getBooksByEditorScoreDesc() {
        List<Book> books = bookRepository.findAllByOrderByEditorScoreDesc();

        Map<String, DashboardBookResponse> highestRatedBooks = new LinkedHashMap<>();
        Iterator<Book> itr = books.iterator();
        int length = 0;
        DashboardBookResponse dashboardBookResponse = null;
        while (itr.hasNext() && length < 5) {
            Book book = itr.next();
            dashboardBookResponse = new DashboardBookResponse(book.getEditorScore(), book.getId());
            highestRatedBooks.put(book.getName(), dashboardBookResponse);
            length++;
        }
        return highestRatedBooks;
    }

    @Override
    public Map<String, DashboardBookResponse> getBooksByReviewNumberDesc() {
        List<Book> books = bookRepository.findAllByOrderByReviewNumberDesc();

        Map<String, DashboardBookResponse> highestReviewedBooks = new LinkedHashMap<>();
        Iterator<Book> itr = books.iterator();
        int length = 0;
        DashboardBookResponse dashboardBookResponse = null;
        while (itr.hasNext() && length < 5) {
            Book book = itr.next();
            dashboardBookResponse = new DashboardBookResponse(book.getId(), book.getReviewNumber());
            highestReviewedBooks.put(book.getName(), dashboardBookResponse);
            length++;
        }
        return highestReviewedBooks;
    }
}
