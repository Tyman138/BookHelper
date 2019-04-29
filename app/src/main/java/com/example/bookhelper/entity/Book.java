package com.example.bookhelper.entity;

public class Book {
    private String name;
    private String genre;
    private String numbersOfPages;
    private String language;
    private String edition;
    private String publisher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getNumbersOfPages() {
        return numbersOfPages;
    }

    public void setNumbersOfPages(String numbersOfPages) {
        this.numbersOfPages = numbersOfPages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Book(String name, String genre, String numbersOfPages, String language, String edition, String publisher) {
        this.name = name;
        this.genre = genre;
        this.numbersOfPages = numbersOfPages;
        this.language = language;
        this.edition = edition;
        this.publisher = publisher;
    }
}
