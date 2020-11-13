package com.innova.dto.request;

import java.util.Set;

import javax.validation.constraints.*;

public class EditorApplicationForm {

    @NotBlank
    @Size(max = 60)
    @Email
    private String email;

    @Size(min = 10, max = 10)
    private String phoneNumber;

    private String name;
    private String lastname;
    private String whatDoYouWant;
    private String hobbies;
    private String education;
    private String favBook;
    private String favMovie;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getWhatDoYouWant() {
        return whatDoYouWant;
    }

    public void setWhatDoYouWant(String whatDoYouWant) {
        this.whatDoYouWant = whatDoYouWant;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getFavBook() {
        return favBook;
    }

    public void setFavBook(String favBook) {
        this.favBook = favBook;
    }

    public String getFavMovie() {
        return favMovie;
    }

    public void setFavMovie(String favMovie) {
        this.favMovie = favMovie;
    }

    @Override
    public String toString() {
        return "EditorApplicationForm{" +
                "email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", whatDoYouWant='" + whatDoYouWant + '\'' +
                ", hobbies='" + hobbies + '\'' +
                ", education='" + education + '\'' +
                ", favBook='" + favBook + '\'' +
                ", favMovie='" + favMovie + '\'' +
                '}';
    }
}