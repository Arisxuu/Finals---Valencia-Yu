package com.example.code12_firebaseauthentication.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.code12_firebaseauthentication.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AdminJobOwnerActivity extends AppCompatActivity {

    private TextView tvPaykids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_job_owner);

        tvPaykids = findViewById(R.id.tv_payKids);

        //Scan QR code
        IntentIntegrator qrScan = new IntentIntegrator(this);
        tvPaykids.setOnClickListener(new View.OnClickListener() {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult != null){
            if(intentResult.getContents() == null){
                //TODO cancelled
            }else {
                //QR output nakukuha rito
                //Toast.makeText(ProfileActivity.this, intentResult.getContents(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AdminJobOwnerActivity.this, PayActivity.class);
                intent.putExtra("cashReceiver", intentResult.getContents());
                startActivity(intent);

            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}