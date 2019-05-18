package com.omkar.kumbhar.rentaride.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.omkar.kumbhar.rentaride.Method.SearchData;
import com.omkar.kumbhar.rentaride.Method.Validation;
import com.omkar.kumbhar.rentaride.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FilterActivity extends AppCompatActivity {
    private FusedLocationProviderClient mFusedLocationClient;
    double longitude;
    double latitude;
    EditText Source,StartingTime,StartingDate,EndingTime,EndingDate;
    ImageButton btnSource;
    Button btnSearch;
    Spinner spiSeater;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    SearchData searchData;
    final Calendar c = Calendar.getInstance();
    int mHour,mMinute,month,day,year;
    String Vehicle;
    Validation validation;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        setTitle("Filter");

        Source = (EditText) findViewById(R.id.edtSource);
        StartingTime = (EditText) findViewById(R.id.StartingTime);
        StartingDate =(EditText) findViewById(R.id.StartingDate);
        EndingTime = (EditText) findViewById(R.id.EndingTime);
        EndingDate = (EditText) findViewById(R.id.EndingDate);
        spiSeater = (Spinner) findViewById(R.id.spiSeater);
        btnSearch =(Button) findViewById(R.id.btnSearch);
        searchData = new SearchData();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        year = c.get(Calendar.YEAR);

        validation = new Validation();

        Intent intent = getIntent();
        Vehicle = intent.getStringExtra("Vehicle");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StartingDate.setShowSoftInputOnFocus(false);
            EndingDate.setShowSoftInputOnFocus(false);
        }



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetParameters();
            }
        });
        EndingDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    setDate(year,month,day,"Select Date When journey End",EndingDate);
                }
                return false;
            }
        });
        EndingTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    setTime(mHour,mMinute,"Select When journey End",EndingTime);
                }
                return false;
            }
        });
        StartingDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    setDate(year,month,day,"Select Date When journey Start",StartingDate);
                }
                return false;
            }
        });
        StartingTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    setTime(mHour,mMinute,"Select When journey Start",StartingTime);
                }
                return false;
            }
        });
        btnSource = (ImageButton) findViewById(R.id.btnSource);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        btnSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocation();
            }
        });
    }


    private void SetParameters() {

        boolean isValid = validation.FilterValidation(Source,StartingTime, StartingDate,EndingTime,EndingDate,getApplicationContext());

        //  Toast.makeText(this, ""+isValid, Toast.LENGTH_SHORT).show();

        if(isValid){
            String source = Source.getText().toString().trim();
            String  startTime = StartingTime.getText().toString().trim();
            String  startDate = StartingDate.getText().toString().trim();
            String endTime = EndingTime.getText().toString().trim();
            String endDate = EndingDate.getText().toString().trim();
            String seater = spiSeater.getSelectedItem().toString();
            boolean result = searchData.AddSearchData(source,startTime,startDate,endTime,endDate,seater,getApplicationContext());

            if(result){
                Intent i = new Intent(FilterActivity.this,ResultActivity.class);

                i.putExtra("Vehicle",Vehicle);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Apply activity transition
                    startActivity(i,ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                } else {
                    // Swap without transition
                    startActivity(i);
                }

            }


        }


    }




    private void setDate(int year, int month, int day, String title, EditText edtDate) {
        datePickerDialog =new DatePickerDialog(FilterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edtDate.setError(null);
                month = month + 1;
                String date = dayOfMonth+"/"+month +"/"+year;
                edtDate.setText(date);
            }
        },year,month,day);
        datePickerDialog.setTitle(title);
        datePickerDialog.show();
    }

    private void setTime(int mHour, int mMinute, String title, EditText edtTime) {
        timePickerDialog = new TimePickerDialog(FilterActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                edtTime.setError(null);
                edtTime.setText(setStartTime(hourOfDay,minute));
            }
        }, mHour, mMinute, false);
        timePickerDialog.setTitle(title);
        timePickerDialog.show();
    }
    private String setStartTime(int hourOfDay, int minute) {
        int hour = hourOfDay;
        int minutes = minute;
        String timeSet = "";
        if (hour > 12) {
            hour -= 12;
            timeSet = "PM";
        } else if (hour == 0) {
            hour += 12;
            timeSet = "AM";
        } else if (hour == 12){
            timeSet = "PM";
        }else{
            timeSet = "AM";
        }

        String min = "";
        if (minutes < 10)
            min = "0" + minutes ;
        else
            min = String.valueOf(minutes);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hour).append(':')
                .append(min ).append(" ").append(timeSet).toString();
        return aTime;
    }
    private void setLocation() {
        if (ActivityCompat.checkSelfPermission(FilterActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(FilterActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(FilterActivity.this,
                    new String[]{ Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},888);
        }

        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Toast.makeText(FilterActivity.this, "" + location, Toast.LENGTH_SHORT).show();
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    String address = getAddress(longitude, latitude);
                    // Toast.makeText(getApplicationContext(), address, Toast.LENGTH_LONG).show();
                    Source.setError(null);
                    Source.setText(address);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 888 ){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                setLocation();
            }else {
                btnSource.setOnClickListener(null);
            }
        }
    }
}
