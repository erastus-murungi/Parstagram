package com.example.parstagram.data.model;

import android.graphics.Bitmap;

public class SuccessfulPost {
    private String description;
    private Bitmap photo;

    public SuccessfulPost(String description, Bitmap photo) {
        this.description = description;
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}

