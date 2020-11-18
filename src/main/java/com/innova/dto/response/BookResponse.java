package com.innova.dto.response;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;


public class BookResponse {
    private String bookName;
    private int editorScore;
    private int userScore;
    private AbstractMap.SimpleEntry editorReview;
    private Map<String, String> userReviews;
    private Map<String,Integer> modes;

    public BookResponse(String bookName, int editorScore, int userScore, AbstractMap.SimpleEntry editorReview, Map<String, String> userReviews, Map<String, Integer> modes) {
        this.bookName = bookName;
        this.editorScore = editorScore;
        this.userScore = userScore;
        this.editorReview = editorReview;
        this.userReviews = userReviews;
        this.modes = modes;
    }
    @Override
    public String toString() {
        return "BookResponse{" +
                "bookName='" + bookName + '\'' +
                ", editorScore=" + editorScore +
                ", userScore=" + userScore +
                ", editorReview='" + editorReview + '\'' +
                ", userReviews=" + userReviews +
                ", modes=" + modes +
                '}';
    }
}
