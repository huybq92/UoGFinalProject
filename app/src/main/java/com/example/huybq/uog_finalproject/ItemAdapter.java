package com.example.huybq.uog_finalproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private List<Item> itemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, location;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            location = (TextView) view.findViewById(R.id.location);
        }
    }

    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.name.setText(item.name);
        holder.location.setText(item.location);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
