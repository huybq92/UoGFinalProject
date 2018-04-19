package com.example.huybq.uog_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SellActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputName, inputDesc, inputLocation;
    private Button btnSell;
    private DatabaseReference mFirebaseDatabase;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        txtDetails = (TextView) findViewById(R.id.activity_sell_txt_label);
        inputName = (EditText) findViewById(R.id.activity_sell_edit_name);
        inputDesc = (EditText) findViewById(R.id.activity_sell_edit_description);
        inputLocation = (EditText) findViewById(R.id.activity_sell_edit_location);
        btnSell = (Button) findViewById(R.id.activity_sell_btn_submit);

        // get reference to 'users' node
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("item");

        // Save / update the user
        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // In real apps this userId should be fetched
                // by implementing firebase auth
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String name = inputName.getText().toString();
                String desc = inputDesc.getText().toString();
                String location = inputLocation.getText().toString();

                // Check for already existed userId
                if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(name) || TextUtils.isEmpty(location)) {
                    Toast.makeText(getApplicationContext(), "only description can be empty", Toast.LENGTH_SHORT).show();
                } else {
                    createItem(userId, name, desc, location);
                    Toast.makeText(getApplicationContext(), "selling successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }

    /**
     * Add new item object under 'item' node
     */
    private void createItem(String userId, String name, String desc, String location) {
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference(); // root node
        //Map<String, Item> item = new HashMap<>();
        //item.put(itemId, new Item(userId, name, desc, location));
        mFirebaseDatabase.child("item").push().setValue(new Item(userId, name, desc, location));
    }
}