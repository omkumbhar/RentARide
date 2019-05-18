package com.omkar.kumbhar.rentaride.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myhexaville.smartimagepicker.ImagePicker;
import com.omkar.kumbhar.rentaride.Method.AdUser;
import com.omkar.kumbhar.rentaride.Method.UserData;
import com.omkar.kumbhar.rentaride.R;

public class ProfileActivity extends AppCompatActivity {
    ImagePicker imagepicker;
    ImageView ProfileImg;
    AdUser ImageData;
    UserData userData;
    EditText UserName,UserEmail,UserNumber;
    Button paymentMethod;
    TextView ChangePic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");

        ImageData = new AdUser();
        ChangePic = findViewById(R.id.ChangePic);
        ProfileImg = (ImageView) findViewById(R.id.imgLicense);
        UserName = (EditText) findViewById(R.id.UserName);
        UserEmail = (EditText) findViewById(R.id.UserEmail);
        UserNumber = (EditText) findViewById(R.id.UserNumber);
        paymentMethod= (Button) findViewById(R.id.paymentMethod);
        userData = new UserData();

        if(  ImageData.isImageInserted(getApplicationContext())){
            Glide.with(getApplicationContext()).load(ImageData.getUserImageUrl(getApplicationContext())).into(ProfileImg);
        }




        UserName.setText(userData.getUserName(getApplicationContext()));
        UserEmail.setText(userData.getEmail(getApplicationContext()));



        ChangePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   PhotoPicke();
            }
        });

        paymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent slideactivity = new Intent(ProfileActivity.this, AddPaymentMethod.class);
                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.transition.animation,R.transition.animation2).toBundle();
                startActivity(slideactivity, bndlanimation);
            }
        });




    }

    private void PhotoPicke() {
        imagepicker = new ImagePicker(this,
                null,
                imageUri -> {
                    ProfileImg.setImageURI(imageUri);
                    ImageData.AddImageData(imageUri.toString(),getApplicationContext());
                }
        );
        imagepicker.choosePicture(true);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagepicker.handleActivityResult(resultCode,requestCode,data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagepicker.handlePermission(requestCode,grantResults);
    }

}
