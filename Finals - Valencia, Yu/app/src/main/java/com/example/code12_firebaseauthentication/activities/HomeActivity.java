package com.example.code12_firebaseauthentication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.code12_firebaseauthentication.R;
import com.example.code12_firebaseauthentication.models.UserModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
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

    //Make drawer layout do something on click
    private NavigationView nav;

    String temp, role;

    //Bottom Navigation
    BottomNavigationView bottomNavigationView;

    JobFragment jobFragment = new JobFragment();
    StoreFragment storeFragment = new StoreFragment();
    HomeFragment homeFragment = new HomeFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        ivQR = findViewById(R.id.iv_homeQR);

        tvHomeCash = findViewById(R.id.tv_homeCash);
        tvHomeName = findViewById(R.id.tv_homeName);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference("Users/" + mUser.getUid());

        generateQr(mUser.getUid());

        //Make drawer layout do something on click
        nav = findViewById(R.id.nv_menu);



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

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.item_profile:
                        Intent intent = new Intent(HomeActivity.this, ProfileScreenActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.item_settings:
                        Intent intentS = new Intent(HomeActivity.this, SettingsActivity.class);
                        startActivity(intentS);
                        break;

                    case R.id.item_aboutUs:
                        Intent intentU = new Intent(HomeActivity.this, ProfileActivity.class);
                        startActivity(intentU);
                        break;

                    case R.id.item_contactUs:
                        Intent intentC = new Intent(HomeActivity.this, ContactUsActivity.class);
                        startActivity(intentC);
                        break;
                    case R.id.item_signOut:
                        mAuth.signOut();
                        startActivity(new Intent(HomeActivity.this, SignInActivity.class));
                        Toast.makeText(HomeActivity.this, "Signed out successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }

                return false;
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.job:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,jobFragment).commit();
                        return true;
                    case R.id.store:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,storeFragment).commit();
                        return true;
                }

                return false;
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