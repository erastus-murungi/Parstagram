package com.example.parstagram.data.model;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class LocalPost {
    public String description;
    public String photoUrl;
    public LocalUser user;
    public String creationTime;

    public LocalPost() {

    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public LocalUser getUser() {
        return user;
    }

    public LocalPost(String description, String photoUrl, LocalUser user, String creationTime) {
        this.description = description;
        this.photoUrl = photoUrl;
        this.user = user;
        this.creationTime = creationTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static LocalPost fromParsePost(Post post) {
        LocalPost localPost = new LocalPost();
        localPost.user = LocalUser.fromParseUser(post.getUser());
        localPost.photoUrl = post.getImage().getUrl();
        localPost.description = post.getDescription();
        localPost.creationTime = post.getCreatedAt().toString();
        return localPost;
    }

    public static List<LocalPost> fromListParsePosts(List<Post> posts) {
        List<LocalPost> localPosts = new ArrayList<>(posts.size());
        for (Post post: posts) {
            localPosts.add(fromParsePost(post));
        }
        return localPosts;
    }
}
