package com.omkar.kumbhar.rentaride.Method;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class PaymentData {

    public boolean AddCarData(String holderName, String cardNumber, String expiryMon,String expiryYear, Context context){
        boolean result = false;
        try {
            SharedPreferences ShareData = context.getSharedPreferences("CardPaymentData", 0);
            SharedPreferences.Editor editor = ShareData.edit();
            editor.putString("CardHolderName",holderName);
            editor.putString("CardNumber",cardNumber);
            editor.putString("ExpiryMon",expiryMon);
            editor.putString("ExpiryYear",expiryYear);
            editor.putBoolean("CardData",true);
            editor.apply();
            result = true;
        } catch (Exception e) {
            Toast.makeText(context, "Something Went Wrong While Writing User Data", Toast.LENGTH_SHORT).show();
        }
        return result;
    }


    public boolean AddDebData(String holderName, String cardNumber, String expiryMon, String expiryYear, int debitCardType, Context context){
        boolean result = false;
        try {
            SharedPreferences ShareData = context.getSharedPreferences("DebitPaymentData", 0);
            SharedPreferences.Editor editor = ShareData.edit();
            editor.putString("DebitHolderName",holderName);
            editor.putString("DebitNumber",cardNumber);
            editor.putString("ExpiryMon",expiryMon);
            editor.putString("ExpiryYear",expiryYear);
            editor.putInt("DebitCardPosition", debitCardType);
            editor.putBoolean("DebitData",true);
            editor.apply();
            result = true;
        } catch (Exception e) {
            Toast.makeText(context, "Something Went Wrong While Writing User Data.", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    //CARD DATA RETRIEVAL
    public String getCardHolderName(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("CardPaymentData", 0);
        return  ShareData.getString("CardHolderName",null);
    }

    public String getCardNumber(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("CardPaymentData", 0);
        return  ShareData.getString("CardNumber",null);
    }

    public String getCardExpiryMon(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("CardPaymentData", 0);
        return  ShareData.getString("ExpiryMon",null);
    }

    public String getCardExpiryYear(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("CardPaymentData", 0);
        return  ShareData.getString("ExpiryYear",null);
    }

    public boolean getCardData(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("CardPaymentData", 0);
        return  ShareData.getBoolean("CardData",false);
    }












    //DEBIT DATA RETRIEVAL

    public String getDebitHolderName(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("DebitPaymentData", 0);
        return  ShareData.getString("DebitHolderName",null);
    }

    public String getDebitNumber(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("DebitPaymentData", 0);
        return  ShareData.getString("DebitNumber",null);
    }

    public String getDebitExpiryMon(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("DebitPaymentData", 0);
        return  ShareData.getString("ExpiryMon",null);
    }

    public int getDebitCardPosition(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("DebitPaymentData", 0);
        return  ShareData.getInt("DebitCardPosition",0);
    }

    public boolean getDebitData(Context context){
        SharedPreferences ShareData = context.getSharedPreferences("DebitPaymentData", 0);
        return  ShareData.getBoolean("DebitData",false);
    }
















}
