package com.example.parstagram.ui.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parstagram.R;
import com.example.parstagram.databinding.ActivityLoginBinding;
import com.example.parstagram.databinding.FragmentUserBinding;
import com.example.parstagram.databinding.ItemPostBinding;
import com.parse.ParseUser;

import java.util.Objects;


public class UserFragment extends Fragment {
    private FragmentUserBinding binding;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        final TextView logOutTextView = view.findViewById(R.id.logout);

        logOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Objects.requireNonNull(getActivity()).finish();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}