package com.omkar.kumbhar.rentaride.Method;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class UserData {
    private Context context;

    public boolean AddData(String Name, String Uid, String UserEmail, Context context){
        boolean result = false;
        try {
            SharedPreferences ShareData = context.getSharedPreferences("UserData", 0);
            SharedPreferences.Editor editor = ShareData.edit();
            editor.putString("UserName",Name);
            editor.putString("UserUid",Uid);
            editor.putString("UserEmail",UserEmail);
            editor.putBoolean("UserLoginStatus",true);
            editor.apply();
            result = true;
        } catch (Exception e) {
            Toast.makeText(context, "Something Went Wrong While Writing User Data", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public boolean RemoveAddData( Context context){
        boolean result = false;
        try {
            SharedPreferences ShareData = context.getSharedPreferences("UserData", 0);
            SharedPreferences.Editor editor = ShareData.edit();
            editor.clear();
            editor.apply();
            result = true;
        } catch (Exception e) {
            Toast.makeText(context, "Something Went Wrong While Writing User Data", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public boolean AddEmailVerified(boolean status,Context context){
        boolean result = false;

        try {
            SharedPreferences ShareData = context.getSharedPreferences("UserData", 0);
            SharedPreferences.Editor editor = ShareData.edit();
            editor.putBoolean("UserVerified",status);
            editor.apply();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public  boolean getEmailVerified(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("UserData", 0);
        return ShareData.getBoolean("UserVerified",false);
    }

    public  void AddProfilePic(String Profile,Context context){
        SharedPreferences ShareData = context.getSharedPreferences("UserData", 0);
        SharedPreferences.Editor editor = ShareData.edit();
        editor.putString("Profile",Profile);
        editor.apply();
    }
    public  String getProfilePic(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("UserData", 0);
        return ShareData.getString("Profile",null);
    }

    public  boolean AddCity(String City,Context context){
        boolean result = false;
        try {
            SharedPreferences ShareData = context.getSharedPreferences("UserData", 0);
            SharedPreferences.Editor editor = ShareData.edit();
            editor.putString("UserCity",City);
            editor.apply();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public  String getCity(Context context){
        this.context = context;
        String name ;
        SharedPreferences ShareData = context.getSharedPreferences("UserData", 0);
        name = ShareData.getString("UserCity",null);
        return name;
    }

    public  String getUserName(Context context){
        this.context = context;
        String name ;
        SharedPreferences ShareData = context.getSharedPreferences("UserData", 0);
        name = ShareData.getString("UserName",null);
        return name;

    }



    public  String getUserUid(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("UserData", 0);
        return ShareData.getString("UserUid",null);

    }

    public  String getEmail(Context context){
        String email ;
        SharedPreferences ShareData = context.getSharedPreferences("UserData", 0);
        return ShareData.getString("UserEmail",null);

    }
    public  boolean getLoginStatus(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("UserData", 0);
        return  ShareData.getBoolean("UserLoginStatus",false);
    }





}
