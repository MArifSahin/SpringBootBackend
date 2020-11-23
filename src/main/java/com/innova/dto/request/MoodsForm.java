package com.innova.dto.request;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MoodsForm {
    @NotBlank
    private int drama;
    @NotBlank
    private int fun;
    @NotBlank
    private int action;
    @NotBlank
    private int adventure;
    @NotBlank
    private int romance;
    @NotBlank
    private int thriller;
    @NotBlank
    private int horror;

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

    @Override
    public String toString() {
        return "MoodsForm{" +
                "drama=" + drama +
                ", fun=" + fun +
                ", action=" + action +
                ", adventure=" + adventure +
                ", romance=" + romance +
                ", thriller=" + thriller +
                ", horror=" + horror +
                '}';
    }
}
