package com.example.parstagram.ui.capture;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parstagram.R;
import com.example.parstagram.data.model.SuccessfulPost;

import java.io.File;


public class CaptureViewModel extends ViewModel {
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public static String photoFileName = "photo.jpg";

    private MutableLiveData<File> mPhotoFile = new MutableLiveData<>();
    private MutableLiveData<CapturePostState> mCapturePostState = new MutableLiveData<>();
    private MutableLiveData<CaptureResult> mCaptureResult = new MutableLiveData<>();
    private MutableLiveData<Bitmap> mBitmap = new MutableLiveData<>();

    LiveData<CapturePostState> getPostState() {return mCapturePostState;}
    LiveData<CaptureResult> getResult() {return mCaptureResult;}
    LiveData<Bitmap> getBitmap() {return mBitmap;}
    LiveData<File> getPhotoFile() {return mPhotoFile;}

    public void post(String description) {
        if (isEmpty(description)) {
            mCaptureResult.setValue(new CaptureResult(R.string.empty_description));
        } else if (mBitmap == null || mBitmap.getValue() == null) {
            mCaptureResult.setValue(new CaptureResult(R.string.no_photo));
        } else {
            mCaptureResult.setValue(new CaptureResult(new SuccessfulPost(description, mBitmap.getValue())));
        }
    }

    public void captionTextChanged(String description) {
    }

    public void capturePostDataChanged(Bitmap bitmap) {
        if (!photoFileValid(bitmap)){
            mCapturePostState.setValue(new CapturePostState(R.string.no_photo));
        } else {
            mBitmap.setValue(bitmap);
            mCapturePostState.setValue(new CapturePostState(true));
        }
    }

    public void updatePhotoFile(File photoFile){
        mPhotoFile.setValue(photoFile);
    }

    private static boolean isEmpty(String description) {
        return description == null || description.trim().length() == 0;
    }

    private static boolean photoFileValid(Bitmap bitmap) {
        return bitmap != null;
    }
}
