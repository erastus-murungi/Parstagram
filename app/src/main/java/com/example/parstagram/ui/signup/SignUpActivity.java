package com.example.parstagram.ui.signup;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parstagram.R;
import com.example.parstagram.databinding.ActivitySignUpBinding;
import com.example.parstagram.ui.main.MainActivity;

public class SignUpActivity extends AppCompatActivity {

    private SignUpViewModel mSignUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSignUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        final ActivitySignUpBinding signUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());


        final EditText userNameEditText = signUpBinding.username;
        final EditText passwordEditText = signUpBinding.password;
        final EditText emailEditText = signUpBinding.email;
        final EditText nameEditText = signUpBinding.name;
        final Button signUpButton = signUpBinding.signup;
        final ProgressBar loadingProgressBar = signUpBinding.loading;

        mSignUpViewModel.getSignUpFormState().observe(this, new Observer<SignUpFormState>() {
            @Override
            public void onChanged(SignUpFormState signUpFormState) {
                if (signUpFormState == null) {
                    return;
                }
                signUpButton.setEnabled(signUpFormState.isDataValid());

                if (signUpFormState.getUsernameError() != null) {
                    userNameEditText.setError(getString(signUpFormState.getUsernameError()));
                }
                if (signUpFormState.getEmailError() != null) {
                    emailEditText.setError(getString(signUpFormState.getEmailError()));
                }
                if (signUpFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(signUpFormState.getPasswordError()));
                }
                if (signUpFormState.getNameError() != null) {
                    nameEditText.setError(getString(signUpFormState.getNameError()));
                }
            }
        });

        mSignUpViewModel.getSignUpResult().observe(this, new Observer<SignUpResult>() {
            @Override
            public void onChanged(SignUpResult signUpResult) {
                if (signUpResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.VISIBLE);
                if (signUpResult.getError() != null) {
                    showSignUpFailed(signUpResult.getError());
                    loadingProgressBar.setVisibility(View.GONE);
                    passwordEditText.setText(null);
                }
                if (signUpResult.getSuccess() != null) {
                    updateUiWithUser(signUpResult.getSuccess());
                    setResult(Activity.RESULT_OK);

                    // Complete and destroy login activity once successful
                    finish();
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                mSignUpViewModel.signUpDataChanged(userNameEditText.getText().toString(),
                        nameEditText.getText().toString(), emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        userNameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);
        nameEditText.addTextChangedListener(afterTextChangedListener);

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mSignUpViewModel.signUp(userNameEditText.getText().toString(),
                            passwordEditText.getText().toString(), emailEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                mSignUpViewModel.signUp(userNameEditText.getText().toString(),
                        passwordEditText.getText().toString(), emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }


    private void showSignUpFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void updateUiWithUser(SignUpUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        goToMainActivity();
    }

    private void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

}