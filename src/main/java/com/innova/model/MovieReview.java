package com.innova.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name="movie_review", schema = "public")
public class MovieReview {

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

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonBackReference
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    public MovieReview() {
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

    public Boolean getEditorReview() {
        return isEditorReview;
    }

    public void setEditorReview(Boolean editorReview) {
        isEditorReview = editorReview;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
