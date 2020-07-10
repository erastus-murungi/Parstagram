package com.example.parstagram.ui.main.adapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.parstagram.data.model.Post;
import com.example.parstagram.ui.main.MainViewModel;

import java.util.ArrayList;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(new ArrayList<Post>());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
