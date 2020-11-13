package com.innova.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="content_book", schema= "public")
public class Book {

    @NotBlank
    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "published_year")
    private int publishedYear;

    @Column(name = "author")
    private String author;

    @Column(name = "editor_score")
    private int editorScore;

    @Column(name = "user_score")
    private int userScore;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mode_id", nullable = false)
    private Modes modes;

    @Column(name = "has_editor_review")
    private boolean hasEditorReview;

    @Column(name = "has_user_review")
    private boolean hasUserReview;

    @Column(name = "review_number")
    private int reviewNumber;

    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private Set<BookReview> bookReviews = new HashSet<>();

    public Book() {

    }

    public Book(@NotBlank String id, String name,int editorScore, int userScore, int reviewNumber) {
        this.id = id;
        this.name = name;
        this.editorScore = editorScore;
        this.userScore = userScore;
        this.reviewNumber = reviewNumber;
    }

    public void addBookReview(BookReview bookReview) {
        bookReviews.add(bookReview);
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

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public Modes getModes() {
        return modes;
    }

    public void setModes(Modes modes) {
        this.modes = modes;
    }

    public boolean isHasEditorReview() {
        return hasEditorReview;
    }

    public void setHasEditorReview(boolean hasEditorReview) {
        this.hasEditorReview = hasEditorReview;
    }

    public int getReviewNumber() {
        return reviewNumber;
    }

    public void setReviewNumber(int reviewNumber) {
        this.reviewNumber = reviewNumber;
    }

    public boolean isHasUserReview() {
        return hasUserReview;
    }

    public void setHasUserReview(boolean hasUserReview) {
        this.hasUserReview = hasUserReview;
    }



    public Set<BookReview> getBookReviews() {
        return bookReviews;
    }

    public void setBookReviews(Set<BookReview> bookReviews) {
        this.bookReviews = bookReviews;
    }
}
