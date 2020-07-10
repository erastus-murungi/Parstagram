package com.example.parstagram.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.parstagram.R;
import com.example.parstagram.databinding.ActivityMainBinding;
import com.example.parstagram.ui.capture.CaptureActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityMainBinding mMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mMainBinding.getRoot());

        final BottomNavigationView mMainBottomNavigationView = mMainBinding.bottomNavigation;

        mMainBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                // scroll to the top
                                break;
                            case R.id.action_new_post:
                                goToCaptureActivity();
                                break;
                            case R.id.action_user:
                                // go to user page
                                break;
                            default:
                                return false;
                        }
                        return true;
                    }
                });
    }

    private void goToCaptureActivity() {
        startActivity(new Intent(this, CaptureActivity.class));
    }
}