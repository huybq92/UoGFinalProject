package com.example.huybq.uog_finalproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuFragment extends Fragment {

    // Views declaration
    ListView listMenu;

    // Defined Array values to show in ListView
    String[] menu_items = new String[] {
            "Login",
            "Sell stuffs",
            "Your orders",
            "Your list",
            "Item 4",
            "Exit app"
    };

    String[] menu_items_after_login = new String[] {
            "Your account",
            "Sell stuffs",
            "Your orders",
            "Your list",
            "Item 5",
            "Exit app"
    };

    //Array adapter to populate above array to the listview
    ArrayAdapter<String> arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Default initiation to inflate the views
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        listMenu = (ListView) view.findViewById(R.id.fragment_menu_list);
        arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.fragment_menu_listview_rowitem, R.id.fragment_menu_listview_rowitem_textview, menu_items);
        listMenu.setAdapter(arrayAdapter);

        return view;
    }

// end of class
}
