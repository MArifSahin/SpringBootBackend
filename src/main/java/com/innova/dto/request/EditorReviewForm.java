package com.innova.dto.request;

import javax.validation.constraints.NotBlank;

public class EditorReviewForm {
    @NotBlank
    private String reviewText;

    @NotBlank
    private int editorScore;

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getEditorScore() {
        return editorScore;
    }

    public void setEditorScore(int editorScore) {
        this.editorScore = editorScore;
    }

    //TODO mode
}
