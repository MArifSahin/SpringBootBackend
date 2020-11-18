package com.innova.dto.request;

import javax.validation.constraints.NotBlank;

public class UserReviewForm {

    @NotBlank
    private String bookId;

    @NotBlank
    private String bookName;

    @NotBlank
    private String reviewText;

    @NotBlank
    private int userScore;

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
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

    @Override
    public String toString() {
        return "UserReviewForm{" +
                "bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", reviewText='" + reviewText + '\'' +
                ", userScore=" + userScore +
                '}';
    }
}
