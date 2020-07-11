package com.example.parstagram.ui.details;

import android.text.format.DateUtils;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parstagram.R;
import com.example.parstagram.data.model.LocalPost;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetailViewModel extends ViewModel {

    private MutableLiveData<DetailState> mState = new MutableLiveData<>();
    private LocalPost mPost;

    public DetailViewModel(LocalPost post) {
        mPost = post;
        if (post.creationTime == null) {
            mState.setValue(new DetailState(R.string.no_creation_time_provided));
        }
        if (post.description == null) {
            mState.setValue(new DetailState(R.string.no_description_provided));
        }
        if (post.photoUrl == null) {
            mState.setValue(new DetailState(R.string.no_photo_url_provied));
        }
        if (post.user == null
                || post.user.profilePictureUrl == null
                || post.user.username == null
                || post.user.name == null) {
            mState.setValue(new DetailState(R.string.user_corrupted));
        }
        else {
            mState.setValue(new DetailState(true, true, true, true));
        }
    }

    LocalPost getPost() {
        return mPost;
    }

    LiveData<DetailState> getState() {
        return mState;
    }

    public String getCreationTime() {
        return getRelativeTimeAgo(mPost.creationTime);
    }


    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
