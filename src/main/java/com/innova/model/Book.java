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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @SequenceGenerator(name = "book_seq", sequenceName = "book_seq", initialValue = 1, allocationSize = 100)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "published_year")
    private int published_year;

    @Column(name = "author")
    private String author;

    @Column(name = "editor_score")
    private int editor_score;

    @Column(name = "user_score")
    private int user_score;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mode_id", nullable = false)
    private Modes modes;

    @Column(name = "review_number")
    private int review_number;

    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private Set<BookReview> bookReviews = new HashSet<>();

    public Book(@NotBlank Integer id) {
        this.id = id;
    }

    public Book() {

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

    public int getPublished_year() {
        return published_year;
    }

    public void setPublished_year(int published_year) {
        this.published_year = published_year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public Set<BookReview> getBookReviews() {
        return bookReviews;
    }

    public void setBookReviews(Set<BookReview> bookReviews) {
        this.bookReviews = bookReviews;
    }
}
