package com.innova.repository;

import com.innova.model.Book;
import com.innova.model.BookReview;
import com.innova.model.Role;
import com.innova.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, Integer> {
    boolean existsByBook(Book book);
    boolean existsByBookAndUser(Book book, User user);
    List<BookReview> findByOrderByReviewDateDesc();
    BookReview findByBookAndUser(Book book, User user);
    Integer deleteByBookAndUser(Book book, User user);
}
