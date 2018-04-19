package com.example.huybq.uog_finalproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    // Variables
    private Toolbar toolbar;
    private MenuItem searchItem;
    private SearchView searchBar;
    private SearchManager searchManager;
    private FloatingActionButton fab;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    //FirebaseDatabase database;
    //FirebaseAuth auth;
    //FirebaseUser user;

    private int currentFragmentId = 1; // first fragment when the app first run is 'Home'

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

                // When user tap Categories button
                case R.id.navigation_dashboard:
                    //Check currentFragment
                    if (!isCurrentFragment(2)) {
                        currentFragmentId = 2;

                        // Make the searchbar visible
                        searchBar.setVisibility(View.VISIBLE);

                        //Replace with the new fragment
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.activity_main_content, new CategoryFragment());
                        fragmentTransaction.commit();
                    }
                    return true;

                //When user tap Menu button
                case R.id.navigation_menu:
                    //Check currentFragment
                    if (!isCurrentFragment(3)) {
                        currentFragmentId = 3;

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
            Toast.makeText(getApplicationContext(), "FAB clicked", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    // DEFAULT: invoked to load UI views when app first run
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get firebase auth instance
        //auth = FirebaseAuth.getInstance();
        //get current signed-in user
        //user = FirebaseAuth.getInstance().getCurrentUser();

        // Search Bar configuration
        searchBar = (SearchView) findViewById(R.id.searchBar);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE); //set the searchable configuration
        //searchBar.setSearchableInfo(searchManager.getSearchableInfo( new ComponentName(this, SearchableActivity.class)));
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
        //fragmentTransaction.add(R.id.activity_main_content, new HomeFragment());
        fragmentTransaction.commit();

        /*
        // Retrieve an instance of Firebase database
        //database = FirebaseDatabase.getInstance();
        // reference the location you want to write to.
        //DatabaseReference myRef = database.getReference("message");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        */

        //database = FirebaseDatabase.getInstance();
        //DatabaseReference ref = database.getReference(); // root node
        //Map<String, Item> item = new HashMap<>();
        //item.put("222222222222", new Item("test", "test", "test", "test"));
    }

    // Check if the current fragment is the one that user clicks on the Bottom Navigation View
    private boolean isCurrentFragment(int id) { return id == currentFragmentId; }

// end of class MainActivity
}
