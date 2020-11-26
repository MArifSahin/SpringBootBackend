package com.innova.dto.response;

import java.util.AbstractMap;

public class DashboardBookResponse {
    private int editorScore;
    private int userScore;
    private String editorReview;
    private String editor;
    private String bookId;
    private int reviewNumber;

    public DashboardBookResponse(String bookId, int editorScore, int userScore, String editorReview, String editor) {
        this.bookId = bookId;
        this.editorScore = editorScore;
        this.userScore = userScore;
        this.editorReview = editorReview;
        this.editor = editor;
    }

    public DashboardBookResponse(int editorScore, String bookId) {
        this.editorScore = editorScore;
        this.bookId = bookId;
    }

    public DashboardBookResponse(String bookId, int reviewNumber) {
        this.bookId = bookId;
        this.reviewNumber = reviewNumber;
    }

    public DashboardBookResponse(int editorScore, int userScore, String bookId) {
        this.editorScore = editorScore;
        this.userScore = userScore;
        this.bookId = bookId;
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

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getReviewNumber() {
        return reviewNumber;
    }

    public void setReviewNumber(int reviewNumber) {
        this.reviewNumber = reviewNumber;
    }
}
