package com.omkar.kumbhar.rentaride.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.omkar.kumbhar.rentaride.Method.SearchData;
import com.omkar.kumbhar.rentaride.Method.UserData;
import com.omkar.kumbhar.rentaride.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderReport extends AppCompatActivity implements OnMapReadyCallback {
    FirebaseFirestore db;
    TextView ReportCarName, ReportSeater, ReportPrice, ReportStartTime, ReportEndTime, ReportPayableAmount;
    ImageView ReportImage;
    SearchData searchData;
    GoogleMap mMap;
    Button ReportBtnPay;
    String price,Vehicle,DocId, Rent ;
    CollectionReference cRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_report);
        setTitle("Upload Vehicles");

        db = FirebaseFirestore.getInstance();
        searchData = new SearchData();

        Intent getIntent = getIntent();
        Vehicle = getIntent.getStringExtra("Vehicle");
        DocId = getIntent.getStringExtra("DocId");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        ReportImage = (ImageView) findViewById(R.id.ReportImage);
        ReportCarName = (TextView) findViewById(R.id.ReportCarName);
        ReportSeater = (TextView) findViewById(R.id.ReportSeater);
        ReportPrice = (TextView) findViewById(R.id.ReportPrice);
        ReportStartTime = (TextView) findViewById(R.id.ReportStartTime);
        ReportEndTime = (TextView) findViewById(R.id.ReportEndTime);
        ReportPayableAmount = (TextView) findViewById(R.id.ReportPayableAmount);
        ReportBtnPay = (Button) findViewById(R.id.ReportBtnPay);


        ReportBtnPay.setOnClickListener(v -> {
            UserData userData = new UserData();
            boolean status= userData.getLoginStatus(OrderReport.this);
            boolean userVerify = userData.getEmailVerified(getApplicationContext());
            if(!status){
                Toast.makeText(this, "User is Not logged in", Toast.LENGTH_SHORT).show();
                openLoginActivity();
            }else if(!userVerify){
                Toast.makeText(this, "Please verify user email address", Toast.LENGTH_SHORT).show();
            }else {
                OpenPaymentActivity();
            }

        });


        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm");
        String StartTime = searchData.getStartDate(this).replace("/", "-") + " " + searchData.getStartTime(this);
        String EndTime = searchData.getEndDate(this).replace("/", "-") + " " + searchData.getEndTime(this);
        try {
            Date Start = sdf.parse(StartTime);
            ReportStartTime.setText(Start.toString());
            Date End = sdf.parse(EndTime);
            ReportEndTime.setText(End.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        switch (Vehicle) {
            case "Car":
                cRef = db.collection("Vehicles").document("Car").collection("CarData");
                break;
            case "Bike":
                cRef = db.collection("Vehicles").document("Bike").collection("BikeData");
                break;
            case "Bus":
                cRef = db.collection("Vehicles").document("Bus").collection("BusData");
                break;
        }


        cRef.document(DocId).get().addOnCompleteListener(task -> {


            if (task.isSuccessful()) {

                searchData.AddSelectedRef(DocId,Vehicle,task.getResult().get("Rent").toString(),getApplicationContext());

              //  Toast.makeText(this, DocId+ ""+Vehicle, Toast.LENGTH_SHORT).show();

                if(task.getResult().contains("Rent")){
                    Rent = task.getResult().get("Rent").toString();
                    price = task.getResult().get("Rent").toString()+"/-";
                }


                String CarName = task.getResult().get("CarName").toString();
                String seater ="Seater "+ task.getResult().get("Seater").toString();
                if (task.getResult().get("ImgUrl") != null) {
                    Glide.with(OrderReport.this).load(task.getResult().get("ImgUrl")).into(ReportImage);
                }

                ReportSeater.setText(seater);
                ReportCarName.setText(CarName);
                ReportPrice.setText(price);
                ReportPayableAmount.setText(ReportPayableAmount.getText().toString()+ price);
            }
        });

    }

    private void openLoginActivity() {
        Intent intent = new Intent(OrderReport.this,LoginActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Apply activity transition
            startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            // Swap without transition
            startActivity(intent);
        }
    }

    private void OpenPaymentActivity() {
        Intent intent = new Intent(OrderReport.this,PaymentActivity.class);
        intent.putExtra("Vehicle",Vehicle);
        intent.putExtra("Rent",Rent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Apply activity transition
            startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            // Swap without transition
            startActivity(intent);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng carPosition = new LatLng(18.966351, 72.821507);
        mMap.addMarker(new MarkerOptions().position(carPosition).title("Car Position"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(carPosition, 12.0f));

    }

}
