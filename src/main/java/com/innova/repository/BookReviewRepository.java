package com.innova.repository;

import com.innova.model.Book;
import com.innova.model.BookReview;
import com.innova.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookReviewRepository extends JpaRepository<BookReview, Integer> {
    boolean existsByBook(Book book);
    List<BookReview> findByOrderByReviewDateDesc();
}
