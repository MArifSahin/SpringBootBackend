package com.innova.dto.request;

import javax.validation.constraints.NotBlank;

public class EditorReviewForm {
    @NotBlank
    private String bookId;

    @NotBlank
    private String bookName;

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

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    //TODO mode
}
