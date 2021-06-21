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
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;


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
            for (UserInfo profile : user.getProviderData()) {
                Uri photoUrl = profile.getPhotoUrl();
                String originalPieceOfUrl = "s96-c";
                String newPieceOfUrlToAdd = "s400-c";

                if (photoUrl != null) {
                    String photoPath = photoUrl.toString();
                    String newString = " ";
                    if(profile.getProviderId().equals("google.com")) {
                        newString = photoPath.replace(originalPieceOfUrl, newPieceOfUrlToAdd);
                    }else if(profile.getProviderId().equals("facebook.com")){
                        newString = photoPath + "?height=500";
                    }
                    Log.i("TAG",newString);


                    Glide.with(this)
                            .load(newString)
                            .into(image);
                }
                name.setText(user.getDisplayName());
                email.setText(profile.getEmail());

            }


            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(Information.this, MainActivity.class);
                    Toast.makeText(Information.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            });

        }
    }
}
