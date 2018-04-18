package com.example.huybq.uog_finalproject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuFragment extends Fragment {

    // Views declaration
    ListView listMenu;

    // Defined Array values to show in ListView
    String[] menu_items = new String[] {"You need to login first"};
    String[] menu_items_after_login = new String[] {
            "Your account",
            "Your orders",
            "Your favorite",
            "Sell stuffs",
            "Log out"
    };
    //Array adapter to populate above array to the listview
    ArrayAdapter<String> arrayAdapter;

    // listener to check if user is currently signed-in
    // if not, start Login Activity to require user to login.
    // if yes, continue with Main Activity
    private FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                Toast.makeText(getActivity(), "no login", Toast.LENGTH_SHORT).show();
                arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.fragment_menu_listview_rowitem, R.id.fragment_menu_listview_rowitem_textview, menu_items);
            } else {
                Toast.makeText(getActivity(), "login", Toast.LENGTH_SHORT).show();
                arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.fragment_menu_listview_rowitem, R.id.fragment_menu_listview_rowitem_textview, menu_items_after_login);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Default initiation to inflate the views
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        // check if user signed in or not
        // to determine the menu items
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            //Toast.makeText(getActivity(), "no login", Toast.LENGTH_SHORT).show();
            arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.fragment_menu_listview_rowitem, R.id.fragment_menu_listview_rowitem_textview, menu_items);
        } else {
            //Toast.makeText(getActivity(), "login", Toast.LENGTH_SHORT).show();
            arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.fragment_menu_listview_rowitem, R.id.fragment_menu_listview_rowitem_textview, menu_items_after_login);
        }

        listMenu = (ListView) view.findViewById(R.id.fragment_menu_list);
        listMenu.setAdapter(arrayAdapter);
        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);

                switch (selectedItem) {
                    case "You need to login first":
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        //getActivity().finish();
                        break;
                    case "Your account":
                        //startActivity(new Intent(getActivity(), LoginActivity.class));
                        //getActivity().finish();
                        Toast.makeText(getActivity(), selectedItem, Toast.LENGTH_SHORT).show();
                        break;
                    case "Your orders":
                        //startActivity(new Intent(getActivity(), LoginActivity.class));
                        //getActivity().finish();
                        Toast.makeText(getActivity(), selectedItem, Toast.LENGTH_SHORT).show();
                        break;
                    case "Your favorite":
                        //startActivity(new Intent(getActivity(), LoginActivity.class));
                        //getActivity().finish();
                        Toast.makeText(getActivity(), selectedItem, Toast.LENGTH_SHORT).show();
                        break;
                    case "Sell stuffs":
                        //startActivity(new Intent(getActivity(), LoginActivity.class));
                        //getActivity().finish();
                        Toast.makeText(getActivity(), selectedItem, Toast.LENGTH_SHORT).show();
                        break;
                    case "Log out":
                        MainActivity.auth.signOut(); //sign out Firebase
                        getActivity().finish();
                        startActivity(getActivity().getIntent()); // reload Main Activity
                        break;
                }
            }
        });

        return view;
    }

    /*
    @Override
    public void onStart() {
        super.onStart();
        MainActivity.auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            MainActivity.auth.removeAuthStateListener(authListener);
        }
    }
    */
// end of class
}
