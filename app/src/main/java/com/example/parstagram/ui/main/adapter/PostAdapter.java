package com.example.parstagram.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram.data.model.LocalPost;
import com.example.parstagram.databinding.ItemPostBinding;
import com.example.parstagram.ui.details.DetailViewModel;
import com.example.parstagram.ui.main.MainViewModel;

import java.util.List;

import ru.embersoft.expandabletextview.ExpandableTextView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<LocalPost> mPosts;
    private Context mContext;
    private ItemOnClickedListener mOnItemClickedListener;

    public PostAdapter(Context context, MainViewModel mainViewModel, ItemOnClickedListener onClickedListener) {
        this.mContext = context;
        this.mPosts = mainViewModel.getPosts().getValue();
        this.mOnItemClickedListener = onClickedListener;
    }

    public interface ItemOnClickedListener {
        void onItemClicked(int position);
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

    public void updatePosts(List<LocalPost> posts) {
        mPosts = posts;
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
        private TextView creationTime;
        private ExpandableTextView captionExpandableTextView;

        public PostViewHolder(@NonNull ItemPostBinding itemPostBinding) {
            super(itemPostBinding.getRoot());

            postImageView = itemPostBinding.imageViewPost;
            profilePictureImageView = itemPostBinding.imageViewProfilePicture;
            usernameTextView = itemPostBinding.textViewUsername;
            captionExpandableTextView = itemPostBinding.expandableTextViewDescription;
            creationTime = itemPostBinding.textViewCreationTime;
        }

        public void bind(LocalPost post) {
            captionExpandableTextView.setText(post.getDescription());
            Glide.with(mContext).load(post.getPhotoUrl()).centerCrop().into(postImageView);
            usernameTextView.setText(post.getUser().getUsername());
            Glide.with(mContext).load(post.getUser().profilePictureUrl).circleCrop().into(profilePictureImageView);
            postImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickedListener.onItemClicked(getAdapterPosition());
                }
            });
            creationTime.setText(DetailViewModel.getRelativeTimeAgo(post.creationTime));

        }
    }
}
