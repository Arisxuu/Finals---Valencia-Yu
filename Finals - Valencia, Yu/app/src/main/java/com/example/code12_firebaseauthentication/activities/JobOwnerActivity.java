package com.example.code12_firebaseauthentication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.code12_firebaseauthentication.R;

public class JobOwnerActivity extends AppCompatActivity {

    private Button btnEntranceGuard;
    private Button btnExitGuard;
    private Button btnJobOwners;
    private Button btnStoreOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobowner);

        btnJobOwners = findViewById(R.id.btn_jobOwners);

        btnEntranceGuard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JobOwnerActivity.this, AdminEntranceGuardActivity.class));
            }
        });

        btnExitGuard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JobOwnerActivity.this, AdminExitGuardActivity.class));
            }
        });


        btnJobOwners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JobOwnerActivity.this, AdminJobOwnerActivity.class));
            }
        });



    }

}