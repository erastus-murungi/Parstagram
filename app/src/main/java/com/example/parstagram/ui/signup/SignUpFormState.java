package com.example.parstagram.ui.signup;

import androidx.annotation.Nullable;

public class SignUpFormState {

    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer nameError;

    private boolean isDataValid;

    SignUpFormState(@Nullable Integer usernameError, @Nullable Integer passwordError,
                    @Nullable Integer emailError, @Nullable Integer nameError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.emailError = emailError;
        this.nameError = nameError;
        this.isDataValid = false;
    }

    SignUpFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.emailError = null;
        this.nameError = null;
        this.isDataValid = isDataValid;
    }

    SignUpFormState(@Nullable Integer emailError) {
        this.emailError = emailError;
        this.usernameError = null;
        this.passwordError = null;
        this.nameError = null;
        this.isDataValid = false;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    public Integer getEmailError() {
        return emailError;
    }

    @Nullable
    public Integer getNameError() {
        return nameError;
    }

    boolean isDataValid() {
        return isDataValid;
    }

}
