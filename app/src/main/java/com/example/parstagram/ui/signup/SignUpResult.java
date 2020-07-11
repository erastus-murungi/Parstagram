package com.example.parstagram.ui.signup;

import androidx.annotation.Nullable;

import com.example.parstagram.ui.signup.SignUpUserView;

public class SignUpResult {
    @Nullable
    private SignUpUserView success;
    @Nullable
    private Integer error;

    SignUpResult(@Nullable Integer error) {
        this.error = error;
    }

    SignUpResult(@Nullable SignUpUserView success) {
        this.success = success;
    }

    @Nullable
    SignUpUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }

}
