package com.innova.dto.response;

import java.util.AbstractMap;

public class LastReviewedBookResponse {
    private int editorScore;
    private int userScore;
    private String editorReview;
    private String editor;

    public LastReviewedBookResponse(int editorScore, int userScore, String editorReview, String editor) {
        this.editorScore = editorScore;
        this.userScore = userScore;
        this.editorReview = editorReview;
        this.editor = editor;
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
}
