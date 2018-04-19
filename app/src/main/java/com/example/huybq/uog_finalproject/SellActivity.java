package com.example.huybq.uog_finalproject;

// REFERENCE:
// https://www.simplifiedcoding.net/firebase-storage-tutorial-android/
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class SellActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView photoName;
    private EditText inputName, inputDesc, inputLocation;
    private Button btnSell, btnPhoto;
    private DatabaseReference mFirebaseDatabase;
    private StorageReference storageReference;
    private Uri filePath; //a Uri object to store local file path
    private String photo_url; // file path in the Firebase storage
    private static final int PICK_IMAGE_REQUEST = 234; //a constant to track the file chooser intent
    public final String STORAGE_PATH_UPLOADS = "itemphoto/";
    String userId;
    String name;
    String desc;
    String location;
    String itemId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        photoName = (TextView) findViewById(R.id.activity_sell_txt_photo);
        inputName = (EditText) findViewById(R.id.activity_sell_edit_name);
        inputDesc = (EditText) findViewById(R.id.activity_sell_edit_description);
        inputLocation = (EditText) findViewById(R.id.activity_sell_edit_location);
        btnSell = (Button) findViewById(R.id.activity_sell_btn_submit);
        btnPhoto = (Button) findViewById(R.id.activity_sell_btn_photo);

        // get reference to 'item' node
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("item");
        storageReference = FirebaseStorage.getInstance().getReference(); // root directory

        // Save / update the user
        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // In real apps this Uid should be fetched by implementing firebase auth
                userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                name = inputName.getText().toString();
                desc = inputDesc.getText().toString();
                location = inputLocation.getText().toString();
                itemId = mFirebaseDatabase.push().getKey();

                // validate input form
                if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(name) || TextUtils.isEmpty(location)) {
                    Toast.makeText(getApplicationContext(), "only description can be empty", Toast.LENGTH_SHORT).show();
                } else {
                    createItem();
                }
            }
        });

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser(); // select gallery app to pick a photo
            }
        });
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    // method to upload photo to storage
    private void createItem() {

        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            // getting the storage reference
            // the file name = itemID_random millis. extension
            StorageReference sRef = storageReference.child(STORAGE_PATH_UPLOADS + itemId + "_"
                                    + System.currentTimeMillis() + filePath.getLastPathSegment());

            // adding the file to above storage reference
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // uploading successfully at this step
                            // dismissing the progress dialog
                            progressDialog.dismiss();

                            // get the string of photo URL
                            photo_url = taskSnapshot.getDownloadUrl().toString();
                            mFirebaseDatabase.child(itemId).setValue(new Item(userId, name, desc, location, photo_url));

                            Toast.makeText(getApplicationContext(), "uploaded successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "failed to upload", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        } else {
            photo_url = "";
            mFirebaseDatabase.child(itemId).setValue(new Item(userId, name, desc, location, photo_url));
        }
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData(); // get URI file path
            // update the text view to display the selected file
            photoName.setText(filePath.toString());
        }
    }
}