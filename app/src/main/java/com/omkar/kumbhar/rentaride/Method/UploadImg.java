package com.omkar.kumbhar.rentaride.Method;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.omkar.kumbhar.rentaride.Activity.UploadVehicleActivity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UploadImg {
    public void UploadCarImage(Uri imageFile, String id, Context applicationContext) {

        FirebaseFirestore db  = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();

        Toast.makeText(applicationContext, "2"+imageFile, Toast.LENGTH_SHORT).show();
        StorageReference imageRef = storage.getReference("/Vehicle/CarImg/"+id+"/"+ imageFile.getLastPathSegment());
        Task<Uri> urlTask = imageRef.putFile(imageFile).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    EditCarDocument(id,task.getResult(),applicationContext);
                }else {
                    Toast.makeText(applicationContext, ""+ task.getException().getCause()+" "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void EditCarDocument(String id, Uri result, Context applicationContext) {
        FirebaseFirestore db  = FirebaseFirestore.getInstance();
        Map<String, Object> carImg= new HashMap<>();
        carImg.put("ImgUrl",result.toString());

        db.collection("Vehicles").document("Car").collection("CarData")
                .document(id).update(carImg).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(applicationContext, "Product Has been uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void UploadBikeImage(Uri imageFile, String id, Context applicationContext) {
        FirebaseFirestore db  = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        //Uri file = Uri.fromFile(new File(String.valueOf(imageFile)));
        StorageReference imageRef = storage.getReference("/Vehicle/BikeImg/"+id+"/"+ imageFile.getLastPathSegment());
        Task<Uri> urlTask = imageRef.putFile(imageFile).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                EditBikeDocument(id,task.getResult(),applicationContext);
            }
        });
    }
    private void EditBikeDocument(String id, Uri result, Context applicationContext) {
        FirebaseFirestore db  = FirebaseFirestore.getInstance();
        Map<String, Object> carImg= new HashMap<>();
        carImg.put("ImgUrl",result.toString());

        db.collection("Vehicles").document("Bike").collection("BikeData")
                .document(id).update(carImg).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(applicationContext, "Product Has been uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void UploadBusImage(Uri imageFile, String id, Context applicationContext) {
        FirebaseFirestore db  = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Uri file = Uri.fromFile(new File(String.valueOf(imageFile)));
        StorageReference imageRef = storage.getReference("/Vehicle/BusImg/"+id+"/"+ imageFile.getLastPathSegment());
        Task<Uri> urlTask = imageRef.putFile(imageFile).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                EditBusDocument(id,task.getResult(),applicationContext);
            }
        });
    }
    private void EditBusDocument(String id, Uri result, Context applicationContext) {
        FirebaseFirestore db  = FirebaseFirestore.getInstance();
        Map<String, Object> carImg= new HashMap<>();
        carImg.put("ImgUrl",result.toString());
        db.collection("Vehicles").document("Bus").collection("BusData")
                .document(id).update(carImg).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(applicationContext, "Product Has been uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
