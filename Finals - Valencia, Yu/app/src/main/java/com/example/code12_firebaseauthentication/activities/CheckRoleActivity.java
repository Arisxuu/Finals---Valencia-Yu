package com.example.code12_firebaseauthentication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.code12_firebaseauthentication.R;
import com.example.code12_firebaseauthentication.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class CheckRoleActivity extends AppCompatActivity {

    private ImageView ivQR;

    //For login logout
    private FirebaseAuth mAuth;
    //To retrieve data from current user
    private FirebaseUser mUser;
    private DatabaseReference mRef;

    //Check role of user
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_role);

        ivQR = findViewById(R.id.iv_qr);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference("Users/" + mUser.getUid());

        //Removes action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Get data from mRef,
                UserModel um = snapshot.getValue(UserModel.class);
                role = um.role;

                if (role.equals("checked_in")){
                    startActivity(new Intent(CheckRoleActivity.this, HomeActivity.class));
                    Toast.makeText(CheckRoleActivity.this, "Successfully verified. You may now enter.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(CheckRoleActivity.this, "Not verified.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        generateQr(mUser.getUid());


    }

    private void generateQr(String s){
        BitMatrix result;
        //object that handles image as code sa android
        Bitmap bitmap = null;

        try{
            result = new MultiFormatWriter().encode(s, BarcodeFormat.QR_CODE, 300, 300, null);
            int w = result.getWidth();
            int h = result.getHeight();
            int[] pixels = new int[w * h];

            for(int y = 0; y < h; y++){
                int offset = y * w;
                for(int x = 0; x < w; x++){
                    pixels[offset + x] = result.get(x, y) ? getColor(R.color.black) : getColor(R.color.white);
                }
            }

            //transform into bitmap, para maging image
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, 300, 0, 0, w, h);

            //set to image view
            ivQR.setImageBitmap(bitmap);

        }catch(WriterException e){
            e.printStackTrace();
        }
    }

}