package com.example.parstagram.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram.data.model.Post;
import com.example.parstagram.databinding.ItemPostBinding;
import com.example.parstagram.ui.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import ru.embersoft.expandabletextview.ExpandableTextView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> mPosts;
    private Context mContext;

    public PostAdapter(Context context, MainViewModel mainViewModel) {
        this.mContext = context;
        this.mPosts = mainViewModel.getPosts().getValue();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        return new PostViewHolder(ItemPostBinding.inflate(layoutInflater, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(mPosts.get(position));
    }

    public void updatePosts(List<Post> posts) {
        mPosts = posts;
        notifyDataSetChanged();
    }

    public void clear() {
        mPosts = new ArrayList<>();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        private ImageView postImageView;
        private ImageView profilePictureImageView;
        private TextView usernameTextView;
        private ExpandableTextView captionExpandableTextView;

        public PostViewHolder(@NonNull ItemPostBinding itemPostBinding) {
            super(itemPostBinding.getRoot());

            postImageView = itemPostBinding.imageViewPost;
            profilePictureImageView = itemPostBinding.imageViewProfilePicture;
            usernameTextView = itemPostBinding.textViewUsername;
            captionExpandableTextView = itemPostBinding.expandableTextViewDescription;
        }

        public void bind(Post post) {
            captionExpandableTextView.setText(post.getDescription());
            Glide.with(mContext).load(post.getImage().getUrl()).centerCrop().into(postImageView);
            usernameTextView.setText(post.getUser().getUsername());
        }
    }
}
