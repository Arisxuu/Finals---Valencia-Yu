package com.example.code12_firebaseauthentication.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.code12_firebaseauthentication.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AdminGiftShopActivity extends AppCompatActivity {

    private TextView tvPayCashier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_gift_shop);

        //Removes action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        tvPayCashier = findViewById(R.id.tv_scanCashier);

        //Scan QR code
        IntentIntegrator qrScan = new IntentIntegrator(this);
        tvPayCashier.setOnClickListener(new View.OnClickListener() {
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
                //Toast.makeText(AdminGiftShopActivity.this, intentResult.getContents(), Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(AdminGiftShopActivity.this, Pay2Activity.class);
                intent2.putExtra("cashReceiver", intentResult.getContents());
                startActivity(intent2);
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}