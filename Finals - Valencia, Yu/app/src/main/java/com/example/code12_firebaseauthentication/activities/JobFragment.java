package com.example.code12_firebaseauthentication.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.code12_firebaseauthentication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;


public class JobFragment extends Fragment {

    private Button btnDownloadMap;
    private StorageReference storageRef;

    public JobFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_job, container, false);

        View view = inflater.inflate(R.layout.fragment_job, container, false);

        btnDownloadMap = view.findViewById(R.id.btn_downloadMap);
        storageRef = FirebaseStorage.getInstance().getReference();

        btnDownloadMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorageReference localRef = storageRef.child("User Pictures/Kidzania-Map.png");

                File rootPath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Kidzania");
                if(!rootPath.exists()) {
                    rootPath.mkdirs();
                }

                final File localFile = new File(rootPath,"Kidzania-Map.png");

                localRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Log.d("STORAGE_LIST","Uploaded");
                        //TODO
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        //TODO
                        Log.d("STORAGE_LIST","Failed");
                    }
                });
            }
        });


        return view;
    }
}