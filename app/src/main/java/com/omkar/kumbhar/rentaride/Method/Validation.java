package com.omkar.kumbhar.rentaride.Method;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.omkar.kumbhar.rentaride.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class Validation {
    public boolean InputValidation(EditText edtUsername) {
        boolean result;
        if (edtUsername.getText().toString().trim().equalsIgnoreCase("")) {
            edtUsername.setError("This field can not be blank");
            // edtUsername.setBackgroundColor(Color.rgb(249, 72, 75));
            result = false;
        }else {
            result = true;
        }
        return result;
    }
    public boolean EmailValidation(EditText edtEmail){
        boolean result;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (edtEmail.getText().toString().trim().equalsIgnoreCase("")) {
            edtEmail.setError("This field can not be blank");
            // edtEmail.setBackgroundColor(Color.rgb(249, 72, 75));
            result = false;
        }else if(!edtEmail.getText().toString().trim().matches(emailPattern)&& edtEmail.getText().toString().trim().length() > 0 ) {
            edtEmail.setError("Invalid Email Address");
            //edtEmail.setBackgroundColor(Color.rgb(249, 72, 75));
            result = false;
        }else {
            result = true;
        }
        return result;
    }
    public boolean PasswordVerification(EditText pass1, EditText pass2){


        boolean result = true;

        if(!pass1.getText().toString().trim().equals(pass2.getText().toString().trim())){
            result = false;
            pass1.setError("Password Dose not match");
            pass2.setError("Password Dose not match");
            //pass1.setBackgroundColor(Color.rgb(249, 72, 75));
            //pass2.setBackgroundColor(Color.rgb(249, 72, 75));

        }else {
            pass1.setBackgroundColor(Color.rgb(255, 255, 255));
            //pass2.setBackgroundResource(android.R.drawable.editbox_background);
        }
        return result;
    }
    public boolean ValidateSpinner(Spinner vehicleSeater, Spinner vehicleFuel, Spinner vehicleType, Context applicationContext) {
        int seaterIndex  = (int) vehicleSeater.getSelectedItemId();
        int fuelIndex = (int) vehicleFuel.getSelectedItemId();
        int typeIndex = (int) vehicleType.getSelectedItemId();
        if (seaterIndex == 0){

            Toasty.info(applicationContext
                    , "Please Select Seat of the vehicle",
                    Toast.LENGTH_SHORT, true).show();

           /* Toast toast = Toast.makeText(applicationContext, "Please Select Seat of the vehicle", Toast.LENGTH_LONG);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toast.getView()
                        .setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                toast.show();
            }else {
                toast.getView().setBackgroundColor(Color.RED);
                toast.show();
            }*/
            return false;
        }else if(fuelIndex == 0){
            Toast t = Toast.makeText(applicationContext, "Please fuel type  of the vehicle", Toast.LENGTH_SHORT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                t.getView()
                        .setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                t.show();
            }else {
                t.getView().setBackgroundColor(Color.RED);
                t.show();
            }
            return false;
        }else if (typeIndex == 0){
            Toast toast =  Toast.makeText(applicationContext, "Please  type  of the vehicle", Toast.LENGTH_SHORT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toast.getView()
                        .setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                toast.show();
            }else {
                toast.getView().setBackgroundColor(Color.RED);
                toast.show();
            }
            return false;
        }
        return  true;
    }

    public boolean SingleSpiVal(Spinner sp,String msg,Context context){
           if(sp.getSelectedItemId() ==0 ){
               Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
               return false;
           }
           return true;
    }

    public boolean FilterValidation(EditText source, EditText startingTime, EditText startingDate, EditText endingTime, EditText endingDate, Context context){
        boolean result = true;
        if (source.getText().toString().trim().length() <= 0) {
            source.setError("This field can not be blank");
            result = false;
        }else {
            result = true;
        }

        if (startingTime.getText().toString().trim().length() <= 0) {
            startingTime.setError("This field can not be blank");
            result = result && false;
        }else {

            result =result && true;
        }

        if (startingDate.getText().toString().trim().equalsIgnoreCase("")) {
            startingDate.setError("This field can not be blank");
            result =result && false;
        }else {
            result =result && true;

        }

        if (endingTime.getText().toString().trim().equalsIgnoreCase("")) {
            endingTime.setError("This field can not be blank");
            result =result && false;
        }else {
            result =result && true;
        }

        if (endingDate.getText().toString().trim().equalsIgnoreCase("")) {
            endingDate.setError("This field can not be blank");
            result =result && false;
        }else {
            result =result && true;
        }


       // boolean isvalid = ValidateDate(startingDate.getText().toString().trim(),startingTime.getText().toString().trim(), context);

        return result;
    }

    private boolean ValidateDate(String date,String time, Context context)  {


        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date strDate = null;
        try {
            strDate = sdf.parse(date + " "+ time);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Toast.makeText(context, System.currentTimeMillis()+"           " + strDate.getTime() , Toast.LENGTH_LONG).show();
        Toast.makeText(context, ""+System.nanoTime(), Toast.LENGTH_SHORT).show();



       /* if (System.currentTimeMillis() > strDate.getTime()) {
            Toast.makeText(context, "PLEASE SELECT APPROPRIATE DATE", Toast.LENGTH_SHORT).show();
            return  false;
        }
        else {
            return true;
        }*/

        return true;
    }
}
