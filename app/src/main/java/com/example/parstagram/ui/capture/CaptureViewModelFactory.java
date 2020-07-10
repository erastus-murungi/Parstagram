package com.example.parstagram.ui.capture;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.parse.ParseUser;

public class CaptureViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CaptureViewModel.class)) {
            return (T) new CaptureViewModel(ParseUser.getCurrentUser());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
