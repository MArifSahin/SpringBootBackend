package com.innova.controller;

import com.innova.constants.ErrorCodes;
import com.innova.dto.request.EditorReviewForm;
import com.innova.dto.request.MoodsForm;
import com.innova.dto.request.UserReviewForm;
import com.innova.dto.response.BookResponse;

import com.innova.dto.response.BookReviewResponse;
import com.innova.dto.response.DashboardBookResponse;
import com.innova.exception.BadRequestException;
import com.innova.model.*;
import com.innova.repository.BookModesRepository;
import com.innova.repository.BookRepository;
import com.innova.repository.BookReviewRepository;
import com.innova.repository.UserRepository;
import com.innova.service.BookModesService;
import com.innova.service.BookReviewService;
import com.innova.service.BookService;
import com.innova.service.UserService;

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
    BookService bookService;

    @Autowired
    BookReviewService bookReviewService;

    @Autowired
    BookModesService bookModesService;

    @Autowired
    BookReviewRepository bookReviewRepository;

    @Autowired
    BookModesRepository bookModesRepository;

    @Autowired
    UserService userService;

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> getBook(@PathVariable String bookId) {
        if (bookId == null) {
            return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("https://book-review-backend.herokuapp.com/#/book")).build();
        } else {
            if (bookService.checkBookById(bookId)) {
                BookResponse bookResponse = bookService.getBookResponseById(bookId);
                return ResponseEntity.ok().body(bookResponse);
            } else {
                throw new BadRequestException("No such book", ErrorCodes.NO_SUCH_BOOK);
            }
        }
    }

    @PostMapping("/write-editor-review")
    public ResponseEntity<?> writeEditorReview(@RequestBody EditorReviewForm editorReviewForm) throws IOException {
        User user = userService.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        if (!user.getRoles().iterator().next().getRole().equals(Roles.ROLE_EDITOR)) {
            return new ResponseEntity<>("Only editors can write editor review!", HttpStatus.BAD_REQUEST);
        }
        if (editorReviewForm.getBookId() == null) {
            return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("https://book-review-backend.herokuapp.com/#/book")).build();
        } else {
            if (bookReviewService.saveEditorReview(editorReviewForm, user) == null) {
                return new ResponseEntity<>("This book already have an editor review!", HttpStatus.BAD_REQUEST);
            } else {
                return ResponseEntity.ok().body(bookReviewService.saveEditorReview(editorReviewForm, user));
            }
        }
    }

    @PostMapping("/write-user-review")
    public ResponseEntity<?> writeUserReview(@RequestBody UserReviewForm userReviewForm) {
        User user = userService.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        if (userReviewForm.getBookId() == null) {
            return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("https://book-review-backend.herokuapp.com/#/book")).build();
        } else {
            return ResponseEntity.ok().body(bookReviewService.saveUserReview(userReviewForm, user));
        }
    }

    @GetMapping("/last-reviews")
    public ResponseEntity<Map<String, DashboardBookResponse>> lastReviews() {
        return ResponseEntity.ok().body(bookReviewService.getLatestReviews());
    }

    @GetMapping("/highest-rated")
    public ResponseEntity<Map<String, DashboardBookResponse>> highestRated() {
        return ResponseEntity.ok().body(bookService.getBooksByEditorScoreDesc());
    }

    @GetMapping("/highest-reviewed")
    public ResponseEntity<Map<String, DashboardBookResponse>> highestReviewed() {
        return ResponseEntity.ok().body(bookService.getBooksByReviewNumberDesc());
    }

    @PostMapping("/find-book-of-mood")
    public ResponseEntity<Map<String, DashboardBookResponse>> findBookOfMood(@RequestBody MoodsForm moodsForm) {
        return ResponseEntity.ok().body(bookModesService.getBookOfMood(moodsForm));
    }

    @GetMapping("/my-reviews")
    public ResponseEntity<Map<String, BookReviewResponse>> getUserReviews() {
        User user = userService.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok().body(bookReviewService.getReviewsByUser(user));
    }

}
