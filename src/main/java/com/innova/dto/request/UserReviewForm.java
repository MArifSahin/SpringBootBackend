package com.innova.dto.request;

import javax.validation.constraints.NotBlank;

public class UserReviewForm {

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
}
