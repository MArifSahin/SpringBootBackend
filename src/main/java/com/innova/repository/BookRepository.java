package com.innova.repository;

import com.innova.model.Book;
import com.innova.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findAllByOrderByUserScoreDesc();
    List<Book> findAllByOrderByEditorScoreDesc();
    List<Book> findAllByOrderByReviewNumberDesc();

}
