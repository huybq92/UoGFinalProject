package com.example.huybq.uog_finalproject;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Item {

    public String itemId;
    public String userId;
    public String name;
    public String description;
    public String location;
    public String images;
    public String username;
    public String userPhoto;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Item() {
    }

    public Item(String itemId, String userId, String name, String description, String location, String images, String username, String userPhoto) {
        this.itemId = itemId;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.location = location;
        this.images = images;
        this.username = username;
        this.userPhoto = userPhoto;
    }
}
