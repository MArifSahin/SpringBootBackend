package com.innova.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Iterator;
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

    @OneToOne(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private BookModes bookModes;

    @Column(name = "has_editor_review")
    public boolean hasEditorReview;

    @Column(name = "has_user_review")
    public boolean hasUserReview;

    @Column(name = "review_number")
    private int reviewNumber;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, orphanRemoval = true)
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

    public BookModes getModes() {
        return bookModes;
    }

    public void setModes(BookModes modes) {
        this.bookModes = modes;
    }


    public int getReviewNumber() {
        return reviewNumber;
    }

    public void setReviewNumber(int reviewNumber) {
        this.reviewNumber = reviewNumber;
    }

    public Set<BookReview> getBookReviews() {
        return bookReviews;
    }

    public void setBookReviews(Set<BookReview> bookReviews) {
        this.bookReviews = bookReviews;
    }

    public BookModes getBookModes() {
        return bookModes;
    }

    public void setBookModes(BookModes bookModes) {
        this.bookModes = bookModes;
    }

    public AbstractMap.SimpleEntry getEditorReview(){
        AbstractMap.SimpleEntry editorReview = null;
        Iterator<BookReview> itr=this.bookReviews.iterator();
        while (itr.hasNext()) {
            BookReview itrReview = itr.next();
            if (itrReview.isEditorReview()) {
                editorReview = new AbstractMap.SimpleEntry(itrReview.getUser().getUsername(), itrReview.getReviewText());
                return editorReview;
            }
        }
        return new AbstractMap.SimpleEntry("", "");
    }
}
