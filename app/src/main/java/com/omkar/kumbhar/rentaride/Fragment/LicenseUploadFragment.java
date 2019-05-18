package com.omkar.kumbhar.rentaride.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.myhexaville.smartimagepicker.ImagePicker;
import com.omkar.kumbhar.rentaride.Activity.HomeActivity;
import com.omkar.kumbhar.rentaride.Method.UploadImg;
import com.omkar.kumbhar.rentaride.Method.UserData;
import com.omkar.kumbhar.rentaride.R;


import java.util.HashMap;
import java.util.Map;


public class LicenseUploadFragment extends Fragment {

    ImagePicker imagepicker;
    ImageView imgLicense;
    Button btnUpload;
    Uri imageUri;
    UploadImg uploadImg;
    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseFirestore db;
    UserData userData;
    public LicenseUploadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_license_upload, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Upload License");
        userData = new UserData();
        db    = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        uploadImg = new UploadImg();
        Button car = (Button) view.findViewById(R.id.btnImage);
        imgLicense = (ImageView) view.findViewById(R.id.imgLicense);

        btnUpload = (Button) view.findViewById(R.id.btnUpload);

        btnUpload.setVisibility(View.GONE);




        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(), ""+imageUri, Toast.LENGTH_SHORT).show();
                UploadLicense(imageUri);
            }
        });








    }

    private void UploadLicense(Uri imageUri) {
        //imgLicense.setImageResource(0);
        StorageReference ref = storageReference.child("/Users/Licesne");

        Task<Uri> urlTask =  ref.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                //url.setText(ref.getDownloadUrl().toString()+" om     ");
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){


                   // Toast.makeText(getContext(), ""+task.getResult(), Toast.LENGTH_SHORT).show();

                    AddRef(task.getResult());




                }
            }
        });
    }

    private void AddRef(Uri result) {
        Map<String, Object> url= new HashMap<>();
        url.put("LicenseUrl",result.toString());
        url.put("Verified",false);
        db.collection("Users").document(userData.getUserUid(getContext())).update(url)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "License has been Uploaded", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void ImagePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 99);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //imagepicker.handleActivityResult(resultCode,requestCode,data);
        if (requestCode == 99 ) {
            if (data != null) {
                // this is the image selected by the user
                imageUri = data.getData();
                if(imageUri != null) {
                    Glide.with(getContext()).load(imageUri).into(imgLicense);
                }
            }
        }





    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagepicker.handlePermission(requestCode,grantResults);
    }

    @Override
    public void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (imgLicense.getDrawable() == null){
                    //Toast.makeText(getActivity(), "Gone", Toast.LENGTH_SHORT).show();
                    //Image doesn´t exist.
                    btnUpload.setVisibility(View.GONE);
                }else{
                    //Toast.makeText(getActivity(), "Visible", Toast.LENGTH_SHORT).show();
                    //Image Exists!.
                    btnUpload.setVisibility(View.VISIBLE);
                }
            }
        }, 500);

    }
}
