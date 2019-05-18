package com.omkar.kumbhar.rentaride.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.omkar.kumbhar.rentaride.R;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder>{
    List<String> group =new ArrayList<String>();

    public CityAdapter(List<String> group) {
        this.group = group;
    }

    @NonNull
    @Override
    public CityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.city_select, viewGroup, false);
        return new CityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.CityName.setText(group.get(i));
    }

    @Override
    public int getItemCount() {
        return group.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView CityName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            CityName = (TextView) itemView.findViewById(R.id.CityName);

        }
    }
}
