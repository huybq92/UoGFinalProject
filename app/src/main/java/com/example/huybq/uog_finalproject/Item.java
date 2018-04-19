package com.example.huybq.uog_finalproject;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Item {

    //public String itemId;
    public String userId;
    public String name;
    public String description;
    public String location;
    public String[] images;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Item() {
    }

    public Item(String userId, String name, String description, String location, String[] images) {
        //this.itemId = itemId;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.location = location;
        this.images = images;
    }

    public Item(String userId, String name, String description, String location) {
        //this.itemId = itemId;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.location = location;
    }
}
