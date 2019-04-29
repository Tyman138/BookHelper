package com.example.bookhelper.realmEntity;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Edition extends RealmObject {
    @Required
    private String bookId;
    private String Edition;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getEdition() {
        return Edition;
    }

    public void setEdition(String edition) {
        Edition = edition;
    }
}
