package com.example.parstagram.ui.signup;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parstagram.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
                    Log.e("SignUp", "", e);
                    signUpResult.setValue(new SignUpResult(e.getMessage()));
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
        } else if (!isNameValid(name)) {
            signUpFormState.setValue(new SignUpFormState(R.string.invalid_name));
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

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() >= 8;
    }
}
