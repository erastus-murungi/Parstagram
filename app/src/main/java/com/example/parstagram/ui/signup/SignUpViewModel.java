package com.example.parstagram.ui.signup;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parstagram.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUpViewModel extends ViewModel {

    private MutableLiveData<SignUpFormState> signUpFormState = new MutableLiveData<>();
    private MutableLiveData<SignUpResult> signUpResult = new MutableLiveData<>();

    LiveData<SignUpFormState> getSignUpFormState() {
        return signUpFormState;
    }

    LiveData<SignUpResult> getSignUpResult() {
        return signUpResult;
    }

    public void signUp(String username, String password, String email, String name) {
        // try logging in a separate asynchronous job
        final ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.put("name", name);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    signUpResult.setValue(new SignUpResult(new SignUpUserView(user.getUsername())));
                } else {
                    signUpResult.setValue(new SignUpResult(R.string.sign_up_failed));
                }
            }
        });
    }

    public void signUpDataChanged(String username, String name, String email, String password) {
        if (!isUserNameValid(username)) {
            signUpFormState.setValue(new SignUpFormState(R.string.invalid_username, null, null, null));
        } else if (!isEmailValid(email)) {
            signUpFormState.setValue(new SignUpFormState(R.string.invalid_email));
        } else if (!isPasswordValid(password)) {
            signUpFormState.setValue(new SignUpFormState(null, R.string.invalid_password, null, null));
        } else {
            signUpFormState.setValue(new SignUpFormState(true));
        }
    }

    private boolean isNameValid(String name) {
        return name != null;
    }

    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() >= 8;
    }
}
