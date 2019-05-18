package com.omkar.kumbhar.rentaride.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.omkar.kumbhar.rentaride.Activity.OrderReport;
import com.omkar.kumbhar.rentaride.Method.ResultModel;
import com.omkar.kumbhar.rentaride.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    List<ResultModel> result;
    private Context mContext;
    String Vehicle;

    public SearchAdapter(List<ResultModel> result, Context mContext, String vehicle) {
        this.result = result;
        this.mContext = mContext;
        Vehicle = vehicle;
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.result_view, viewGroup, false);
        return new SearchAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder myViewHolder, int i) {
        final ResultModel resultModel = result.get(i);
        String seater = "Seater"+ " : "+ resultModel.getSeater();
        String rent =    mContext.getString(R.string.ruppee) + resultModel.getRent();
        String fuel = "Fuel Type"+ " : " + resultModel.getFuleType();

        String distance = "Distance"+ " : " + getDistance(4,5);

        myViewHolder.resultName.setText(resultModel.getCarName());
        myViewHolder.ResultSeater.setText(seater);
        myViewHolder.ResultPrice.setText(rent);
        myViewHolder.ResultFuelType.setText(fuel);
        myViewHolder.ResultDistance.setText(distance);

        if(resultModel.getImgUrl() != null) {
            Glide.with(mContext).load(resultModel.getImgUrl()).into(myViewHolder.ResultImage);
        }
        myViewHolder.RLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(mContext, ""+ resultModel.getDocId(), Toast.LENGTH_SHORT).show();
               /* Intent intent = new Intent(mContext,OrderReport.class);
                intent.putExtra("DocId",resultModel.getDocId());
                intent.putExtra("Vehicle",Vehicle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);*/
            }
        });
    }

    private int getDistance(int a, int b) {
        return a+b;
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView resultName, ResultPrice, ResultSeater, ResultFuelType, ResultDistance ;
        ImageView ResultImage;
        RelativeLayout RLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            resultName = (TextView) itemView.findViewById(R.id.ResultName);
            ResultPrice = (TextView) itemView.findViewById(R.id.ResultPrice);
            ResultSeater = (TextView) itemView.findViewById(R.id.ResultSeater);
            ResultFuelType = (TextView) itemView.findViewById(R.id.ResultFuelType);
            ResultDistance = (TextView) itemView.findViewById(R.id.ResultDistance);
            ResultImage = (ImageView) itemView.findViewById(R.id.ResultImage);
            RLayout = (RelativeLayout) itemView.findViewById(R.id.parent_layout);
        }
    }
}
