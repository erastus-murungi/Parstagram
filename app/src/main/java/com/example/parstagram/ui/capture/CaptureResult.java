package com.example.parstagram.ui.capture;

import androidx.annotation.Nullable;

import com.example.parstagram.data.model.SuccessfulPost;


public class CaptureResult {

    @Nullable
    private SuccessfulPost success;
    @Nullable
    private Integer error;

    CaptureResult(@Nullable Integer error) {
        this.error = error;
    }

    CaptureResult(@Nullable SuccessfulPost success) {
        this.success = success;
    }

    @Nullable
    SuccessfulPost getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
