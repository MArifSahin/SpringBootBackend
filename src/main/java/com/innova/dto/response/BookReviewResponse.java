package com.innova.dto.response;

import java.time.LocalDateTime;

public class BookReviewResponse {
    private String reviewText;
    private String bookName;
    private String bookId;
    private int score;
    private LocalDateTime reviewDate;

    public BookReviewResponse(String reviewText, String bookName, String bookId, int score, LocalDateTime reviewDate) {
        this.reviewText = reviewText;
        this.bookName = bookName;
        this.bookId = bookId;
        this.score = score;
        this.reviewDate = reviewDate;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }
}
