package com.innova.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "content_modes", schema = "public")
public class Modes {
    @NotBlank
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mode_seq")
    @SequenceGenerator(name = "mode_seq", sequenceName = "mode_seq", initialValue = 1, allocationSize = 100)
    private Integer id;

    @Column(name = "dram")
    @Size(min = 0, max = 100)
    private int drama;

    @Column(name = "comedy")
    @Size(min = 0, max = 100)
    private int comedy;

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

    public Modes(@NotBlank Integer id) {
        this.id = id;
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

    public int getComedy() {
        return comedy;
    }

    public void setComedy(int comedy) {
        this.comedy = comedy;
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
}
