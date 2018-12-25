package com.example.ravish.testro;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    TextView emailx, usernamex;
    ImageView profileImage;
    Button out;
    private FirebaseAuth mAuth;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            usernamex = (TextView) view.findViewById(R.id.profileUsername);
            emailx = (TextView) view.findViewById(R.id.profileEmailTwo);
            profileImage = (ImageView) view.findViewById(R.id.profilePicture);
            if (user.getPhotoUrl() != null) {
                Picasso.get().load(user.getPhotoUrl()).into(profileImage);
            }
            emailx.setText(user.getEmail());
            usernamex.setText(user.getDisplayName());


        }
        out = view.findViewById(R.id.signout);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getContext(),login.class);
                startActivity(intent);
            }
        });
        return view;

    }
}

