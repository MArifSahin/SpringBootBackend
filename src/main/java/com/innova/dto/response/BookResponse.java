package com.innova.dto.response;

import java.util.Map;
import java.util.Set;


public class BookResponse {
    private String bookName;
    private int editorScore;
    private int userScore;
    private String editorReview;
    private Set<String> userReviews;
    private Map<String,Integer> modes;

    public BookResponse(String bookName, int editorScore, int userScore, String editorReview, Set<String> userReviews, Map<String, Integer> modes) {
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
