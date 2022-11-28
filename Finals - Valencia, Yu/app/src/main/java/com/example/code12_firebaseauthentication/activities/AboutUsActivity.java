package com.example.code12_firebaseauthentication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.code12_firebaseauthentication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class AboutUsActivity extends AppCompatActivity {

    private ImageView ivLogo;
    private TextView tvCash;
    private Button btnScan;
    private Button btnQR;
    private Button btnProfile;
    private TextView tvProgram;
    private TextView tvUid;
    private Button testing;

    //For login logout
    private FirebaseAuth mAuth;
    //To retrieve data from current user
    private FirebaseUser mUser;
    private DatabaseReference mRef;

    //For web views
    private Button btnLearnMore1, btnLearnMore2, btnLearnMore3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);


        //For web view
        btnLearnMore1 = findViewById(R.id.btn_learnMore1);
        btnLearnMore2 = findViewById(R.id.btn_learnMore2);
        btnLearnMore3 = findViewById(R.id.btn_learnMore3);

        btnLearnMore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutUsActivity.this, WebViewActivity.class));
            }
        });

        btnLearnMore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutUsActivity.this, WebViewActivity.class));
            }
        });

        btnLearnMore3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutUsActivity.this, WebViewActivity.class));
            }
        });
    }
}
