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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ChangePassword2Activity extends AppCompatActivity {

    private EditText etNewPassword, etNewRepeatPassword;
    private Button btnFinish;

    //For login logout
    private FirebaseAuth mAuth;
    //To retrieve data from current user
    private FirebaseUser mUser;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password2);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        etNewPassword = findViewById(R.id.et_cpNewPassword);
        etNewRepeatPassword = findViewById(R.id.et_cpRepeatNewPassword);
        btnFinish = findViewById(R.id.btn_cpFinishChangePw);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etNewPassword.getText().toString().equals(etNewRepeatPassword.getText().toString())){
                    mUser.updatePassword(etNewPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ChangePassword2Activity.this, "Password has been changed successfully.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ChangePassword2Activity.this, AboutUsActivity.class));
                                finish();
                            } else {
                                Toast.makeText(ChangePassword2Activity.this, "An error has occured, please try again.", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }
                else{
                    Toast.makeText(ChangePassword2Activity.this, "Passwords do not match, please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}