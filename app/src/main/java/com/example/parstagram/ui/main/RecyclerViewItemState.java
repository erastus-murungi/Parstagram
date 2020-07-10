package com.example.parstagram.ui.main;

import androidx.annotation.Nullable;

public class RecyclerViewItemState {

    @Nullable
    private Integer error;

    @Nullable
    public Integer getError() {
        return error;
    }

    public void setError(@Nullable Integer error) {
        this.error = error;
    }

    public RecyclerViewItemState(@Nullable Integer error) {
        this.error = error;
    }
}
