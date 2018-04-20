package com.example.huybq.uog_finalproject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

// REFERENCE: https://www.androidhive.info/2016/01/android-working-with-recycler-view/

public class HomeFragment extends Fragment {

    // Views declaration
    TextView text;
    private RecyclerView recyclerView;
    private ItemAdapter mAdapter;
    DatabaseReference ref;
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Default initiation to inflate the views
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // need to prepare item data before load it into recycler view
        mAdapter = new ItemAdapter(MainActivity.itemList);
        prepareItemData();

        // initiate recycler view and set it up
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        // listener for recycler view
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // save the item object that user click on
                MainActivity.selectedItem = MainActivity.itemList.get(position);
                //Toast.makeText(getActivity(), MainActivity.selectedItem.location + " is selected!", Toast.LENGTH_SHORT).show();
                // start Item Activity
                startActivity(new Intent(getActivity(), ItemActivity.class));
                // do Not finished() Main Activity here to preserve its static variables
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        return view;
    }

    // retrieve data from the database
    // and finally save all data to MainActivity.itemList<Item>
    private void prepareItemData() {
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("item");

        // retrieve data from firebase
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

    // call by prepareItemData().
    // to iterate through the HashMap containing all the data
    // fetched from the Database, then add to an array of Item object
    private void collectItems(Map<String,Object> item) {
        Item item2;
        // clear the list first
        MainActivity.itemList.clear();
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
            MainActivity.itemList.add(item2);
        }
    }
// end of class
}