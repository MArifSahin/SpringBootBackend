package com.innova.repository;

import com.innova.model.Book;
import com.innova.model.BookModes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookModesRepository extends JpaRepository<BookModes, Integer> {

    @Query("SELECT m FROM BookModes m WHERE m.drama = ?1 and m.fun = ?2 and m.action = ?3 and m.adventure = ?4 and m.romance = ?5 and m.thriller = ?6 and m.horror = ?7")
    List<BookModes> findAllBooksByModes(int drama, int fun, int action, int adventure, int romance, int thriller, int horror);
}
