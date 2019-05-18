package com.omkar.kumbhar.rentaride.Activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.omkar.kumbhar.rentaride.Method.Payment;
import com.omkar.kumbhar.rentaride.Method.Validation;
import com.omkar.kumbhar.rentaride.R;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class PaymentActivity extends AppCompatActivity {
    RadioButton CardPayment,DebitPayment;
    EditText CardHolderName,CardNumber,DebitHolderName,DebitNumber,DebitCvv;
    Spinner CardExpiryMon,CardExpiryYear,DebitExpiryMon,DebitExpiryYear,DebitCardType;
    TextView PayPrice;
    Button ReportBtnPay;
    String rent;
    String Vehicle;
    Payment payment;

    String cardHolderName,cardNumber,cardExpiryMon,cardExpiryYear;

    String debitHolderName,debitNumber,debitCvv,debitExpiryMon,debitExpiryYear,debitCardType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setTitle("Payment");
        Intent intent = getIntent();
        rent = intent.getStringExtra("Rent");
        Vehicle = intent.getStringExtra("Vehicle");

        payment= new Payment();

        PayPrice = (TextView) findViewById(R.id.PayPrice);

        CardPayment  = (RadioButton) findViewById(R.id.CardPayment);
        DebitPayment = (RadioButton) findViewById(R.id.DebitPayment);

        CardHolderName = (EditText) findViewById(R.id.CardHolderName);
        CardNumber   = (EditText) findViewById(R.id.CardNumber);
        DebitHolderName = (EditText) findViewById(R.id.DebitHolderName);
        DebitNumber  = (EditText) findViewById(R.id.DebitNumber);
        DebitCvv     = (EditText) findViewById(R.id.DebitCvv);

        CardExpiryMon = (Spinner) findViewById(R.id.CardExpiryMon);
        CardExpiryYear = (Spinner) findViewById(R.id.CardExpiryYear);
        DebitExpiryMon = (Spinner) findViewById(R.id.DebitExpiryMon);
        DebitExpiryYear = (Spinner) findViewById(R.id.DebitExpiryYear);
        DebitCardType = (Spinner) findViewById(R.id.DebitCardType);

        ReportBtnPay =(Button) findViewById(R.id.ReportBtnPay);

        CardPayment.setGravity(Gravity.CENTER);
        DebitPayment.setGravity(Gravity.CENTER);

        String btnText =   "Pay "+ ReportBtnPay.getText().toString() + rent  ;
        ReportBtnPay.setText(btnText);

        ReportBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CardPayment.isChecked()){
                    boolean valid =  cardValidation();
                    cardHolderName = CardHolderName.getText().toString().trim();
                    cardNumber = CardNumber.getText().toString().trim();
                    cardExpiryMon = String.valueOf(CardExpiryMon.getSelectedItemId());
                    cardExpiryYear = CardExpiryYear.getSelectedItem().toString();
                    if (valid){
                        CardHolderName.setFocusable(false);
                       payment.CardPayment(cardHolderName,cardNumber,cardExpiryMon,cardExpiryYear,rent,getApplicationContext());
                        //Toast.makeText(PaymentActivity.this, "resuluts "+result, Toast.LENGTH_SHORT).show();

                        //Toast.makeText(PaymentActivity.this, ""+Vehicle, Toast.LENGTH_SHORT).show();
                    }
                }else if(DebitPayment.isChecked()){
                    boolean valid =  DebitValidation();
                    debitHolderName = DebitHolderName.getText().toString().trim();
                    debitNumber =     DebitNumber.getText().toString().trim();
                    debitCvv =   DebitCvv.getText().toString().trim();
                    debitExpiryMon = String.valueOf(DebitExpiryMon.getSelectedItemId());
                    debitExpiryYear = String.valueOf(DebitExpiryYear.getSelectedItem());
                    debitCardType = DebitCardType.getSelectedItem().toString();
                    if(valid){
                      payment.DebitPayment(debitHolderName, debitNumber ,debitCvv,debitExpiryMon,debitExpiryYear,debitCardType,rent,getApplicationContext());
                    }

                    //Toast.makeText(PaymentActivity.this, ""+DebitPayment.isChecked(), Toast.LENGTH_SHORT).show();
                }

            }
        });



        String payText = getResources().getString(R.string.ruppee) + rent;

        PayPrice.setText(payText);

        VisibilityChange(0);

        CardPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    int card = 1;
                    VisibilityChange(card);
                }

            }
        });

        DebitPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    int debit = 2;
                    VisibilityChange(debit);
                }
            }
        });

    }



    private void VisibilityChange(int rad) {


        switch (rad){
            case 0:{
                CardExpiryMon.setVisibility(View.GONE);
                CardExpiryYear.setVisibility(View.GONE);
                CardHolderName.setVisibility(View.GONE);
                CardNumber.setVisibility(View.GONE);


                DebitExpiryMon.setVisibility(View.GONE);
                DebitExpiryYear.setVisibility(View.GONE);
                DebitHolderName.setVisibility(View.GONE);
                DebitNumber.setVisibility(View.GONE);
                DebitCvv.setVisibility(View.GONE);
                DebitCardType.setVisibility(View.GONE);


                break;

            }

            case 1:{

                /*Animation();*/
                CardExpiryMon.setVisibility(View.VISIBLE);
                CardExpiryYear.setVisibility(View.VISIBLE);
                CardHolderName.setVisibility(View.VISIBLE);
                CardNumber.setVisibility(View.VISIBLE);

                if(DebitPayment.isChecked()){
                    DebitPayment.setChecked(false);
                    DebitExpiryMon.setVisibility(View.GONE);
                    DebitExpiryYear.setVisibility(View.GONE);
                    DebitHolderName.setVisibility(View.GONE);
                    DebitNumber.setVisibility(View.GONE);
                    DebitCvv.setVisibility(View.GONE);
                    DebitCardType.setVisibility(View.GONE);
                }




                break;
            }
            case 2:{


                if(CardPayment.isChecked()) {
                    CardPayment.setChecked(false);
                    CardExpiryMon.setVisibility(View.GONE);
                    CardExpiryYear.setVisibility(View.GONE);
                    CardHolderName.setVisibility(View.GONE);
                    CardNumber.setVisibility(View.GONE);
                }

                DebitExpiryMon.setVisibility(View.VISIBLE);
                DebitExpiryYear.setVisibility(View.VISIBLE);
                DebitHolderName.setVisibility(View.VISIBLE);
                DebitNumber.setVisibility(View.VISIBLE);
                DebitCvv.setVisibility(View.VISIBLE);
                DebitCardType.setVisibility(View.VISIBLE);

                break;
            }
        }



    }

    private boolean cardValidation() {
        Validation validation = new Validation();
        boolean name= validation.InputValidation(CardHolderName);
        boolean number = validation.InputValidation( CardNumber);
        boolean spinMon = validation.SingleSpiVal(CardExpiryMon,"Please select Expiry month of Card",getApplicationContext());
        boolean spinYear = validation.SingleSpiVal(CardExpiryYear,"Please select Expiry Year of Card",getApplicationContext());
       // Toast.makeText(this, ""+spinMon, Toast.LENGTH_SHORT).show();
        if(name&& number&& spinMon&& spinYear){
            return true;
        }
        return  false;
    }
    private boolean DebitValidation() {
        Validation validation = new Validation();
        boolean name = validation.InputValidation(DebitHolderName);
        boolean number = validation.InputValidation(DebitNumber);
        boolean cvv = validation.InputValidation(DebitCvv);
        boolean spinMon = validation.SingleSpiVal(DebitExpiryMon,"Please select Expiry month of Debit Card",getApplicationContext());
        boolean spinYear = validation.SingleSpiVal(DebitExpiryYear,"Please select Expiry month of Debit Card",getApplicationContext());
        boolean depType = validation.SingleSpiVal(DebitCardType,"Please select Expiry month of Debit  Card",getApplicationContext());
        return (name && number&& cvv && spinMon && spinYear && depType);
    }

}
