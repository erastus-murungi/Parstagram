package com.example.parstagram.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.parstagram.data.model.LocalPost;

import java.util.ArrayList;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(new ArrayList<LocalPost>());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
