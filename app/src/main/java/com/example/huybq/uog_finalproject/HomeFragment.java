package com.example.huybq.uog_finalproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Map.Entry;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
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

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mAdapter = new ItemAdapter(MainActivity.itemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        prepareItemData();

        return view;
    }

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

    private void collectItems(Map<String,Object> item) {
        Item item2;
        // clear the list first
        MainActivity.itemList.clear();
        //iterate through each item, ignore itemId
        for (Map.Entry<String, Object> entry : item.entrySet()) {
            Map singleItem = (Map) entry.getValue();
            item2 = new Item(entry.getKey(), singleItem.get("userId").toString(), singleItem.get("name").toString(), singleItem.get("description").toString(), singleItem.get("location").toString(), singleItem.get("images").toString());
            MainActivity.itemList.add(item2);
        }
    }
// end of class
}