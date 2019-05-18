package com.omkar.kumbhar.rentaride.Activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.omkar.kumbhar.rentaride.Method.UserData;

public class MainActivity extends AppCompatActivity {
    SharedPreferences pref ;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser cUser;
    UserData userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userData = new UserData();
        cUser = mAuth.getCurrentUser();
        if(cUser != null){
            cUser.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(cUser.isEmailVerified()){
                        userData.AddEmailVerified(true,getApplicationContext());
                    }
                }
            });
            userData.AddProfilePic(String.valueOf(cUser.getPhotoUrl()),getApplicationContext());
        }
    }
    private void SetFirstRun() {
        pref =  PreferenceManager.getDefaultSharedPreferences(this);
        if (!pref.getBoolean("firstTime", false)) {
            // <---- run your one time code here
            Intent i = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
            // mark first time has ran.
            SharedPreferences.Editor editor = pref.edit();

            editor.putBoolean("firstTime", true);
            editor.apply();
        }else {
            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void AlertBuilder(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("You are not connected to internet please connect to the internet");
        builder.setTitle("No Network Connection");
        builder.setCancelable(false);

      builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
              dialog.dismiss();
          }
      });
      builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              finishAffinity();
              System.exit(0);
              dialog.dismiss();
          }
      });
        AlertDialog alert11 = builder.create();
        alert11.show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(isNetworkAvailable()){
            SetFirstRun();
        }else {
            AlertBuilder();
        }
    }
}
