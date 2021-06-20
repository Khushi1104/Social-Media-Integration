package com.example.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Information extends AppCompatActivity {
    private ImageView image;
    private Button logout;
    private TextView name;
    private TextView email;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        logout = findViewById(R.id.logout);
        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            onSignedInInitialize();
//
//
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
        }


    logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Information.this,MainActivity.class);
        startActivity(intent);
        finish();
        }
    });

    }

    private void onSignedInInitialize() {
        if(user.getDisplayName() != null && user.getDisplayName().length() > 0)
        {
            name.setText(user.getDisplayName());
        }

        String photoUrl;
        String provider = user.getProviders().get(0);
        if (provider.equals("facebook.com")) {
            photoUrl = user.getPhotoUrl() + "?height=500";
        }
        else if(provider.equals("google.com"))
        {
            photoUrl = user.getPhotoUrl().toString();

//Remove thumbnail url and replace the original part of the Url with the new part
            photoUrl = photoUrl.substring(0, photoUrl.length() - 15) + "s400-c/photo.jpg";

        }
        else
        {
            photoUrl = user.getPhotoUrl().toString();
        }

        Picasso.with(this)
                .load(photoUrl).placeholder(R.drawable.avatars).error(R.drawable.avatars).into(image);

    }


}
