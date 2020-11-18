package com.innova.controller;

import com.innova.constants.ErrorCodes;
import com.innova.dto.request.EditorReviewForm;
import com.innova.dto.request.UserReviewForm;
import com.innova.dto.response.BookResponse;
import com.innova.exception.BadRequestException;
import com.innova.model.Book;
import com.innova.model.BookModes;
import com.innova.model.BookReview;
import com.innova.model.User;
import com.innova.repository.BookRepository;
import com.innova.repository.BookReviewRepository;
import com.innova.repository.UserRepository;
import com.innova.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
    public ResponseEntity<BookResponse> getBook(@RequestParam("book-id") String bookId) {
        if (bookId == null) {
            return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("http://localhost:4200/book")).build();
        } else {
            if(bookRepository.existsById(bookId)){
                Book book = bookRepository.findById(bookId).get();
                Iterator<BookReview> itr = book.getBookReviews().iterator();
                Set<String> userReviews=new HashSet<>();
                String editorReview="";
                while(itr.hasNext()){
                    BookReview itrReview=itr.next();
                    if(itrReview.isEditorReview()){
                        editorReview=itrReview.getReviewText();
                    }
                    else{
                        userReviews.add(itrReview.getReviewText());
                    }
                }

                BookResponse bookResponse=new BookResponse(
                        book.getName(),book.getEditorScore(),book.getUserScore(),editorReview,userReviews,book.getModes().createMap()
                );
                System.out.println(bookResponse);
                return ResponseEntity.ok().body(bookResponse);
            }
            else{
                throw new BadRequestException("No such book", ErrorCodes.NO_SUCH_BOOK);
            }
        }
    }

    @PostMapping("/write-editor-review")
    public ResponseEntity<?> writeEditorReview(@RequestBody EditorReviewForm editorReviewForm){
        User user = userServiceImpl.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        if (editorReviewForm.getBookId() == null) {
            return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("http://localhost:4200/book")).build();
        }
        else{
            Book book;
            if(bookRepository.existsById(editorReviewForm.getBookId())){
                book = bookRepository.findById(editorReviewForm.getBookId()).get();
            }
            else{
                book = new Book(editorReviewForm.getBookId(),editorReviewForm.getBookName(),0,0,0);
                book.setHasEditorReview(true);
                //TODO get modes
                BookModes bookModes = new BookModes();
                bookModes.setBook(book);
                book.setBookModes(bookModes);
                bookRepository.save(book);
            }

            BookReview bookReview = new BookReview(editorReviewForm.getReviewText(),
                    true,
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
    public ResponseEntity<?> writeUserReview(@RequestBody UserReviewForm userReviewForm){
        User user = userServiceImpl.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        if (userReviewForm.getBookId() == null) {
            return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("http://localhost:4200/book")).build();
        }
        else{
            Book book;
            if(bookRepository.existsById(userReviewForm.getBookId())){
                book = bookRepository.findById(userReviewForm.getBookId()).get();
                int newScore=(book.getUserScore()*book.getReviewNumber()+userReviewForm.getUserScore())/(book.getReviewNumber()+1);
                book.setUserScore(newScore);
                book.setReviewNumber(book.getReviewNumber()+1);
            }
            else{
                book = new Book(userReviewForm.getBookId(),userReviewForm.getBookName(),0, userReviewForm.getUserScore(), 1);
                book.setHasUserReview(true);
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

}
