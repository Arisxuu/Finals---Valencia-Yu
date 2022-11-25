package com.example.code12_firebaseauthentication.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.code12_firebaseauthentication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AdminExitGuardActivity extends AppCompatActivity {

    private Button btnScanForExit;

    //For login logout
    private FirebaseAuth mAuth;
    //To retrieve data from current user
    private FirebaseUser mUser;
    private DatabaseReference mRefReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_exit_guard);

        btnScanForExit = findViewById(R.id.btn_scanForExit);

        //Scan QR code
        IntentIntegrator qrScan = new IntentIntegrator(this);
        btnScanForExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.setPrompt("Scan");
                qrScan.setCameraId(0); //0 - back cam, 1 - front cam
                qrScan.setBeepEnabled(true); //pagkascan gagawa ng beep na sound
                qrScan.initiateScan(); //starts the camera
            }
        });

    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                //TODO cancelled
            } else {
                //QR output nakukuha rito
                //Toast.makeText(ProfileActivity.this, intentResult.getContents(), Toast.LENGTH_LONG).show();
                mRefReceiver = FirebaseDatabase.getInstance().getReference("Users/" + intentResult.getContents());
                mRefReceiver.child("role").setValue("checked_out");
                Toast.makeText(AdminExitGuardActivity.this, "Customer is free to leave.", Toast.LENGTH_SHORT).show();

                //Scan QR code
                IntentIntegrator qrScan = new IntentIntegrator(this);
                qrScan.setPrompt("Scan");
                qrScan.setCameraId(0); //0 - back cam, 1 - front cam
                qrScan.setBeepEnabled(true); //pagkascan gagawa ng beep na sound
                qrScan.initiateScan(); //starts the camera

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    };
}