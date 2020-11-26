package com.innova.service;

import com.innova.dto.request.EditorReviewForm;
import com.innova.dto.request.UserReviewForm;
import com.innova.dto.response.DashboardBookResponse;
import com.innova.model.Book;
import com.innova.model.BookModes;
import com.innova.model.BookReview;
import com.innova.model.User;
import com.innova.repository.BookRepository;
import com.innova.repository.BookReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class BookReviewServiceImpl implements BookReviewService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookReviewRepository bookReviewRepository;

    @Override
    public BookReview saveReview(BookReview bookReview) {
        return bookReviewRepository.save(bookReview);
    }

    @Override
    public Map<String, DashboardBookResponse> getLatestReviews() {
        List<BookReview> reviews = bookReviewRepository.findByOrderByReviewDateDesc();
        Map<String, DashboardBookResponse> bookReviews = new HashMap<>();
        Iterator<BookReview> itr = reviews.iterator();
        BookReview review;
        int length = 0;
        DashboardBookResponse dashboardBookResponse = null;
        while (itr.hasNext() && length < 5) {
            review = itr.next();
            if (!bookReviews.containsKey(review.getBook().getName())) {
                dashboardBookResponse = new DashboardBookResponse(review.getBook().getId(),
                        review.getBook().getEditorScore(),
                        review.getBook().getUserScore(),
                        review.getBook().getEditorReview().getValue().toString(),
                        review.getBook().getEditorReview().getKey().toString());
                bookReviews.put(review.getBook().getName(), dashboardBookResponse);
                length++;
            }
        }
        return bookReviews;
    }

    @Override
    public BookReview saveUserReview(UserReviewForm userReviewForm, User user) {
        Book book;
        if (bookRepository.existsById(userReviewForm.getBookId())) {
            book = bookRepository.findById(userReviewForm.getBookId()).get();
            if(bookReviewRepository.existsByBookAndUser(book, user)){
                BookReview deletedReview = bookReviewRepository.findByBookAndUser(book, user);
                int deletedUserScore = deletedReview.getScore();
                bookReviewRepository.deleteById(deletedReview.getId());
                int newScore = (book.getUserScore() * book.getReviewNumber()-deletedUserScore+ userReviewForm.getUserScore()) / (book.getReviewNumber());
                book.setUserScore(newScore);
            }
            else {
                int newScore = (book.getUserScore() * book.getReviewNumber() + userReviewForm.getUserScore()) / (book.getReviewNumber() + 1);
                book.setUserScore(newScore);
                book.setReviewNumber(book.getReviewNumber() + 1);
            }
        } else {
            book = new Book(userReviewForm.getBookId(), userReviewForm.getBookName(), 0, userReviewForm.getUserScore(), 1);
            book.hasUserReview = true;
            BookModes bookModes = new BookModes();
            bookModes.setBook(book);
            book.setBookModes(bookModes);
            bookRepository.save(book);
        }


        BookReview bookReview = new BookReview(userReviewForm.getReviewText(),
                false,
                LocalDateTime.now(),
                userReviewForm.getUserScore(),
                book,
                user);
        book.addBookReview(bookReview);
        bookReviewRepository.save(bookReview);

        return bookReview;
    }

    @Override
    public BookReview saveEditorReview(EditorReviewForm editorReviewForm, User user) {
        Book book;
        if (bookRepository.existsById(editorReviewForm.getBookId())) {
            book = bookRepository.findById(editorReviewForm.getBookId()).get();
            if (book.hasEditorReview) {
                return null;
            }
            book.setEditorScore(editorReviewForm.getEditorScore());
        } else {
            book = new Book(editorReviewForm.getBookId(), editorReviewForm.getBookName(), editorReviewForm.getEditorScore(), 0, 0);
            book.hasEditorReview = true;
            BookModes bookModes = new BookModes(editorReviewForm.getMoods());
            bookModes.setBook(book);
            book.setBookModes(bookModes);
            bookRepository.save(book);
        }

        BookReview bookReview = new BookReview(editorReviewForm.getReviewText(),
                true,
                LocalDateTime.now(),
                editorReviewForm.getEditorScore(),
                book,
                user);
        bookReviewRepository.save(bookReview);
        return  bookReview;
    }
}
