package com.example.bookhelper.realmEntity;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Country extends RealmObject {
    @Required
    private String authorId;
    private String country;

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
