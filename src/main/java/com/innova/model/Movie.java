package com.innova.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="content_movie", schema= "public")
public class Movie {

    @NotBlank
    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "release_date")
    private int releaseDate;

    @Column(name = "director")
    private String director;

    @Column(name = "editor_score")
    private int editorScore;

    @Column(name = "user_score")
    private int user_score;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mode_id", nullable = false)
    private MovieModes movieModes;

    @Column(name = "review_number")
    private int reviewNumber;

    @Column(name = "has_editor_review")
    private int hasEditorReview;

    @Column(name = "has_user_review")
    private int hasUserReview;

    @OneToMany(mappedBy = "movie")
    @JsonManagedReference
    private Set<MovieReview> movieReviews = new HashSet<>();

    public Movie() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getEditorScore() {
        return editorScore;
    }

    public void setEditorScore(int editorScore) {
        this.editorScore = editorScore;
    }

    public int getUser_score() {
        return user_score;
    }

    public void setUser_score(int user_score) {
        this.user_score = user_score;
    }

    public MovieModes getModes() {
        return movieModes;
    }

    public void setModes(MovieModes modes) {
        this.movieModes = modes;
    }

    public int getReviewNumber() {
        return reviewNumber;
    }

    public void setReviewNumber(int reviewNumber) {
        this.reviewNumber = reviewNumber;
    }

    public int getHasEditorReview() {
        return hasEditorReview;
    }

    public void setHasEditorReview(int hasEditorReview) {
        this.hasEditorReview = hasEditorReview;
    }

    public int getHasUserReview() {
        return hasUserReview;
    }

    public void setHasUserReview(int hasUserReview) {
        this.hasUserReview = hasUserReview;
    }
}
