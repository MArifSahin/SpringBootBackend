package com.innova.controller;

import com.innova.constants.ErrorCodes;
import com.innova.dto.request.EditorReviewForm;
import com.innova.dto.request.UserReviewForm;
import com.innova.exception.BadRequestException;
import com.innova.model.Book;
import com.innova.model.BookReview;
import com.innova.model.User;
import com.innova.repository.BookRepository;
import com.innova.repository.BookReviewRepository;
import com.innova.repository.UserRepository;
import com.innova.security.services.UserDetailImpl;
import com.innova.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    public ResponseEntity<?> getBook(@RequestParam("book-id") String bookId) {
        if (bookId == null) {
            return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("http://localhost:4200/book")).build();
        } else {
            if(bookRepository.existsById(bookId)){
                Book book = bookRepository.findById(bookId).get();
                return ResponseEntity.ok().body(book);
            }
            else{
                throw new BadRequestException("No such book", ErrorCodes.NO_SUCH_BOOK);
            }
        }
    }

    @PostMapping("/write-editor-review")
    public ResponseEntity<?> writeEditorReview(@RequestParam("book-id") String bookId,
                                               @RequestParam("book-name") String bookName,
                                               @RequestBody EditorReviewForm editorReviewForm){
        User user = userServiceImpl.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        if (bookId == null) {
            return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("http://localhost:4200/book")).build();
        }
        else{
            Book book;
            if(bookRepository.existsById(bookId)){
                book = bookRepository.findById(bookId).get();
            }
            else{
                book = new Book(bookId,bookName,0,0,0);
                book.setHasEditorReview(true);
                bookRepository.save(book);
            }

            BookReview bookReview = new BookReview(editorReviewForm.getReviewText(),
                    false,
                    null,
                    editorReviewForm.getEditorScore(),
                    book,
                    user);
            bookReviewRepository.save(bookReview);
            book.setReviewNumber(book.getReviewNumber()+1);
            return ResponseEntity.ok().body(bookReview);
        }
    }

    @PostMapping("/write-user-review")
    public ResponseEntity<?> writeUserReview(@RequestParam("book-id") String bookId,
                                             @RequestParam("book-name") String bookName,
                                             @RequestBody UserReviewForm userReviewForm){
        User user = userServiceImpl.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        if (bookId == null) {
            return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("http://localhost:4200/book")).build();
        }
        else{
            Book book;
            if(bookRepository.existsById(bookId)){
                book = bookRepository.findById(bookId).get();
            }
            else{
                book = new Book(bookId,bookName,0,0,0);
                book.setHasUserReview(true);
                bookRepository.save(book);
            }

            BookReview bookReview = new BookReview(userReviewForm.getReviewText(),
                    false,
                    null,
                    userReviewForm.getUserScore(),
                    book,
                    user);
            bookReviewRepository.save(bookReview);
            book.setReviewNumber(book.getReviewNumber()+1);
            return ResponseEntity.ok().body(bookReview);
        }
    }

}
