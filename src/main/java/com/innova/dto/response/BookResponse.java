package com.innova.dto.response;

import java.util.Map;


public class BookResponse {
    private String bookName;
    private int editorScore;
    private int userScore;
    private String editorReview;
    private String editor;
    private Map<String, String> userReviews;
    private Map<String, Integer> modes;
    private String bookId;

    public BookResponse(String bookId, String bookName, int editorScore, int userScore, String editorReview, String editor, Map<String, String> userReviews, Map<String, Integer> modes) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.editorScore = editorScore;
        this.userScore = userScore;
        this.editorReview = editorReview;
        this.editor = editor;
        this.userReviews = userReviews;
        this.modes = modes;
    }

    public BookResponse() {
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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getEditorScore() {
        return editorScore;
    }

    public void setEditorScore(int editorScore) {
        this.editorScore = editorScore;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public String getEditorReview() {
        return editorReview;
    }

    public void setEditorReview(String editorReview) {
        this.editorReview = editorReview;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public Map<String, String> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(Map<String, String> userReviews) {
        this.userReviews = userReviews;
    }

    public Map<String, Integer> getModes() {
        return modes;
    }

    public void setModes(Map<String, Integer> modes) {
        this.modes = modes;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
