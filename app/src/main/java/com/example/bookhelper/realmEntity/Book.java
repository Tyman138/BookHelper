package com.example.bookhelper.realmEntity;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Book extends RealmObject {
    @Required
   private String authorId;
   private String bookName;

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
