package com.example.huybq.uog_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ItemAdapter mAdapter;
    DatabaseReference ref;
    FirebaseDatabase database;
    // contains all the item object that will be displayed in Recycler View
    public static List<Item> cartItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // initiate views
        toolbar = (Toolbar) findViewById(R.id.cart_toolbar);
        toolbar.setTitle("Your shopping cart");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // need to prepare item data before load it into recycler view
        mAdapter = new ItemAdapter(cartItemList);
        prepareItemData();

        // initiate recycler view and set it up
        recyclerView = (RecyclerView) findViewById(R.id.cart_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        // listener for recycler view
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // save the item object that user click on
                MainActivity.selectedItem = cartItemList.get(position);

                // start Item Activity
                startActivity(new Intent(getApplicationContext(), ItemActivity.class));
                // do Not finished() Main Activity here to preserve its static variables
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));

    } // end of onCreate()

    // retrieve data from the database
    // and finally save all data to cartItemList<Item>
    private void prepareItemData() {
        database = FirebaseDatabase.getInstance();

        // point to the node cart/[userId] in Firebase
        ref = database.getReference("cart").child( FirebaseAuth.getInstance().getCurrentUser().getUid() );

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

        mAdapter.notifyDataSetChanged();
    }

    // call by prepareItemData() method.
    // to iterate through the HashMap containing all the data
    // fetched from the Database, then add to an array of Item object
    private void collectItems(Map<String,Object> item) {
        Item item2;

        // clear the list first
        cartItemList.clear();

        //iterate through each item, ignore itemId
        for (Map.Entry<String, Object> entry : item.entrySet()) {
            Map singleItem = (Map) entry.getValue();
            item2 = new Item(entry.getKey(), singleItem.get("userId").toString(),
                                                singleItem.get("name").toString(),
                                                singleItem.get("description").toString(),
                                                singleItem.get("location").toString(),
                                                singleItem.get("images").toString(),
                                                singleItem.get("username").toString(),
                                                singleItem.get("userPhoto").toString());
            cartItemList.add(item2); // add item to array list
        }
    }
}
