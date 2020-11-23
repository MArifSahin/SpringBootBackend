package com.innova.controller;

import com.innova.constants.ErrorCodes;
import com.innova.dto.request.EditorReviewForm;
import com.innova.dto.request.UserReviewForm;
import com.innova.dto.response.BookResponse;
import com.innova.dto.response.LastReviewedBookResponse;
import com.innova.exception.BadRequestException;
import com.innova.model.*;
import com.innova.repository.BookRepository;
import com.innova.repository.BookReviewRepository;
import com.innova.repository.UserRepository;
import com.innova.service.UserServiceImpl;
import com.innova.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("api/book")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookReviewRepository bookReviewRepository;

    @Autowired
    UserServiceImpl userServiceImpl;


    @GetMapping("/")
    public ResponseEntity<BookResponse> getBook(@RequestParam("bookId") String bookId) {
        if (bookId == null) {
            return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("https://book-review-backend.herokuapp.com/#/book")).build();
        } else {
            if (bookRepository.existsById(bookId)) {
                Book book = bookRepository.findById(bookId).get();
                Iterator<BookReview> itr = book.getBookReviews().iterator();
                Map<String, String> userReviews = new HashMap<>();
                AbstractMap.SimpleEntry editorReview = null;
                while (itr.hasNext()) {
                    BookReview itrReview = itr.next();
                    if (itrReview.isEditorReview()) {
                        editorReview = new AbstractMap.SimpleEntry(itrReview.getUser().getUsername(), itrReview.getReviewText());
                    } else {
                        userReviews.put(itrReview.getUser().getUsername(), itrReview.getReviewText());
                    }
                }

                BookResponse bookResponse = new BookResponse(
                        book.getName(), book.getEditorScore(), book.getUserScore(), editorReview, userReviews, book.getModes().createMap()
                );
                return ResponseEntity.ok().body(bookResponse);
            } else {
                throw new BadRequestException("No such book", ErrorCodes.NO_SUCH_BOOK);
            }
        }
    }

    @PostMapping("/write-editor-review")
    public ResponseEntity<?> writeEditorReview(@RequestBody EditorReviewForm editorReviewForm) throws IOException {
        User user = userServiceImpl.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());

        if (!user.getRoles().iterator().next().getRole().equals(Roles.ROLE_EDITOR)) {
            return new ResponseEntity<String>("Only editors can write editor review!", HttpStatus.BAD_REQUEST);
        }
        if (editorReviewForm.getBookId() == null) {
            return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("https://book-review-backend.herokuapp.com/#/book")).build();
        } else {
            Book book;
            if (bookRepository.existsById(editorReviewForm.getBookId())) {
                book = bookRepository.findById(editorReviewForm.getBookId()).get();
                if (book.hasEditorReview) {
                    return new ResponseEntity<String>("This book already have an editor review!", HttpStatus.BAD_REQUEST);
                }
                book.setEditorScore(editorReviewForm.getEditorScore());
            } else {
                book = new Book(editorReviewForm.getBookId(), editorReviewForm.getBookName(), editorReviewForm.getEditorScore(), 0, 0);
                book.hasEditorReview = true;
                //TODO get modes
                BookModes bookModes = new BookModes();
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
            return ResponseEntity.ok().body(bookReview);
        }
    }

    @PostMapping("/write-user-review")
    public ResponseEntity<?> writeUserReview(@RequestBody UserReviewForm userReviewForm) {
        User user = userServiceImpl.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        if (userReviewForm.getBookId() == null) {
            return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("https://book-review-backend.herokuapp.com/#/book")).build();
        } else {
            Book book;
            if (bookRepository.existsById(userReviewForm.getBookId())) {
                book = bookRepository.findById(userReviewForm.getBookId()).get();
                int newScore = (book.getUserScore() * book.getReviewNumber() + userReviewForm.getUserScore()) / (book.getReviewNumber() + 1);
                book.setUserScore(newScore);
                book.setReviewNumber(book.getReviewNumber() + 1);
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
            bookReviewRepository.save(bookReview);
            return ResponseEntity.ok().body(bookReview);
        }
    }

    @GetMapping("/last-reviews")
    public ResponseEntity<Map<String, LastReviewedBookResponse>> lastReviews() {
        List<BookReview> reviews = bookReviewRepository.findByOrderByReviewDateDesc();
        Map<String, LastReviewedBookResponse> bookReviews = new HashMap<>();
        Iterator<BookReview> itr = reviews.iterator();
        BookReview review;
        int length = 0;
        LastReviewedBookResponse lastReviewedBookResponse = null;
        while (itr.hasNext() && length <= 5) {
            review = itr.next();
            if (!bookReviews.containsKey(review.getBook().getName())) {
                 lastReviewedBookResponse = new LastReviewedBookResponse(review.getBook().getEditorScore(),
                        review.getBook().getUserScore(),
                        review.getBook().getEditorReview().getValue().toString(),
                        review.getBook().getEditorReview().getKey().toString());
                bookReviews.put(review.getBook().getName(), lastReviewedBookResponse);
                length++;
            }
        }
        return ResponseEntity.ok().body(bookReviews);
    }

    @GetMapping("/highest-rated")
    public ResponseEntity<Map<String, Integer>> highestRated() {
        List<Book> books = bookRepository.findAllByOrderByUserScoreDesc();

        Map<String, Integer> highestRatedBooks = new LinkedHashMap<>();
        Iterator<Book> itr = books.iterator();
        int length = 0;
        while (itr.hasNext() && length <= 5) {
            Book book = itr.next();
            highestRatedBooks.put(book.getName(), book.getUserScore());
        }
        return ResponseEntity.ok().body(highestRatedBooks);
    }

    @GetMapping("/highest-reviewed")
    public ResponseEntity<Map<String, Integer>> highestReviewed() {
        List<Book> books = bookRepository.findAllByOrderByReviewNumberDesc();

        Map<String, Integer> highestReviewedBooks = new LinkedHashMap<>();
        Iterator<Book> itr = books.iterator();
        int length = 0;
        while (itr.hasNext() && length <= 5) {
            Book book = itr.next();
            highestReviewedBooks.put(book.getName(), book.getReviewNumber());
        }
        return ResponseEntity.ok().body(highestReviewedBooks);
    }

}
