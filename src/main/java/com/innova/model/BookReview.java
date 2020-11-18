package com.innova.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_review", schema = "public")
public class BookReview {

    @NotBlank
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_review_seq")
    @SequenceGenerator(name = "book_review_seq", sequenceName = "book_review_seq", initialValue = 1, allocationSize = 100)
    private Integer id;

    @Column(name = "review_text")
    private String reviewText;

    @Column(name = "is_editor_review")
    private Boolean isEditorReview;

    @Column(name = "review_date", columnDefinition = "timestamp without time zone not null")
    @NotNull
    private LocalDateTime reviewDate;

    @Column(name = "score")
    private int score;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @JsonBackReference
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    public BookReview(@NotBlank Integer id) {
        this.id = id;
    }

    public BookReview(String reviewText, Boolean isEditorReview, @NotNull LocalDateTime reviewDate, int score, Book book, User user) {
        this.reviewText = reviewText;
        this.isEditorReview = isEditorReview;
        this.reviewDate = reviewDate;
        this.score = score;
        this.book = book;
        this.user = user;
    }

    public BookReview() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Boolean isEditorReview() {
        return isEditorReview;
    }

    public void setEditorReview(Boolean editorReview) {
        isEditorReview = editorReview;
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
