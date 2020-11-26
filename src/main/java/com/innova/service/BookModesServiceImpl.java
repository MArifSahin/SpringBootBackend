package com.innova.service;

import com.innova.dto.request.MoodsForm;
import com.innova.dto.response.DashboardBookResponse;
import com.innova.model.Book;
import com.innova.model.BookModes;
import com.innova.repository.BookModesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookModesServiceImpl implements BookModesService {

    @Autowired
    BookModesRepository bookModesRepository;

    @Override
    public Map<String, DashboardBookResponse> getBookOfMood(MoodsForm moodsForm) {
        List<BookModes> bookModes = bookModesRepository.findAllBooksByModes(moodsForm.getDrama(),
                moodsForm.getFun(), moodsForm.getAction(), moodsForm.getAdventure(), moodsForm.getRomance(), moodsForm.getThriller(), moodsForm.getHorror());
        Iterator<BookModes> iterator = bookModes.iterator();
        Map<String,DashboardBookResponse> booksOfYourMood = new LinkedHashMap<>();
        DashboardBookResponse dashboardBookResponse = null;
        while (iterator.hasNext()) {
            Book book = iterator.next().getBook();
            dashboardBookResponse = new DashboardBookResponse(book.getEditorScore(), book.getUserScore(), book.getId());
            booksOfYourMood.put(book.getName(),dashboardBookResponse);
        }
        return booksOfYourMood;
    }
}
