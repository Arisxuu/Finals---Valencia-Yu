package com.example.code12_firebaseauthentication.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

public class ProfileActivity extends AppCompatActivity {

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
    //User role holder
    private String role;

    //For Drawer Layout
    private DrawerLayout dlContent;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView dltvCash;
    private TextView tvHeaderName;

    //Make drawer layout do something on click
    private NavigationView nav;

    //For web views
    private Button btnLearnMore1, btnLearnMore2, btnLearnMore3;

    String temp;

    //Bottom Navigation
    BottomNavigationView bottomNavigationView;

    JobFragment jobFragment = new JobFragment();
    StoreFragment storeFragment = new StoreFragment();
    HomeFragment homeFragment = new HomeFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //set text in action bar
        setTitle("Kidzania");

        //bottom navigation
        bottomNavigationView  = findViewById(R.id.bottom_navigation);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference("Users/" + mUser.getUid());


        dlContent = findViewById(R.id.dl_content);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, dlContent, R.string.drawer_open, R.string.drawer_close);
        dlContent.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Make drawer layout do something on click
        nav = findViewById(R.id.nv_menu);

        //For web view
        btnLearnMore1 = findViewById(R.id.btn_learnMore1);
        btnLearnMore2 = findViewById(R.id.btn_learnMore2);
        btnLearnMore3 = findViewById(R.id.btn_learnMore3);

        //Drawer Layout
        tvHeaderName = nav.getHeaderView(0).findViewById(R.id.tv_header_name);
        dltvCash = nav.getHeaderView(0).findViewById(R.id.tv_cash);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.item_profile:
                        Intent intent = new Intent(ProfileActivity.this, ProfileScreenActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.item_settings:
                        Intent intentS = new Intent(ProfileActivity.this, SettingsActivity.class);
                        startActivity(intentS);
                        break;

                    case R.id.item_aboutUs:
                        Intent intentU = new Intent(ProfileActivity.this, ProfileActivity.class);
                        startActivity(intentU);
                        break;

                    case R.id.item_contactUs:
                        Intent intentC = new Intent(ProfileActivity.this, ContactUsActivity.class);
                        startActivity(intentC);
                        break;
                    case R.id.item_signOut:
                        mAuth.signOut();
                        startActivity(new Intent(ProfileActivity.this, SignInActivity.class));
                        Toast.makeText(ProfileActivity.this, "Signed out successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }

                return false;
            }
        });

        //getter setter of data straight from mUser, pero limited
        /*tvName.setText(mUser.getDisplayName());
        tvEmail.setText(mUser.getEmail());*/

        //getter setter of data from firebase, not limited
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Get data from mRef,
                UserModel um = snapshot.getValue(UserModel.class);
                //tvProgram.setText(um.collegeProgram);
                tvHeaderName.setText(um.name);
                temp = "Cash: ₱ " + um.cash;
                dltvCash.setText(temp);
                role = um.role;

                if (role.equals("checked_out")){
                    startActivity(new Intent(ProfileActivity.this, CheckRoleActivity.class));
                    finish();
                }
                else if (role.equals("admin_entrance")){
                    startActivity(new Intent(ProfileActivity.this, AdminEntranceGuardActivity.class));
                }
                else if (role.equals("admin_exit")){
                    startActivity(new Intent(ProfileActivity.this, AdminEntranceGuardActivity.class));
                }
                else if (role.equals("admin_jobOwner")){
                    startActivity(new Intent(ProfileActivity.this, JobOwnerActivity.class));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

        btnLearnMore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, WebViewActivity.class));
            }
        });

        btnLearnMore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, WebViewActivity.class));
            }
        });

        btnLearnMore3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, WebViewActivity.class));
            }
        });
    }

    //Calls XML menu that we made, papunta activity
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        return true;
    }

    //Handles yung click sa bawat menu item, so di natin gagawan ng tig-iisang onClickListener
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        //Eto yung ID na sinset natin sa menu_actionbar.xml
        switch(item.getItemId()){
            case R.id.menu_addCash:
                Toast.makeText(this, "Add cash clicked", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
