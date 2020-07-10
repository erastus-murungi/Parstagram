package com.example.parstagram.data.model;

import java.io.File;

public class CreatedPost {
    private String description;
    private File photo;

    public CreatedPost(String description, File photo) {
        this.description = description;
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }
}

