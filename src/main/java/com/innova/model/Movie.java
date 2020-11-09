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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @SequenceGenerator(name = "book_seq", sequenceName = "book_seq", initialValue = 1, allocationSize = 100)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "release_date")
    private int release_date;

    @Column(name = "director")
    private String director;

    @Column(name = "editor_score")
    private int editor_score;

    @Column(name = "user_score")
    private int user_score;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mode_id", nullable = false)
    private Modes modes;

    @Column(name = "review_number")
    private int review_number;

    @OneToMany(mappedBy = "movie")
    @JsonManagedReference
    private Set<MovieReview> movieReviews = new HashSet<>();

    public Movie(@NotBlank Integer id) {
        this.id = id;
    }

    public Movie() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRelease_date() {
        return release_date;
    }

    public void setRelease_date(int release_date) {
        this.release_date = release_date;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getEditor_score() {
        return editor_score;
    }

    public void setEditor_score(int editor_score) {
        this.editor_score = editor_score;
    }

    public int getUser_score() {
        return user_score;
    }

    public void setUser_score(int user_score) {
        this.user_score = user_score;
    }

    public Modes getModes() {
        return modes;
    }

    public void setModes(Modes modes) {
        this.modes = modes;
    }

    public int getReview_number() {
        return review_number;
    }

    public void setReview_number(int review_number) {
        this.review_number = review_number;
    }

    public Set<MovieReview> getMovieReviews() {
        return movieReviews;
    }

    public void setMovieReviews(Set<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
    }
}
