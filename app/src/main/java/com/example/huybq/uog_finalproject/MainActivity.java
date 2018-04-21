package com.example.huybq.uog_finalproject;

// REFERENCE for sending mail by using Gmail API:
// https://developers.google.com/gmail/api/quickstart/android
// https://github.com/QuizSystem/GmailAPI/blob/master/app/src/main/java/com/haynhanh/gmailapi/MainActivity.java

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Variables
    private Toolbar toolbar;
    private MenuItem searchItem;
    private SearchView searchBar;
    private SearchManager searchManager;
    private FloatingActionButton fab;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private int currentFragmentId = 1; // first fragment when the app first run is Home Fragment

    // contains all the item object that will be displayed in Recycler View
    public static List<Item> itemList = new ArrayList<>();

    // contains the current selected item object that will be used
    // to display in Item Activity.
    public static Item selectedItem = new Item();

    // Listener for selecting items of bottom navigation bar
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                // When use tap Home Button
                case R.id.navigation_home:
                    //Check if currentFragment
                    if (!isCurrentFragment(1)) {
                        currentFragmentId = 1;

                        // Make the searchbar visible
                        searchBar.setVisibility(View.VISIBLE);

                        // Replace current fragment with the new fragment
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.activity_main_content, new HomeFragment());
                        fragmentTransaction.commit();
                    }
                    return true;

                //When user tap Menu button
                case R.id.navigation_menu:
                    //Check currentFragment
                    if (!isCurrentFragment(2)) {
                        currentFragmentId = 2;

                        //Hide the searchbar in Menu
                        searchBar.setVisibility(View.GONE);

                        //Replace with the new fragment
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.activity_main_content, new MenuFragment());
                        fragmentTransaction.commit();
                    }
                    return true;
                // end of switch
            }
            return false;
        }
    };

    // Listener for clicking on the Search Bar
    private View.OnClickListener searchBarSelectedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            searchBar.setIconified(false); //make the search widget get focused --> display keyboard
        }
    };

    //Listener for clicking on the FAB
    private View.OnClickListener fabClickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Toast.makeText(getApplicationContext(), "FAB clicked", Toast.LENGTH_SHORT).show();
            // start CartActivity
            startActivity(new Intent(getApplicationContext(), CartActivity.class));
        }
    };

    @Override
    // DEFAULT: invoked to load UI views when app first run
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Search Bar configuration
        searchBar = (SearchView) findViewById(R.id.searchBar);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE); //set the searchable configuration
        searchBar.setSearchableInfo(searchManager.getSearchableInfo( new ComponentName(this, SearchableActivity.class)));
        searchBar.setOnClickListener(searchBarSelectedListener); // make the searchbar react when user tap it
        searchBar.setImeOptions(EditorInfo.IME_ACTION_SEARCH); //change Return key of the keyboard to Search key

        // Bottom Navigation View configuration
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener); //set listener

        // FAB configuration
        fab = (FloatingActionButton) findViewById(R.id.fab_cart);
        fab.setOnClickListener(fabClickedListener); //set listener

        // Get fragment manager from this Activity to manage the changing of fragments
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main_content, new HomeFragment());
        fragmentTransaction.commit();
    }

    // Check if the current fragment is the one that user clicks on the Bottom Navigation View
    private boolean isCurrentFragment(int id) { return id == currentFragmentId; }
    // end of class MainActivity
}
