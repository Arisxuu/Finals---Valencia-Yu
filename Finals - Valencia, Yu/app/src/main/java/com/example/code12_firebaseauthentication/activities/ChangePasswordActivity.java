package com.example.code12_firebaseauthentication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.code12_firebaseauthentication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btncpChangePw;

    //For login logout
    private FirebaseAuth mAuth;
    //To retrieve data from current user
    private FirebaseUser mUser;
    private DatabaseReference mRef;

    //Holds user input e-mail & pw
    AuthCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etEmail = findViewById(R.id.et_cpEmail);
        etPassword = findViewById(R.id.et_cpPassword);
        btncpChangePw = findViewById(R.id.btn_cpChangePw);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        btncpChangePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                credential = EmailAuthProvider
                        .getCredential(etEmail.getText().toString(), etPassword.getText().toString());

                mUser.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(ChangePasswordActivity.this, ChangePassword2Activity.class));
                                } else {
                                    Toast.makeText(ChangePasswordActivity.this, "Wrong credentials, please try again.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }
}