package com.example.code12_firebaseauthentication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class HomeActivity extends AppCompatActivity {

    private ImageView ivQR;
    private TextView tvHomeName, tvHomeCash;

    //For login logout
    private FirebaseAuth mAuth;
    //To retrieve data from current user
    private FirebaseUser mUser;
    private DatabaseReference mRef;

    String temp, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ivQR = findViewById(R.id.iv_homeQR);

        tvHomeCash = findViewById(R.id.tv_homeCash);
        tvHomeName = findViewById(R.id.tv_homeName);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference("Users/" + mUser.getUid());

        generateQr(mUser.getUid());



        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Get data from mRef,
                UserModel um = snapshot.getValue(UserModel.class);
                tvHomeName.setText(um.name);
                temp = "Cash: ₱ " + um.cash;
                tvHomeCash.setText(temp);
                role = um.role;

                if (role.equals("checked_out")){
                    startActivity(new Intent(HomeActivity.this, CheckRoleActivity.class));
                    finish();
                }
                else if (role.equals("admin_entrance")){
                    startActivity(new Intent(HomeActivity.this, AdminEntranceGuardActivity.class));
                }
                else if (role.equals("admin_exit")){
                    startActivity(new Intent(HomeActivity.this, AdminEntranceGuardActivity.class));
                }
                else if (role.equals("admin_jobOwner")){
                    startActivity(new Intent(HomeActivity.this, JobOwnerActivity.class));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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