package com.omkar.kumbhar.rentaride.Activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.myhexaville.smartimagepicker.ImagePicker;
import com.omkar.kumbhar.rentaride.Method.DucumentRef;
import com.omkar.kumbhar.rentaride.Method.UploadImg;
import com.omkar.kumbhar.rentaride.Method.UserData;
import com.omkar.kumbhar.rentaride.Method.Validation;
import com.omkar.kumbhar.rentaride.R;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class UploadVehicleActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    ImageView vehicleImg,btnLocation;
    TextView ChooseImg;
    EditText vehicleName,vehicleLocation,vehicleRent;
    Spinner vehicleSeater,vehicleFuel,vehicleType;
    Button btnUpload;
    ImagePicker imagepicker;
    Validation valid;
    Uri imgFile;
    DucumentRef DocRef;
    UploadImg uploadImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_vehicle);
        setTitle("Publish Vehicle");
        db = FirebaseFirestore.getInstance();

        DocRef = new DucumentRef();
        uploadImg = new UploadImg();
        valid = new Validation();

        vehicleImg = (ImageView) findViewById(R.id.vehicleImg);
        btnLocation = (ImageView) findViewById(R.id.btnLocation);
        ChooseImg = (TextView) findViewById(R.id.ChooseImg);
        vehicleName= (EditText) findViewById(R.id.vehicleName);
        vehicleLocation= (EditText) findViewById(R.id.vehicleLocation);
        vehicleRent= (EditText) findViewById(R.id.vehicleRent);
        vehicleSeater= (Spinner) findViewById(R.id.vehicleSeater);
        vehicleFuel= (Spinner) findViewById(R.id.vehicleFuel);
        vehicleType= (Spinner) findViewById(R.id.vehicleType);
        btnUpload = (Button) findViewById(R.id. btnUpload);


        ChooseImg.setOnClickListener(v -> ImagePicker());

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validate = ValidateEditText(vehicleName,vehicleLocation,vehicleRent);
                boolean selected = valid.ValidateSpinner(vehicleSeater,vehicleFuel,vehicleType,getApplicationContext());
                if(validate && selected){
                    UploadVehicle();
                }
            }
        });

    }

    private void UploadVehicle() {
        switch ((int) vehicleType.getSelectedItemId()){
            case 1:{
                Map<String, Object> car= new HashMap<>();
                car.put("CarName",vehicleName.getText().toString().trim());
                car.put("Location", vehicleLocation.getText().toString().trim());
                car.put("Seater", vehicleSeater.getSelectedItem().toString());
                car.put("Rent",vehicleRent.getText().toString().trim());
                car.put("FuleType", vehicleFuel.getSelectedItem().toString().trim());

                db.collection("Vehicles").document("Car").collection("CarData")
                        .add(car)
                       .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Upload Vehicle
                        uploadImg.UploadCarImage(imgFile ,documentReference.getId(),getApplicationContext());
                        //Add to User DataBase
                        DocRef.AddCarDocumentReference(documentReference.getId(),getApplicationContext());

                    }
                });
                break;
            }
            case 2:{
                Map<String, Object> bike= new HashMap<>();
                bike.put("CarName",vehicleName.getText().toString().trim());
                bike.put("Location", vehicleLocation.getText().toString().trim());
                bike.put("Seater", vehicleSeater.getSelectedItem().toString());
                bike.put("Rent",vehicleRent.getText().toString().trim());
                bike.put("FuleType", vehicleFuel.getSelectedItem().toString().trim());
                db.collection("Vehicles").document("Bike").collection("BikeData")
                        .add(bike)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                uploadImg.UploadBikeImage(imgFile ,documentReference.getId(),getApplicationContext());
                                DocRef.AddBikeDocumentReference(documentReference.getId(),getApplicationContext());
                            }
                        });
                break;
            }
            case 3:{
                Map<String, Object> bus= new HashMap<>();
                bus.put("CarName",vehicleName.getText().toString().trim());
                bus.put("Location", vehicleLocation.getText().toString().trim());
                bus.put("Seater", vehicleSeater.getSelectedItem().toString());
                bus.put("Rent",vehicleRent.getText().toString().trim());
                bus.put("FuleType", vehicleFuel.getSelectedItem().toString().trim());
                db.collection("Vehicles").document("Bus").collection("BusData")
                        .add(bus)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                               // File file = new File(getRealPathFromURI(imgFile));
                                uploadImg.UploadBusImage(imgFile ,documentReference.getId(),getApplicationContext());
                                DocRef.AddBusDocumentReference(documentReference.getId(),getApplicationContext());
                            }
                        });
                break;
            }
        }
    }

    private boolean ValidateEditText(EditText vehicleName, EditText vehicleLocation, EditText vehicleRent) {
        return valid.InputValidation(vehicleName) &&
                valid.InputValidation(vehicleLocation) && valid.InputValidation(vehicleRent);

    }
    private void ImagePicker() {
        imagepicker = new ImagePicker(this,
                null,
                imageUri -> {
                    vehicleImg .setImageURI(imageUri);
                    imgFile = imageUri;
                    //Toast.makeText(this,  getRealPathFromURI(imageUri) +"", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this,  imageUri +"", Toast.LENGTH_SHORT).show();
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
