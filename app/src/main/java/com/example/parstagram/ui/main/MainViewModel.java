package com.example.parstagram.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parstagram.R;
import com.example.parstagram.data.model.LocalPost;
import com.example.parstagram.data.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class MainViewModel extends ViewModel {
    public static final int NUM_POSTS = 20;
    private MutableLiveData<List<LocalPost>> mPosts = new MutableLiveData<>();
    private MutableLiveData<RecyclerViewItemState> mState = new MutableLiveData<>();

    public LiveData<List<LocalPost>> getPosts() {return mPosts;}

    LiveData<RecyclerViewItemState> getState() {return mState;}

    public MainViewModel(List<LocalPost> posts) {
        assert posts != null;
        mPosts.setValue(posts);
        queryPosts();
    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(NUM_POSTS);
        query.addAscendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e == null) {
                    mPosts.setValue(LocalPost.fromListParsePosts(posts));
                } else {
                    mState.setValue(new RecyclerViewItemState(R.string.query_failure));
                }
            }
        });
    }

    public void refresh() {
        queryPosts();
    }


}
