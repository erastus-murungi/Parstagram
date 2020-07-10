package com.example.parstagram.ui.capture;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parstagram.databinding.ActivityCaptureBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;

public class CaptureActivity extends AppCompatActivity {
    public static final String TAG = "CaptureActivity";
    private CaptureViewModel captureViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        captureViewModel = new ViewModelProvider(this, new CaptureViewModelFactory()).get(CaptureViewModel.class);

        final ActivityCaptureBinding captureBinding = ActivityCaptureBinding.inflate(getLayoutInflater());

        setContentView(captureBinding.getRoot());

        final TextView cancelTextView = captureBinding.cancel;
        final Button openCameraButton = captureBinding.buttonOpenCamera;
        final Button postButton = captureBinding.post;
        final TextInputLayout captionTextInputLayout = captureBinding.textInputCaption;
        final ImageView pictureImageView = captureBinding.imageViewPicture;


        captureViewModel.getPostState().observe(this, new Observer<CapturePostState>() {
            @Override
            public void onChanged(CapturePostState state) {
                if (state == null) {
                    return;
                }
                if (state.isImageReady()) {
                    pictureImageView.setImageBitmap(captureViewModel.getBitmap().getValue());
                    openCameraButton.setVisibility(View.GONE);
                }
            }
        });


        captureViewModel.getResult().observe(this, new Observer<CaptureResult>() {
            @Override
            public void onChanged(CaptureResult captureResult) {
                if (captureResult == null) {
                    return;
                }
                if (captureResult.getError() != null) {
                    showPostFailed(captureResult.getError());
                }
                if (captureResult.getSuccess() != null) {
                    Toast.makeText(getApplicationContext(), "success creating post", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // notify the view model of the change of data
                captureViewModel.post(captionTextInputLayout.getEditText().getText().toString());
            }
        });

        openCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // launch the camera
                launchCamera();
            }
        });
    }

    private void showPostFailed(@StringRes Integer error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Create a File reference for future access
        captureViewModel.updatePhotoFile(getPhotoFileUri(CaptureViewModel.photoFileName));
        Uri fileProvider = FileProvider.getUriForFile(CaptureActivity.this, "com.codepath.fileprovider", captureViewModel.getPhotoFile().getValue());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CaptureViewModel.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CaptureViewModel.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // update view model with new picture
                captureViewModel.capturePostDataChanged(BitmapFactory.decodeFile(captureViewModel.getPhotoFile().getValue().getAbsolutePath()));
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename

        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }


}