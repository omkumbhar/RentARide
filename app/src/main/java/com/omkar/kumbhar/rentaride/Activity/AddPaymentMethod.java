package com.omkar.kumbhar.rentaride.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.omkar.kumbhar.rentaride.Method.PaymentData;
import com.omkar.kumbhar.rentaride.Method.Validation;
import com.omkar.kumbhar.rentaride.R;

public class AddPaymentMethod extends AppCompatActivity {
    RadioButton CardPayment,DebitPayment;
    EditText CardHolderName,CardNumber,DebitHolderName,DebitNumber;
    Spinner CardExpiryMon,CardExpiryYear,DebitExpiryMon,DebitExpiryYear,DebitCardType;
    TextView PayPrice;
    Button ReportBtnPay;
    PaymentData paymentdata;
    String cardHolderName,cardNumber,cardExpiryMon,cardExpiryYear;
    String debitHolderName,debitNumber,debitExpiryMon,debitExpiryYear,debitCardType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment_method);
        setTitle("Add Payment");
        AddReferences();
        paymentdata = new PaymentData();
        VisibilityChange(0);

        if(paymentdata.getCardData(getApplicationContext())){
           // Toast.makeText(this, "1" +paymentdata.getDebitData(getApplicationContext()) , Toast.LENGTH_SHORT).show();
            VisibilityChange(1);
            setCardData();
        }else if(paymentdata.getDebitData(getApplicationContext())){
            VisibilityChange(2);
            setDebitCard();
            //Toast.makeText(this, "2"+ paymentdata.getCardData(getApplicationContext()), Toast.LENGTH_SHORT).show();
        }

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


        ReportBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CardPayment.isChecked()){
                    boolean valid =  cardValidation();
                    cardHolderName = CardHolderName.getText().toString().trim();
                    cardNumber      = CardNumber.getText().toString().trim();
                    cardExpiryMon = String.valueOf(CardExpiryMon.getSelectedItemPosition());
                    cardExpiryYear = String.valueOf(CardExpiryYear.getSelectedItemPosition());
                    if (valid){
                        CardHolderName.setFocusable(false);
                      boolean  result =  paymentdata.AddCarData(cardHolderName ,cardNumber,cardExpiryMon,cardExpiryYear,getApplicationContext());
                        if (result){
                            Toast.makeText(AddPaymentMethod.this, "Card Data Insert", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(AddPaymentMethod.this,HomeActivity.class);
                            startActivity(i);
                        }
                    }
                }else if(DebitPayment.isChecked()){
                    boolean valid =  DebitValidation();
                    debitHolderName = DebitHolderName.getText().toString().trim();
                    debitNumber =     DebitNumber.getText().toString().trim();
                    debitExpiryMon = String.valueOf(DebitExpiryMon.getSelectedItemPosition());
                    debitExpiryYear = String.valueOf(DebitExpiryYear.getSelectedItemPosition());
                    debitCardType = DebitCardType.getSelectedItem().toString();
                    int debitCardPos = DebitCardType.getSelectedItemPosition();
                    if(valid){
                       boolean result = paymentdata.AddDebData(debitHolderName, debitNumber,debitExpiryMon,debitExpiryYear,debitCardPos,getApplicationContext());
                       if(result){
                           Toast.makeText(AddPaymentMethod.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                           Intent i = new Intent(AddPaymentMethod.this,HomeActivity.class);
                           startActivity(i);
                       }
                    }
                }
            }
        });
    }

    private void setDebitCard() {
        DebitPayment.setChecked(true);
        DebitHolderName.setText(paymentdata.getDebitHolderName(getApplicationContext()));
        DebitNumber.setText(paymentdata.getDebitNumber(getApplicationContext()));
        int expiryMon = Integer.parseInt(paymentdata.getDebitExpiryMon(getApplicationContext()));
        DebitExpiryMon.setSelection(expiryMon);
        int expiryYear = Integer.parseInt( paymentdata.getCardExpiryYear(getApplicationContext()));
        DebitExpiryYear.setSelection(expiryYear);
    }

    private void setCardData() {
        CardPayment.setChecked(true);
        CardHolderName.setText(paymentdata.getCardHolderName(getApplicationContext()));
        CardNumber.setText(paymentdata.getCardNumber(getApplicationContext()));
        int expiryMon =  Integer.parseInt( paymentdata.getCardExpiryMon(getApplicationContext()));
        CardExpiryMon.setSelection(expiryMon);
        int expiryYear = Integer.parseInt( paymentdata.getCardExpiryYear(getApplicationContext()));
        CardExpiryYear.setSelection(expiryYear);
    }

    private void AddReferences() {
        PayPrice = (TextView) findViewById(R.id.PayPrice);

        CardPayment  = (RadioButton) findViewById(R.id.CardPayment);
        DebitPayment = (RadioButton) findViewById(R.id.DebitPayment);

        CardHolderName = (EditText) findViewById(R.id.CardHolderName);
        CardNumber   = (EditText) findViewById(R.id.CardNumber);
        DebitHolderName = (EditText) findViewById(R.id.DebitHolderName);
        DebitNumber  = (EditText) findViewById(R.id.DebitNumber);


        CardExpiryMon = (Spinner) findViewById(R.id.CardExpiryMon);
        CardExpiryYear = (Spinner) findViewById(R.id.CardExpiryYear);
        DebitExpiryMon = (Spinner) findViewById(R.id.DebitExpiryMon);
        DebitExpiryYear = (Spinner) findViewById(R.id.DebitExpiryYear);
        DebitCardType = (Spinner) findViewById(R.id.DebitCardType);
        ReportBtnPay =(Button) findViewById(R.id.ReportBtnPay);
        CardPayment.setGravity(Gravity.CENTER);
        DebitPayment.setGravity(Gravity.CENTER);
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
                DebitCardType.setVisibility(View.VISIBLE);

                break;
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.finish();
        overridePendingTransition  (R.transition.animation, R.transition.animation2);
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
        boolean spinMon = validation.SingleSpiVal(DebitExpiryMon,"Please select Expiry month of Debit Card",getApplicationContext());
        boolean spinYear = validation.SingleSpiVal(DebitExpiryYear,"Please select Expiry month of Debit Card",getApplicationContext());
        boolean depType = validation.SingleSpiVal(DebitCardType,"Please select Expiry month of Debit  Card",getApplicationContext());
        return (name && number && spinMon && spinYear && depType);
    }

}
