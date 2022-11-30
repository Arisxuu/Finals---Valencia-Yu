package com.example.code12_firebaseauthentication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
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
    private TextView tvHomeName, tvHomeCash, tvHomeEmail, tvHomeBirthday, tvHomeUID, tvHomeAddress;

    //For login logout
    private FirebaseAuth mAuth;
    //To retrieve data from current user
    private FirebaseUser mUser;
    private DatabaseReference mRef;
    //User role holder
    private String temp, role;

    //For Drawer Layout
    private DrawerLayout dlContent;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView dltvCash;
    private TextView tvHeaderName;


    //Make drawer layout do something on click
    private NavigationView nav;

    //Bottom Navigation
    BottomNavigationView bottomNavigationView;

    JobFragment jobFragment = new JobFragment();
    StoreFragment storeFragment = new StoreFragment();
    HomeFragment homeFragment = new HomeFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //set text in action bar
        setTitle("Kidzania");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference("Users/" + mUser.getUid());

        dlContent = findViewById(R.id.dl_content);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, dlContent, R.string.drawer_open, R.string.drawer_close);
        dlContent.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Make drawer layout do something on click
        nav = findViewById(R.id.nv_menu);

        //Drawer Layout
        tvHeaderName = nav.getHeaderView(0).findViewById(R.id.tv_header_name);
        dltvCash = nav.getHeaderView(0).findViewById(R.id.tv_cash);

        //Passport Data
        ivQR = findViewById(R.id.iv_homeQR);
        tvHomeName = findViewById(R.id.tv_homeName);
        tvHomeCash = findViewById(R.id.tv_homeCash);
        tvHomeAddress = findViewById(R.id.tv_homeAddress);
        tvHomeBirthday = findViewById(R.id.tv_homeBirthday);
        tvHomeUID = findViewById(R.id.tv_homeUID);


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
                        Intent intentU = new Intent(HomeActivity.this, AboutUsActivity.class);
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

                //Sets data on drawer layout
                tvHeaderName.setText(um.name);
                temp = "Cash: ₱ " + um.cash;
                dltvCash.setText(temp);
                role = um.role;

                //Sets data on passport
                tvHomeName.setText(um.name);
                temp = "₱ " + um.cash;
                tvHomeCash.setText(temp);
                tvHomeAddress.setText(um.address);
                tvHomeBirthday.setText(um.birthday);
                tvHomeUID.setText(mUser.getUid().toString());


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
                    startActivity(new Intent(HomeActivity.this, AdminJobOwnerActivity.class));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        generateQr(mUser.getUid().toString());

        //Make drawer layout do something on click
        nav = findViewById(R.id.nv_menu);

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
                        Intent intentU = new Intent(HomeActivity.this, AboutUsActivity.class);
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

    /*//Calls XML menu on navbar that we made, papunta activity
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        return true;
    }*/

    //Handles yung click sa bawat menu item, so di natin gagawan ng tig-iisang onClickListener
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        /*//Eto yung ID na sinset natin sa menu_actionbar.xml
        switch(item.getItemId()){
            case R.id.menu_addCash:
                Toast.makeText(this, "Add cash clicked", Toast.LENGTH_SHORT).show();
                break;
        }*/

        return super.onOptionsItemSelected(item);
    }

}