package com.example.parstagram.data.model;

import com.parse.ParseUser;

import org.parceler.Parcel;

@Parcel
public class LocalUser {
    public String username;
    public String name;
    public String profilePictureUrl;
    public String email;

    public LocalUser(String username, String name, String profilePictureUrl, String email) {
        this.username = username;
        this.name = name;
        this.profilePictureUrl = profilePictureUrl;
        this.email = email;
    }

    public LocalUser(String username, String name) {
        this.username = username;
        this.name = name;
    }

    public LocalUser() {

    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public static LocalUser fromParseUser(ParseUser user) {
        LocalUser localUser = new LocalUser();
        localUser.name = user.getString("name");
        localUser.username = user.getUsername();
        localUser.email = user.getEmail();
        localUser.profilePictureUrl = user.getParseFile("profilePicture").getUrl();
        return localUser;
    }
}
