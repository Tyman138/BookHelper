package com.example.bookhelper.realmEntity;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Publisher extends RealmObject {
    @Required
    private String bookId;
    private String publisher;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
