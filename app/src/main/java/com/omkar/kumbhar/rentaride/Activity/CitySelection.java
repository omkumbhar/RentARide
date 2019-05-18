package com.omkar.kumbhar.rentaride.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.omkar.kumbhar.rentaride.Adapter.CityAdapter;
import com.omkar.kumbhar.rentaride.Method.UserData;
import com.omkar.kumbhar.rentaride.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CitySelection extends AppCompatActivity {
    double longitude;
    double latitude;
    private FusedLocationProviderClient mFusedLocationClient;
    Button skipBtn;
    ImageView btnLocation;
    List<String> citie;
    AutoCompleteTextView edtCity;
    RecyclerView recyCitie;
    String[] cities = { "Mumbai,Maharastra","Nagapur,Maharastra","Satara,Maharastra","Belgaon,Maharastra",
            "Rayagada,Maharastra","Andheri,Maharastra","Dadar,Maharastra","Pune,Maharastra","Punji,Goa" };

     RecyclerView mRecyclerView;

    CityAdapter listAdapter;
    UserData userData;
    Adapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);
        setTitle("City Selection");
        userData = new UserData();

        Toast.makeText(this, ""+ userData.getUserName(this), Toast.LENGTH_SHORT).show();

        skipBtn = (Button) findViewById(R.id.skipBtn);

        edtCity =(AutoCompleteTextView) findViewById(R.id.edtCity);
        btnLocation = (ImageView) findViewById(R.id.btnLocation);

        //For AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, cities);
        edtCity.setThreshold(1);
        edtCity.setAdapter(adapter);

        //for List View
        citie  = new ArrayList<String>();
        citie.add("Mumbai");
        citie.add("Satara");
        citie.add("Pune");
        citie.add("Panvel");
        citie.add("Bhivandi");
        citie.add("jalagaon");
        citie.add("Thane");
        citie.add("Nagpur");
        citie.add("Kohlapur");
        citie.add("Nashik");
        citie.add("Amaravati");
        citie.add("Solapaur");
        citie.add("Navi Mumbai");
        recyCitie = (RecyclerView) findViewById(R.id.recyCitie);
        listAdapter = new CityAdapter(citie);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyCitie.setLayoutManager(mLayoutManager);
        recyCitie.setAdapter(listAdapter);



        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocation();
            }
        });


        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







                Intent i = new Intent(CitySelection.this,LicenseUploadActivity.class);
                startActivity(i);
                finish();
            }
        });









        edtCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
                skipBtn.setText("Next");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });







    }

    private void setLocation() {
        if (ActivityCompat.checkSelfPermission(CitySelection.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CitySelection.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(CitySelection.this,
                    new String[]{ Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},888);
        }

        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Toast.makeText(CitySelection.this, "" + location, Toast.LENGTH_SHORT).show();
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    String address = getAddress(longitude, latitude);
                    Toast.makeText(getApplicationContext(), address, Toast.LENGTH_LONG).show();
                    edtCity.setText(address);
                }
            }
        });
    }

    private String getAddress(double longitude, double latitude) {
        Geocoder geocoder;
        List<Address> addresses = new ArrayList<>();
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        return city;
    }





}
