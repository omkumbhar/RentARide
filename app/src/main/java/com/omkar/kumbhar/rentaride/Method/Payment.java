package com.omkar.kumbhar.rentaride.Method;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.omkar.kumbhar.rentaride.Activity.HomeActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

public class Payment {

    public boolean CardPayment(String cardHolderName, String cardNumber, String cardExpiryMon, String cardExpiryYear, String rent, Context applicationContext) {
        final boolean[] result = new boolean[1];
        CollectionReference cRef;
        FirebaseFirestore db =FirebaseFirestore.getInstance();
        cRef = db.collection("Bank").document("CreditCard").collection("CreditUsers");
        Query query = cRef.whereEqualTo("CreditCardNumber",cardNumber);




        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().isEmpty()){

                        Toasty.info(applicationContext
                                , "No records found in the databse  please make payment with another card",
                                Toast.LENGTH_SHORT, true).show();
                        return;
                    }
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        IsValid(document.get("CardHolderName"),document.get("CardExpiryMon"),document.get("CardExpiryYear"),document.get("Amount").toString(),document.getId(),applicationContext);
                    }
                }else {
                    Toast.makeText(applicationContext, "No database Found", Toast.LENGTH_SHORT).show();
                }
            }




            private void IsValid(Object cHolderName, Object cExpiryMon, Object cExpiryYear, String amount, String id, Context applicationContext) {

                 /*if(!cHolderName.toString().equals(cardHolderName)){
                    Toast.makeText(applicationContext, cHolderName.toString()+" = "+cardHolderName, Toast.LENGTH_SHORT).show();
                }
                    if(!cExpiryMon.toString().equals(cardExpiryMon)){
                    Toast.makeText(applicationContext, cExpiryMon.toString().toString()+" = "+cardExpiryMon, Toast.LENGTH_SHORT).show();
                }
                if(!cExpiryYear.toString().equals(cardExpiryYear)){
                    Toast.makeText(applicationContext, cExpiryYear.toString()+" = "+cardExpiryYear, Toast.LENGTH_SHORT).show();
                }
                if(cHolderName.toString().equals(cardHolderName)&& cExpiryMon.toString().equals(cardExpiryMon)&& cExpiryYear.toString().equals(cardExpiryYear)  ){
                    PayTheRent(id,amount);
                }
                else {
                    Toast.makeText(applicationContext, "Not Mached", Toast.LENGTH_SHORT).show();
                }*/
            }



            private void PayTheRent(String id, String  amount) {
                try {
                    int cost = Integer.parseInt(rent);
                    int amt = Integer.parseInt(amount);
                    if(amt < cost){
                        Toast toast = Toast.makeText(applicationContext, "You Dont Have Enogh Balanace to pay rent please make payment with another card", Toast.LENGTH_LONG);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            toast.getView()
                                    .setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            toast.show();
                        }else {
                            toast.getView().setBackgroundColor(Color.RED);
                            toast.show();
                        }
                        return;
                    }
                    int updatedAmt=   amt - cost ;
                    String Amt = String.valueOf(updatedAmt);
                    Map<String, Object> Amount = new HashMap<>();
                    Amount.put("Amount",Amt);
                    cRef.document(id).update(Amount).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toasty.success(applicationContext, "Paymet Is Successful", Toast.LENGTH_SHORT, true).show();
                           NextIntent(applicationContext);
                            AddOrderRef addOrder = new AddOrderRef();
                            addOrder.AddRefernce(applicationContext);


                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(applicationContext, ""+e.hashCode()+"  "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return result[0];
    }






    public boolean DebitPayment(String debitHolderName, String debitNumber, String debitCvv, String debitExpiryMon, String debitExpiryYear, String debitCardType, String rent, Context applicationContext) {
        CollectionReference cRef;
        FirebaseFirestore db =FirebaseFirestore.getInstance();
        cRef = db.collection("Bank").document("DebitCard").collection("DebitUsers");
        Query query = cRef.whereEqualTo("DebitCardNumber",debitNumber);


        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().isEmpty()){
                        Toasty.info(applicationContext, "No records found in the databse  please make payment with another debit card",
                                Toast.LENGTH_SHORT, true).show();
                        return;
                    }
                    for (QueryDocumentSnapshot document : task.getResult()) {
                       // Toast.makeText(applicationContext, "Data Found", Toast.LENGTH_SHORT).show();
                        IsDebitValid(document.get("DebitHolderName"),document.get("DebitExpiryMonth"),document.get("DebitExpiryYear"),document.get("Amount").toString(),document.get("DebitCVV").toString(),document.get("DebitType"),document.getId(),applicationContext);
                    }
                }
            }
            private void IsDebitValid(Object dHolderName, Object dExpiryMonth, Object dExpiryYear, String amount, String dCVV, Object dType, String id, Context applicationContext) {
                if(dHolderName.toString().equals(debitHolderName) && dExpiryMonth.toString().equals(debitExpiryMon) && dExpiryYear.toString().equals(debitExpiryYear) && dCVV.equals(debitCvv) && dType.toString().equals(debitCardType)){
                    PayTheRent(id,amount);
                }
            }






            private void PayTheRent(String id, String amount) {
                int cost = Integer.parseInt(rent);
                int amt = Integer.parseInt(amount);
                if(amt < cost){
                    Toast toast = Toast.makeText(applicationContext, "You Dont Have Enogh Balanace to pay rent please make payment with another card", Toast.LENGTH_LONG);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        toast.getView().setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        toast.show();
                        return;
                    }
                }
                int updatedAmt =   amt - cost ;
                String Amt = String.valueOf(updatedAmt);
                Map<String, Object> Amount = new HashMap<>();
                Amount.put("Amount",Amt);
                cRef.document(id).update(Amount).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toasty.success(applicationContext, "Paymet Is Successful", Toast.LENGTH_SHORT, true).show();
                        NextIntent(applicationContext);

                    }
                });
            }
        });
        return  true;
    }
    private void NextIntent(Context applicationContext) {
        Intent i = new Intent(applicationContext,HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        applicationContext.startActivity(i);
    }

}
