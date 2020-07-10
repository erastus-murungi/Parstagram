package com.example.parstagram.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parstagram.R;
import com.example.parstagram.data.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<Post>> mPosts = new MutableLiveData<>();
    private MutableLiveData<RecyclerViewItemState> mState = new MutableLiveData<>();

    public LiveData<List<Post>> getPosts() {return mPosts;}

    LiveData<RecyclerViewItemState> getState() {return mState;}

    public MainViewModel(List<Post> posts) {
        assert posts != null;
        mPosts.setValue(posts);
        queryPosts();
    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e == null) {
                    mPosts.setValue(posts);
                } else {
                    mState.setValue(new RecyclerViewItemState(R.string.query_failure));
                }
            }
        });
    }


}
