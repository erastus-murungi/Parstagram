package com.example.parstagram.ui.capture;

import androidx.annotation.Nullable;


class CapturePostState {
    @Nullable
    private Integer descriptionError;
    @Nullable
    private Integer photoFileError;
    private boolean imageReady;

    CapturePostState(@Nullable Integer descriptionError) {
        this.descriptionError = descriptionError;
        this.photoFileError = null;
        this.imageReady = false;
    }

    CapturePostState(@Nullable Integer descriptionError, @Nullable Integer photoFileError) {
        this.descriptionError = descriptionError;
        this.photoFileError = photoFileError;
        this.imageReady = false;
    }

    CapturePostState(boolean imageReady) {
        this.descriptionError = null;
        this.photoFileError = null;
        this.imageReady = imageReady;
    }

    @Nullable
    Integer getDescriptionError() {
        return descriptionError;
    }

    @Nullable
    Integer getPhotoFileError() {
        return photoFileError;
    }

    boolean isImageReady() {
        return imageReady;
    }

}
