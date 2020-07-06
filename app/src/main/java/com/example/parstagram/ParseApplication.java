package com.example.parstagram;

import android.app.Application;
import android.content.res.Configuration;

import com.parse.Parse;

import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;

public class ParseApplication extends Application {
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("erastus-parstagram")
                .clientKey("DirtyDiana")
                .clientBuilder(builder)
                .server("https://erastus-parstagram.herokuapp.com/parse/")
                .build());
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
