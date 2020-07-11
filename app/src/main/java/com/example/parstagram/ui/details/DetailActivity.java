package com.example.parstagram.ui.details;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parstagram.R;
import com.example.parstagram.databinding.ActivityDetailBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import ru.embersoft.expandabletextview.ExpandableTextView;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding mDetailBinding;
    private DetailActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        final DetailViewModel detailViewModel =
                new ViewModelProvider(this,
                        new DetailViewModelFactory(getIntent()
                                .getParcelableExtra("Post")))
                        .get(DetailViewModel.class);

        mDetailBinding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(mDetailBinding.getRoot());

        context = this;

        final TextView createdAtTextView = mDetailBinding.textViewCreatedAt;
        final ExpandableTextView captionTextView = mDetailBinding.expandableTextViewDescription;
        final ImageView postImageView = mDetailBinding.imageViewPost;
        final ImageView profilePictureImageView = mDetailBinding.imageViewProfilePicture;


        detailViewModel.getState().observe(this, new Observer<DetailState>() {
            @Override
            public void onChanged(DetailState detailState) {
                if (detailState == null) {
                    return;
                }
                if (detailState.isImageReady()) {
                    Glide.with(context)
                            .load(detailViewModel.getPost().getPhotoUrl())
                            .centerCrop().into(postImageView);
                }
                if (detailState.isCreationTimeReady()) {
                    createdAtTextView.setText(detailViewModel.getCreationTime());
                }

                if (detailState.isDescriptionReady()) {
                    captionTextView.setText(detailViewModel.getPost().getDescription());
                }
                if (detailState.isUserDataCorrect()) {
                    Glide.with(context)
                            .load(detailViewModel.getPost().getUser().getProfilePictureUrl())
                            .circleCrop().into(profilePictureImageView);
                }
                else {
                    if (detailState.getError() != null) {
                        showErrorSnackBar(mDetailBinding.mainLayout, detailState.getError());
                    }
                }
            }
        });


    }

    private void showErrorSnackBar(RelativeLayout mainLayout, @StringRes Integer error) {
        Snackbar.make(mainLayout, error, Snackbar.LENGTH_LONG).setTextColor(getColor(R.color.red)).show();
    }
}