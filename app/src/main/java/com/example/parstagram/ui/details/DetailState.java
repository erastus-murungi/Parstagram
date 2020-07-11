package com.example.parstagram.ui.details;

import androidx.annotation.Nullable;

public class DetailState {

    public boolean isImageReady() {
        return imageReady;
    }

    public void setImageReady(boolean imageReady) {
        this.imageReady = imageReady;
    }

    public boolean isDescriptionReady() {
        return descriptionReady;
    }

    public void setDescriptionReady(boolean descriptionReady) {
        this.descriptionReady = descriptionReady;
    }

    @Nullable
    public Integer getError() {
        return error;
    }

    public void setError(@Nullable Integer error) {
        this.error = error;
    }

    private boolean imageReady = false;
    private boolean descriptionReady = false;
    private boolean creationTimeReady = false;


    @Nullable
    private Integer error;


    public DetailState(boolean imageReady, boolean descriptionReady, @Nullable Integer error) {
        this.imageReady = imageReady;
        this.descriptionReady = descriptionReady;
        this.error = error;
    }

    public DetailState(@Nullable Integer error) {
        this.error = error;
        imageReady = false;
        descriptionReady = false;
    }

    public boolean isCreationTimeReady() {
        return creationTimeReady;
    }

    public DetailState(boolean imageReady, boolean descriptionReady, boolean creationTimeReady) {
        this.imageReady = imageReady;
        this.descriptionReady = descriptionReady;
        this.error = null;
    }


}
