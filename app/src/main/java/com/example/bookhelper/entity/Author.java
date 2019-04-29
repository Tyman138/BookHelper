package com.example.bookhelper.entity;

public class Author {
    private String name;
    private String country;
    private String yearsOfLiving;

    public Author() {
    }

    public Author(String name, String country, String yearsOfLiving) {
        this.name = name;
        this.country = country;
        this.yearsOfLiving = yearsOfLiving;
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
