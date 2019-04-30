package com.example.bookhelper.entity;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Author extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private String country;
    private String yearsOfLiving;
    private RealmList<Book> books; //relationships one to many

    public Author() {
    }

    public Author(String name, String country, String yearsOfLiving) {
        this.name = name;
        this.country = country;
        this.yearsOfLiving = yearsOfLiving;
    }

    public RealmList<Book> getBooks() {
        return books;
    }

    public void setBooks(RealmList<Book> books) {
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getYearsOfLiving() {
        return yearsOfLiving;
    }

    public void setYearsOfLiving(String yearsOfLiving) {
        this.yearsOfLiving = yearsOfLiving;
    }


}
