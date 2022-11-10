package com.example.code12_firebaseauthentication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.code12_firebaseauthentication.R;
import com.example.code12_firebaseauthentication.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout tilEmail;
    private TextInputLayout tilName;
    private TextInputLayout tilPassword;
    private TextInputLayout tilRepeatPassword;
    private TextInputLayout tilAddress;
    private TextInputLayout tilProgram;
    private Button btnSignup;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tilEmail = findViewById(R.id.til_email);
        tilName = findViewById(R.id.til_name);
        tilPassword = findViewById(R.id.til_password);
        tilRepeatPassword = findViewById(R.id.til_repeat_password);
        tilAddress = findViewById(R.id.til_address);
        tilProgram = findViewById(R.id.til_program);
        btnSignup = findViewById(R.id.btn_signup);

        //For login & logout
        mAuth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tilPassword.getEditText().getText().toString().equals(tilRepeatPassword.getEditText().getText().toString())){
                    tilRepeatPassword.setError("Password did not match");

                }else {
                    mAuth.createUserWithEmailAndPassword(tilEmail.getEditText().getText().toString(), tilPassword.getEditText().getText().toString())
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        //manageUser(mAuth.getCurrentUser());
                                        createDbInstance(mAuth.getCurrentUser());

                                    }else{
                                        Toast.makeText(SignUpActivity.this, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(SignUpActivity.this, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //TODO
                                }
                            });
                }
            }
        });
    }

    private void manageUser(FirebaseUser user){
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(tilName.getEditText().getText().toString())
                .build();

        user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(SignUpActivity.this, ProfileActivity.class));
                    finish();

                }else{
                    Toast.makeText(SignUpActivity.this, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //TODO
            }
        });
    }

    private void createDbInstance(FirebaseUser user){
        UserModel um = new UserModel(tilName.getEditText().getText().toString(),
                tilEmail.getEditText().getText().toString(),
                tilAddress.getEditText().getText().toString(),
                tilProgram.getEditText().getText().toString(),
                "0");

        String uid = user.getUid();

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users");
        mRef.child(uid).setValue(um);

        startActivity(new Intent(SignUpActivity.this, ProfileActivity.class));
        finish();
    }

}