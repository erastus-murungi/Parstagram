package com.example.parstagram.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.parstagram.R;
import com.example.parstagram.data.model.LocalPost;
import com.example.parstagram.databinding.ActivityMainBinding;
import com.example.parstagram.ui.capture.CaptureActivity;
import com.example.parstagram.ui.details.DetailActivity;
import com.example.parstagram.ui.main.adapter.PostAdapter;
import com.example.parstagram.ui.user.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.parceler.Parcels;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityMainBinding mMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        final FragmentManager fragmentManager = getSupportFragmentManager();
        setContentView(mMainBinding.getRoot());
        final MainViewModel mainViewModel =
                new ViewModelProvider(this, new MainViewModelFactory()).get(MainViewModel.class);

        final BottomNavigationView mMainBottomNavigationView = mMainBinding.bottomNavigation;
        final RecyclerView postsRecyclerView = mMainBinding.recyclerViewPost;
        final PostAdapter postAdapter = new PostAdapter(this, mainViewModel, new PostAdapter.ItemOnClickedListener() {
            @Override
            public void onItemClicked(int position) {
                gotoDetailActivity(mainViewModel, position);
            }
        });

        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postsRecyclerView.setAdapter(postAdapter);


        final SwipeRefreshLayout swipeContainer = mMainBinding.swipeRefreshHome;
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainViewModel.refresh();
                postAdapter.updatePosts(mainViewModel.getPosts().getValue());
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mainViewModel.getPosts().observe(this, new Observer<List<LocalPost>>() {
            @Override
            public void onChanged(List<LocalPost> posts) {
                if (posts == null) {
                    return;
                }
                postAdapter.updatePosts(posts);
            }
        });

        mainViewModel.getState().observe(this, new Observer<RecyclerViewItemState>() {
            @Override
            public void onChanged(RecyclerViewItemState recyclerViewItemState) {
                if (recyclerViewItemState == null) {
                    return;
                }
                if (recyclerViewItemState.getError() != null) {
                    showQueryPostError(recyclerViewItemState.getError());
                }
            }
        });

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
                                // go to user fragment
                                fragmentManager.beginTransaction().replace(R.id.frame_layout_user,
                                        new UserFragment()).addToBackStack(null).commit();
                                break;
                            default:
                                return false;
                        }
                        return true;
                    }
                });
    }

    private void gotoDetailActivity(MainViewModel mainViewModel, int position) {
        // we start activity for result
        // if a successful post happened, perform refresh
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("Post", Parcels.wrap(mainViewModel.getPosts().getValue().get(position)));
        startActivity(detailIntent);
    }

    private void goToCaptureActivity() {
        // we start activity for result
        // if a successful post happened, perform refresh
        startActivity(new Intent(this, CaptureActivity.class));
    }

    private void showQueryPostError(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}