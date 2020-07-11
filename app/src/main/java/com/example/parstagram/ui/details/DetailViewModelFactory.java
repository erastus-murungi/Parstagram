package com.example.parstagram.ui.details;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.parstagram.data.model.LocalPost;

import org.parceler.Parcels;

public class DetailViewModelFactory implements ViewModelProvider.Factory {

    private LocalPost post;
    public DetailViewModelFactory(Parcelable userParcelable) {
        post = Parcels.unwrap(userParcelable);
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailViewModel.class)) {
            return (T) new DetailViewModel(post);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
