package com.example.pradeepkumar.adpost;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
//import com.firebase.ui.database.*;﻿

public class PostActivity extends AppCompatActivity {
    private ImageButton mImageSelect;
    private static final int Gallery_Request = 1;
    private EditText mPostTitle;
    private EditText mPostDesc;
    private Button mSubmitButton;
    private Uri mImageUri = null;
    private StorageReference mstorage;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mImageSelect = (ImageButton) findViewById(R.id.ibImageselect);
        mPostTitle = (EditText) findViewById(R.id.Titlefeild);

        mPostDesc = (EditText) findViewById(R.id.DescFeild);

        mSubmitButton = (Button) findViewById(R.id.Submitpost);
        mstorage = FirebaseStorage.getInstance().getReference();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("PMIT");

        mProgress = new ProgressDialog(this);


        mImageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                //galleryIntent.setType("Image/*");
                startActivityForResult(galleryIntent, Gallery_Request);
            }
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();

            }
        });
    }

    private void startPosting() {
        mProgress.setMessage("Posting to Blog....");

        final String title_val = mPostTitle.getText().toString().trim();
        final String desc_val = mPostDesc.getText().toString().trim();
        if (!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val) && mImageUri != null) {
            mProgress.show();
            StorageReference filepath = mstorage.child("Blog_Images").child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot takeSnapshot) {
                    Uri downloadUrl = takeSnapshot.getDownloadUrl();
                    DatabaseReference newpost=mDatabase.push();
                    newpost.child("title").setValue(title_val);
                    newpost.child("Desc").setValue(desc_val);
                    newpost.child("Image").setValue(downloadUrl.toString());

                    mProgress.dismiss();
                    startActivity(new Intent(PostActivity.this,MainActivity.class));
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Gallery_Request && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            mImageSelect.setImageURI(mImageUri);
        }
    }
}
