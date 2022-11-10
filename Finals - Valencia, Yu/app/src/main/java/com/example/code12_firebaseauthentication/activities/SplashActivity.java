package com.example.code12_firebaseauthentication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.code12_firebaseauthentication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(mUser != null){
            //user is still logged in
            startActivity(new Intent(SplashActivity.this, ProfileActivity.class));
            finish();
        }else {
            //user is not logged in, show login screen
            startActivity(new Intent(SplashActivity.this, SignInActivity.class));
            finish();
        }
    }
}