package com.example.huybq.uog_finalproject;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huybq on 16/1/2018.
 *
 * REFERENCE (official document from Google): https://developer.android.com/guide/topics/search/search-dialog.html
 *
 * This activity receives the search query from MainActivity,
 * then handles the request and displays the result as list.
 */

public class SearchableActivity extends AppCompatActivity {
    //private CustomAdapter adapter;
    //private ListView listView;
    private RecyclerView recyclerView;
    private ItemAdapter mAdapter;
    private Toolbar toolBar;

    // contains all the item object that will be displayed in Recycler View
    public List<Item> matchedItem = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            matchedItem.clear();
            //Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();

            // iterate through the itemList to get a list of item name
            // the list is used to perform searching
            for (Item item : MainActivity.itemList) {
                if (item.name.toLowerCase().contains(query.toLowerCase())) {
                    //System.out.println("Found a match");
                    matchedItem.add(item);
                }
            }

            // initiate toolbar
            toolBar = (Toolbar) findViewById(R.id.activity_search_toolbar);
            toolBar.setTitle("Results for \"" + query + "\"");
            setSupportActionBar(toolBar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // setup the recycler view after getting an item array
            mAdapter = new ItemAdapter(matchedItem);
            recyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(mAdapter);

            // listener for recycler view touch
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    // save the item object that user click on
                    MainActivity.selectedItem = matchedItem.get(position);

                    // start Item Activity
                    startActivity(new Intent(getApplicationContext(), ItemActivity.class));
                    // do Not finished() Main Activity here to preserve its static variables
                }
                @Override
                public void onLongClick(View view, int position) {
                }
            }));
        }
    } //end of onCreated()
}// end of class