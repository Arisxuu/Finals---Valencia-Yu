package com.example.code12_firebaseauthentication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.code12_firebaseauthentication.R;

public class SettingsActivity extends AppCompatActivity {

    private Button buttonHome, buttonPass, buttonProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

       buttonHome = findViewById(R.id.btn_home);
       buttonPass = findViewById(R.id.btn_pass);
       buttonProfile = findViewById(R.id.btn_profile);



        //Go to Profile activity
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, ProfileActivity.class));
            }
        });

        //Go to Edit Profile activity
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, EditProfileActivity.class));
            }
        });



    }
}