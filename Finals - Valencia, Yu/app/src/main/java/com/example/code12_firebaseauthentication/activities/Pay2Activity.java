package com.example.code12_firebaseauthentication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.code12_firebaseauthentication.R;
import com.example.code12_firebaseauthentication.models.UserModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Pay2Activity extends AppCompatActivity {

    private TextView tvRecipientName, tvProductID, tvProductWatch, tvProductBallpen, tvProductNotebook;
    private TextInputLayout tilInputPay;
    private TextInputEditText tietInputPay;
    private Button btnPay;

    //For login logout
    private FirebaseAuth mAuth;
    //To retrieve data from current user
    private FirebaseUser mUser;
    private DatabaseReference mRefReceiver;
    private DatabaseReference mRefSender;

    private Double doubleInputPay;
    private Double doubleRecipientCash;

    private double doubleTotalBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay2);

        //Got receiver data from previous activity
        Intent intent = getIntent();
        String cashReceiver = intent.getStringExtra("cashReceiver");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRefSender = FirebaseDatabase.getInstance().getReference("Users/" + mUser.getUid());
        mRefReceiver = FirebaseDatabase.getInstance().getReference("Users/" + cashReceiver);

        tvRecipientName = findViewById(R.id.tv_recipientName);
        tilInputPay = findViewById(R.id.til_inputPay);
        btnPay = findViewById(R.id.btn_pay);

        tvProductID = findViewById(R.id.tv_productID);
        tvProductWatch = findViewById(R.id.tv_productWatch);
        tvProductBallpen = findViewById(R.id.tv_productBallpen);
        tvProductNotebook = findViewById(R.id.tv_productNotebook);

        //getter setter of data from firebase, not limited
        mRefReceiver.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Get data from mRef, set data on payment activity
                UserModel um = snapshot.getValue(UserModel.class);
                tvRecipientName.setText(um.name);
                doubleRecipientCash = Double.parseDouble(um.cash);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRefSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Get data from mRef,
                UserModel um = snapshot.getValue(UserModel.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doubleInputPay = Double.parseDouble(tilInputPay.getEditText().getText().toString());

                doubleRecipientCash = doubleRecipientCash - doubleInputPay;
                mRefReceiver.child("cash").setValue(doubleRecipientCash.toString());
                Toast.makeText(Pay2Activity.this, "Customer has been charged.", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(Pay2Activity.this, HomeActivity.class));
                finish();
            }
        });

        tvProductID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(tilInputPay.getEditText().getText().toString()))
                {
                    Double totalBill = Double.parseDouble("0");
                    totalBill = totalBill + 150;

                    tilInputPay.getEditText().setText(totalBill.toString());
                    Toast.makeText(Pay2Activity.this, tilInputPay.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Double totalBill = Double.parseDouble(tilInputPay.getEditText().getText().toString());
                    totalBill = totalBill + 150;

                    tilInputPay.getEditText().setText(totalBill.toString());
                    Toast.makeText(Pay2Activity.this, tilInputPay.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(Pay2Activity.this, "Customer has been charged.", Toast.LENGTH_SHORT).show();
            }
        });

        tvProductWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(tilInputPay.getEditText().getText().toString()))
                {
                    Double totalBill = Double.parseDouble("0");
                    totalBill = totalBill + 250;

                    tilInputPay.getEditText().setText(totalBill.toString());
                    Toast.makeText(Pay2Activity.this, tilInputPay.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Double totalBill = Double.parseDouble(tilInputPay.getEditText().getText().toString());
                    totalBill = totalBill + 250;

                    tilInputPay.getEditText().setText(totalBill.toString());
                    Toast.makeText(Pay2Activity.this, tilInputPay.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(Pay2Activity.this, "Customer has been charged.", Toast.LENGTH_SHORT).show();
            }
        });

        tvProductBallpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(tilInputPay.getEditText().getText().toString()))
                {
                    Double totalBill = Double.parseDouble("0");
                    totalBill = totalBill + 120;

                    tilInputPay.getEditText().setText(totalBill.toString());
                    Toast.makeText(Pay2Activity.this, tilInputPay.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Double totalBill = Double.parseDouble(tilInputPay.getEditText().getText().toString());
                    totalBill = totalBill + 120;

                    tilInputPay.getEditText().setText(totalBill.toString());
                    Toast.makeText(Pay2Activity.this, tilInputPay.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(Pay2Activity.this, "Customer has been charged.", Toast.LENGTH_SHORT).show();
            }
        });

        tvProductNotebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(tilInputPay.getEditText().getText().toString()))
                {
                    Double totalBill = Double.parseDouble("0");
                    totalBill = totalBill + 100;

                    tilInputPay.getEditText().setText(totalBill.toString());
                    Toast.makeText(Pay2Activity.this, tilInputPay.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Double totalBill = Double.parseDouble(tilInputPay.getEditText().getText().toString());
                    totalBill = totalBill + 100;

                    tilInputPay.getEditText().setText(totalBill.toString());
                    Toast.makeText(Pay2Activity.this, tilInputPay.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(Pay2Activity.this, "Customer has been charged.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}