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
import com.omkar.kumbhar.rentaride.Method.OrderModel;
import com.omkar.kumbhar.rentaride.Method.ResultModel;
import com.omkar.kumbhar.rentaride.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    List<OrderModel> result;
    private Context mContext;


    public OrderAdapter(List<OrderModel> result, Context mContext) {
        this.result = result;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.order_layout, viewGroup, false);
        return new OrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder myViewHolder, int i) {
        final OrderModel resultModel = result.get(i);
        String seater = myViewHolder.ResultSeater.getText()+ " : "+ resultModel.getTillTime();
        String rent =    myViewHolder.ResultPrice.getText() + resultModel.getRentPaid();
        String fuel = myViewHolder.ResultFuelType.getText()+ " : " + resultModel.getFromTime();



        myViewHolder.resultName.setText(resultModel.getOrderID());
        myViewHolder.ResultSeater.setText(seater );
        myViewHolder.ResultPrice.setText(rent);
        myViewHolder.ResultFuelType.setText(fuel);


        if(resultModel.getImgUrl() != null) {
            Glide.with(mContext).load(resultModel.getImgUrl()).into(myViewHolder.ResultImage);
        }
        myViewHolder.RLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, ""+ resultModel.getDocId(), Toast.LENGTH_SHORT).show();
           // Intent intent = new Intent(mContext,OrderReport.class);
           // intent.putExtra("DocId",resultModel.getDocId());
           // intent.putExtra("Vehicle",Vehicle);
           // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           // mContext.startActivity(intent);
            }
        });
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
            ResultImage = (ImageView) itemView.findViewById(R.id.ResultImage);
            ResultDistance = (TextView) itemView.findViewById(R.id.ResultFromTime);
            ResultSeater = (TextView) itemView.findViewById(R.id.ResultTillTime);
            ResultFuelType = (TextView) itemView.findViewById(R.id.ResultFuelType);
            ResultPrice = (TextView) itemView.findViewById(R.id.ResultPrice);




            RLayout = (RelativeLayout) itemView.findViewById(R.id.parent);
        }
    }
}
