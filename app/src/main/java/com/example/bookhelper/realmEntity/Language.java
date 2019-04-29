package com.example.bookhelper.realmEntity;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Language extends RealmObject {
    @Required
    private String bookId;
    private String lang;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
