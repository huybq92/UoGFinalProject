package com.example.huybq.uog_finalproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CategoryFragment extends Fragment {
    // Views declaration
    TextView text;

    // Default
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Default initiation to inflate the views
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        text = (TextView) view.findViewById(R.id.cate_textview);
        text.setText("Categories");

        return view;
    }

// end of class
}
