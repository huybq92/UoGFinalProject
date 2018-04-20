package com.example.huybq.uog_finalproject;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ItemActivity extends AppCompatActivity implements View.OnClickListener {
    //view objects
    private Button btnCart;
    private Button btnFavorite;
    private TextView txtName, txtDesc, txtLocation, txtUserId;
    private ImageView imageView;
    private String test; // null string to test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        btnCart = (Button) findViewById(R.id.activity_item_btn_cart);
        btnFavorite = (Button) findViewById(R.id.activity_item_btn_favorite);
        imageView = (ImageView) findViewById(R.id.item_imageview);

        txtName = (TextView) findViewById(R.id.activity_item_txt_name);
        txtName.setText(MainActivity.selectedItem.name);
        txtDesc = (TextView) findViewById(R.id.activity_item_txt_desc);
        txtDesc.setText("Description: \n" + MainActivity.selectedItem.description);
        txtLocation = (TextView) findViewById(R.id.activity_item_txt_location);
        txtLocation.setText("Location: \n" + MainActivity.selectedItem.location);
        txtUserId = (TextView) findViewById(R.id.activity_item_txt_userId);
        txtUserId.setText("Item Code: \n" + MainActivity.selectedItem.itemId);
        //Toast.makeText(getApplicationContext(),test,Toast.LENGTH_SHORT).show();

        // load the photo
        // REFERENCE: https://stackoverflow.com/questions/5776851/load-image-from-url
        new DownloadImageTask(imageView).execute(MainActivity.selectedItem.images);

        // set button listeners
        btnCart.setOnClickListener(this);
        btnFavorite.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnCart) {
            //showFileChooser();
        } else {

        }
    }
}
