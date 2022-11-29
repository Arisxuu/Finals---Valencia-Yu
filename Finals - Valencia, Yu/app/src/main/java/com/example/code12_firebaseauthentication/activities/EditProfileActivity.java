package com.example.code12_firebaseauthentication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.code12_firebaseauthentication.R;
import com.example.code12_firebaseauthentication.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etAddress;
    private EditText etBirthday;
    private Button btnChangeBday, btnSave;


    //For login logout
    private FirebaseAuth mAuth;
    //To retrieve data from current user
    private FirebaseUser mUser;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference("Users/" + mUser.getUid());

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etAddress = findViewById(R.id.et_address);
        etBirthday = findViewById(R.id.et_epBirthday);
        btnChangeBday = findViewById(R.id.btn_epBirthdayPicker);
        btnSave = findViewById(R.id.btn_save);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Get data from mRef,
                UserModel um = snapshot.getValue(UserModel.class);
                etName.setText(um.name);
                etEmail.setText(um.email);
                etAddress.setText(um.address);
                etBirthday.setText(um.birthday);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnChangeBday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        //Toast.makeText(SignUpActivity.this, i + ", " + i1 + ", " + i2, Toast.LENGTH_SHORT).show();
                        etBirthday.setText(i1 + 1 + "/" + i2 + "/" + i);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    mRef.child("name").setValue(etName.getText().toString());
                    mRef.child("address").setValue(etAddress.getText().toString());
                    mRef.child("email").setValue(etEmail.getText().toString());
                    mRef.child("birthday").setValue(etBirthday.getText().toString());

                    mUser.updateEmail(etEmail.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(EditProfileActivity.this, "Profile has been updated.", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Toast.makeText(EditProfileActivity.this, "Update failed. Please try again.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                    startActivity(new Intent(EditProfileActivity.this, ProfileScreenActivity.class));
                    finish();

            }
        });

    }
}