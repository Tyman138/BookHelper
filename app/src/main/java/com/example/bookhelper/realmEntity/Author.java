package com.example.bookhelper.realmEntity;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Author extends RealmObject {
    @Required
    private String id;
    private String name;
    private String yearsOfLiving;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYearsOfLiving() {
        return yearsOfLiving;
    }

    public void setYearsOfLiving(String yearsOfLiving) {
        this.yearsOfLiving = yearsOfLiving;
    }
}
