package com.omkar.kumbhar.rentaride.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.myhexaville.smartimagepicker.ImagePicker;
import com.omkar.kumbhar.rentaride.Method.UploadImg;
import com.omkar.kumbhar.rentaride.R;

public class LicenseUploadActivity extends AppCompatActivity {

    ImageView imgLicense;
    ImagePicker imagepicker;
    Button btnImage,btnSkip;
    UploadImg uploadImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_upload);
        imgLicense = (ImageView) findViewById(R.id.imgLicense);
        btnImage =(Button) findViewById(R.id.btnImage);
        btnSkip = (Button) findViewById(R.id.btnSkip);

        imgLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker();
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker();
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LicenseUploadActivity.this,HomeActivity.class);
                startActivity(i);
                finish();
            }
        });



    }

    private void ImagePicker() {
        imagepicker = new ImagePicker(this,
                null,
                imageUri -> {
                    imgLicense.setImageURI(imageUri);
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
