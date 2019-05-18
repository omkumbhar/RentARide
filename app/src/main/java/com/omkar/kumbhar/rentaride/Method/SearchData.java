package com.omkar.kumbhar.rentaride.Method;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class SearchData {
    public boolean AddSearchData(String Source, String StartTime, String StartDate, String EndTime, String EndDate, String Seater, Context context){
        boolean result = false;
        try {
            SharedPreferences ShareData = context.getSharedPreferences("SearchData", 0);
            SharedPreferences.Editor editor = ShareData.edit();
            editor.putString("Source",Source);
            editor.putString("StartTime",StartTime);
            editor.putString("StartDate",StartDate);
            editor.putString("EndTime",EndTime);
            editor.putString("EndDate",EndDate);
            editor.putString("Seater",Seater);
            editor.apply();
            result = true;
        } catch (Exception e) {
            Toast.makeText(context, "Something Went Wrong While Searching the data", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public  String getSource(Context context){
        String source ;
        SharedPreferences ShareData = context.getSharedPreferences("SearchData", 0);

        source = ShareData.getString("Source",null);
        return source;
    }
    public  String getStartTime(Context context){
        String startTime ;
        SharedPreferences ShareData = context.getSharedPreferences("SearchData", 0);

        startTime = ShareData.getString("StartTime",null);
        return startTime;
    }

    public  String getStartDate(Context context){
        String startDate ;
        SharedPreferences ShareData = context.getSharedPreferences("SearchData", 0);

        startDate = ShareData.getString("StartDate",null);
        return startDate;
    }
    public  String getEndTime(Context context){
        String endTime ;
        SharedPreferences ShareData = context.getSharedPreferences("SearchData", 0);

        endTime = ShareData.getString("EndTime",null);
        return endTime;
    }
    public  String getEndDate(Context context){
        String endDate ;
        SharedPreferences ShareData = context.getSharedPreferences("SearchData", 0);

        endDate = ShareData.getString("EndDate",null);
        return endDate;
    }
    public  String getSeater(Context context){
        String seater ;
        SharedPreferences ShareData = context.getSharedPreferences("SearchData", 0);

        seater = ShareData.getString("Seater",null);
        return seater;
    }

    public boolean AddSelectedRef(String id,String vehicle,String rent,Context context){
        boolean result = false;
        try {
            SharedPreferences ShareData = context.getSharedPreferences("SearchData", 0);
            SharedPreferences.Editor editor = ShareData.edit();
            editor.putString("DocID",id);
            editor.putString("Vehicle",vehicle);
            editor.putString("Rent",rent);
            editor.apply();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public  String getVehicle(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("SearchData", 0);
        return ShareData.getString("Vehicle",null);

    }

    public  String getDocID(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("SearchData", 0);
        return ShareData.getString("DocID",null);

    }

    public  String getRent(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("SearchData", 0);
        return ShareData.getString("Rent",null);

    }


}
