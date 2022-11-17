package com.example.code12_firebaseauthentication.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.code12_firebaseauthentication.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AdminActivity extends AppCompatActivity {

    private Button btnEntranceGuard;
    private Button btnExitGuard;
    private Button btnJobOwners;
    private Button btnStoreOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnEntranceGuard = findViewById(R.id.btn_entranceGuard);
        btnExitGuard = findViewById(R.id.btn_exitGuard);
        btnJobOwners = findViewById(R.id.btn_jobOwners);

        btnEntranceGuard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, AdminEntranceGuardActivity.class));
            }
        });

        btnExitGuard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, AdminExitGuardActivity.class));
            }
        });


        btnJobOwners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, AdminJobOwnerActivity.class));
            }
        });



    }

}