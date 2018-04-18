package com.example.huybq.uog_finalproject;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by huybq on 16/1/2018.
 *
 * REFERENCE (official document from Google): https://developer.android.com/guide/topics/search/search-dialog.html
 *
 * This activity receives the search query from MainActivity,
 * then handles the request and displays the result as list.
 */

public class SearchableActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            //Custom method to perform the search
            //doMySearch(query);
        }
    }
}
