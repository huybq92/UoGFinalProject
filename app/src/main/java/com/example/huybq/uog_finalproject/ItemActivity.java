package com.example.huybq.uog_finalproject;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemActivity extends AppCompatActivity {
    //view objects
    private Button btnCart;
    private Button btnFavorite;
    private TextView txtName, txtDesc, txtLocation, txtUserId;
    private ImageView imageView;

    private boolean isInCart;
    private boolean isInFavorite;

    // database objects
    DatabaseReference ref, ref2;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        // initiate view objects
        btnCart = (Button) findViewById(R.id.activity_item_btn_cart);
        btnFavorite = (Button) findViewById(R.id.activity_item_btn_favorite);
        btnCart.setText("Add to cart");
        isInCart = false;
        isInFavorite = false;

        imageView = (ImageView) findViewById(R.id.item_imageview);

        txtName = (TextView) findViewById(R.id.activity_item_txt_name);
        txtName.setText(MainActivity.selectedItem.name);
        txtDesc = (TextView) findViewById(R.id.activity_item_txt_desc);
        txtDesc.setText("Description: \n" + MainActivity.selectedItem.description);
        txtLocation = (TextView) findViewById(R.id.activity_item_txt_location);
        txtLocation.setText("Location: \n" + MainActivity.selectedItem.location);
        txtUserId = (TextView) findViewById(R.id.activity_item_txt_userId);
        txtUserId.setText("Item Code: \n" + MainActivity.selectedItem.itemId);

        // load the photo using AsynTask object.
        // the photo will be loading on background
        // REFERENCE: https://stackoverflow.com/questions/5776851/load-image-from-url
        new DownloadImageTask(imageView).execute(MainActivity.selectedItem.images);

        // check if this item has been in shopping cart.
        // if so, change the text of the button. and change the function of listener
        // to remove item in cart.
        database = FirebaseDatabase.getInstance();
        // point to the node cart/[userId] in Firebase
        ref = database.getReference("cart").child( FirebaseAuth.getInstance().getCurrentUser().getUid() );
        ref2 = database.getReference("favorite").child( FirebaseAuth.getInstance().getCurrentUser().getUid() );
        // retrieve data from Firebase
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    collectItems((Map<String, Object>) dataSnapshot.getValue());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    collectItems2((Map<String, Object>) dataSnapshot.getValue());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        // finally, set button listeners
        // cannot set listner sooner,
        // because the data must be loaded from the database first
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isInCart) {
                    // if item NOT in cart yet, then add it
                    //ref.child(MainActivity.selectedItem.itemId);
                    FirebaseDatabase.getInstance().getReference("cart").child(MainActivity.selectedItem.userId).child(MainActivity.selectedItem.itemId).setValue( new Item(MainActivity.selectedItem.itemId,
                            MainActivity.selectedItem.userId,
                            MainActivity.selectedItem.name,
                            MainActivity.selectedItem.description,
                            MainActivity.selectedItem.location,
                            MainActivity.selectedItem.images,
                            MainActivity.selectedItem.username,
                            MainActivity.selectedItem.userPhoto) );

                    isInCart = true;
                    btnCart.setText("Remove from cart"); //change button text
                    Toast.makeText(getApplicationContext(), "Item added to Cart", Toast.LENGTH_SHORT).show();
                } else {
                    // remove from cart
                    // ref: cart/[userId]/[itemId] => removeValue() at this node
                    FirebaseDatabase.getInstance().getReference("cart").child(MainActivity.selectedItem.userId).child(MainActivity.selectedItem.itemId).removeValue();
                    btnCart.setText("Add to cart");
                    isInCart = false;
                    Toast.makeText(getApplicationContext(), "Item removed from cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isInFavorite) {
                    // if item NOT in favorite list yet, then add it
                    //ref.child(MainActivity.selectedItem.itemId);
                    FirebaseDatabase.getInstance().getReference("favorite").child(MainActivity.selectedItem.userId).child(MainActivity.selectedItem.itemId).setValue( new Item(MainActivity.selectedItem.itemId,
                            MainActivity.selectedItem.userId,
                            MainActivity.selectedItem.name,
                            MainActivity.selectedItem.description,
                            MainActivity.selectedItem.location,
                            MainActivity.selectedItem.images,
                            MainActivity.selectedItem.username,
                            MainActivity.selectedItem.userPhoto) );

                    isInFavorite = true;
                    btnFavorite.setText("Remove from favorite"); //change button text
                    Toast.makeText(getApplicationContext(), "Item added to Favorite", Toast.LENGTH_SHORT).show();
                } else {
                    // remove from cart
                    // ref: cart/[userId]/[itemId] => removeValue() at this node
                    FirebaseDatabase.getInstance().getReference("favorite").child(MainActivity.selectedItem.userId).child(MainActivity.selectedItem.itemId).removeValue();
                    btnFavorite.setText("Add to favorite");
                    isInFavorite = false;
                    Toast.makeText(getApplicationContext(), "Item removed from favorite", Toast.LENGTH_SHORT).show();
                }
            }
        });
    } // end of onCreate()

    // call by prepareItemData() method.
    // to iterate through the HashMap containing all the data
    // fetched from the Database, then add to an array of Item object
    private void collectItems(Map<String,Object> item) {
        Item item2;

        //iterate through each item, ignore itemId
        for (Map.Entry<String, Object> entry : item.entrySet()) {
            Map singleItem = (Map) entry.getValue();
            // compare itemID
            if ( MainActivity.selectedItem.itemId == singleItem.get("itemId").toString() ) {
                btnCart.setText("Remove from cart");
                isInCart = true;
                // stop for loop
                //return;
            }
        }
    }

    // call by prepareItemData() method.
    // to iterate through the HashMap containing all the data
    // fetched from the Database, then add to an array of Item object
    private void collectItems2(Map<String,Object> item) {
        Item item2;

        //iterate through each item, ignore itemId
        for (Map.Entry<String, Object> entry : item.entrySet()) {
            Map singleItem = (Map) entry.getValue();
            // compare itemID
            if ( MainActivity.selectedItem.itemId == singleItem.get("itemId").toString() ) {
                btnFavorite.setText("Remove from favorite");
                isInFavorite = true;
                // stop for loop
                //return;
            }
        }
    }
}
