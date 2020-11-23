package com.innova.dto.response;

public class BooksOfYourMoodResponse {
    private String bookId;
    private String bookName;
    private int editorScore;
    private int userScore;

    public BooksOfYourMoodResponse(String bookId, String bookName, int editorScore, int userScore) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.editorScore = editorScore;
        this.userScore = userScore;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
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
}
