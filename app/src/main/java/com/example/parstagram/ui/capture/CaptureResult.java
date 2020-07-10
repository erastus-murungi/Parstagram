package com.example.parstagram.ui.capture;

import androidx.annotation.Nullable;

import com.example.parstagram.data.model.CreatedPost;


public class CaptureResult {

    @Nullable
    private CreatedPost success;
    @Nullable
    private Integer error;

    CaptureResult(@Nullable Integer error) {
        this.error = error;
    }

    CaptureResult(@Nullable CreatedPost success) {
        this.success = success;
    }

    @Nullable
    CreatedPost getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
