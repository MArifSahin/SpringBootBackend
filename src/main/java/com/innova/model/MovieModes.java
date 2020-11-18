package com.innova.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "movie_modes", schema = "public")
public class MovieModes {
    @NotBlank
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mode_seq")
    @SequenceGenerator(name = "mode_seq", sequenceName = "mode_seq", initialValue = 1, allocationSize = 100)
    private Integer id;

    @Column(name = "dram")
    @Size(min = 0, max = 100)
    private int drama;

    @Column(name = "fun")
    @Size(min = 0, max = 100)
    private int fun;

    @Column(name = "action")
    @Size(min = 0, max = 100)
    private int action;

    @Column(name = "adventure")
    @Size(min = 0, max = 100)
    private int adventure;

    @Column(name = "romance")
    @Size(min = 0, max = 100)
    private int romance;

    @Column(name = "thriller")
    @Size(min = 0, max = 100)
    private int thriller;

    @Column(name = "horror")
    @Size(min = 0, max = 100)
    private int horror;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    public MovieModes(@NotBlank Integer id) {
        this.id = id;
    }

    public MovieModes() {
        this.drama=0;
        this.action=0;
        this.adventure=0;
        this.fun=0;
        this.horror=0;
        this.romance=0;
        this.thriller=0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDrama() {
        return drama;
    }

    public void setDrama(int drama) {
        this.drama = drama;
    }

    public int getFun() {
        return fun;
    }

    public void setFun(int fun) {
        this.fun = fun;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getAdventure() {
        return adventure;
    }

    public void setAdventure(int adventure) {
        this.adventure = adventure;
    }

    public int getRomance() {
        return romance;
    }

    public void setRomance(int romance) {
        this.romance = romance;
    }

    public int getThriller() {
        return thriller;
    }

    public void setThriller(int thriller) {
        this.thriller = thriller;
    }

    public int getHorror() {
        return horror;
    }

    public void setHorror(int horror) {
        this.horror = horror;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

}
