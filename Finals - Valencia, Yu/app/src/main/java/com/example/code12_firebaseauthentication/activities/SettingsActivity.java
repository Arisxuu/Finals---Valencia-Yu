package com.example.code12_firebaseauthentication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.code12_firebaseauthentication.R;

public class SettingsActivity extends AppCompatActivity {

    private Button buttonHome, buttonPass, buttonEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

       buttonHome = findViewById(R.id.btn_home);
       buttonPass = findViewById(R.id.btn_pass);
       buttonEditProfile = findViewById(R.id.btn_profile);


        //Removes action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //Go to Profile activity
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
            }
        });

        //Go to Edit Profile activity
        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, EditProfileActivity.class));
            }
        });

        //Go to change password activity
        buttonPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, ChangePasswordActivity.class));
            }
        });



    }
}