package com.omkar.kumbhar.rentaride.Method;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class AdUser {
    public boolean AddImageData(String Source, Context context){
        boolean result = false;
        try {
            SharedPreferences ShareData = context.getSharedPreferences("UserImage", 0);
            SharedPreferences.Editor editor = ShareData.edit();
            editor.putString("Source",Source);
            editor.putBoolean("Image",true);
            editor.apply();
            result = true;
        } catch (Exception e) {
            Toast.makeText(context, "Something Went Wrong While Searching the data", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public String  getUserImageUrl(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("UserImage", 0);
        return ShareData.getString("Source",null);
    }

    public boolean isImageInserted(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("UserImage", 0);
        return  ShareData.getBoolean("Image",false);
    }

}
