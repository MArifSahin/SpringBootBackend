package com.innova.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "book_modes", schema = "public")
public class BookModes {
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
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public BookModes(@NotBlank Integer id) {
        this.id = id;
    }

    public BookModes() {
        this.drama=0;
        this.action=0;
        this.adventure=0;
        this.fun=0;
        this.horror=0;
        this.romance=0;
        this.thriller=0;
    }

    public BookModes(Map<String, Integer> modesMap){
        this.drama=modesMap.get("drama");
        this.action=modesMap.get("action");
        this.adventure=modesMap.get("adventure");
        this.fun=modesMap.get("fun");
        this.horror=modesMap.get("horror");
        this.romance=modesMap.get("romance");
        this.thriller=modesMap.get("thriller");
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


    public Map<String, Integer> createMap(){
        Map<String,Integer> modesMap=new HashMap<>();
        modesMap.put("drama",this.drama);
        modesMap.put("action",this.action);
        modesMap.put("adventure",this.adventure);
        modesMap.put("fun",this.fun);
        modesMap.put("horror",this.horror);
        modesMap.put("romance",this.romance);
        modesMap.put("thriller",this.thriller);
        return modesMap;
    }
}
