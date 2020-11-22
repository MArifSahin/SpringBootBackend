package com.innova.dto.response;

import java.util.AbstractMap;

public class LastReviewedBookResponse {
    private int editorScore;
    private int userScore;
    private AbstractMap.SimpleEntry editorReview;

    public LastReviewedBookResponse(int editorScore, int userScore, AbstractMap.SimpleEntry editorReview) {
        this.editorScore = editorScore;
        this.userScore = userScore;
        this.editorReview = editorReview;
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

    public AbstractMap.SimpleEntry getEditorReview() {
        return editorReview;
    }

    public void setEditorReview(AbstractMap.SimpleEntry editorReview) {
        this.editorReview = editorReview;
    }
}
