package com.omkar.kumbhar.rentaride.Method;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DucumentRef {
    public void AddCarDocumentReference(String id, Context context) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> DocData= new HashMap<>();
        UserData userData = new UserData();
        DocumentReference DocRef = db.collection("Users").document(userData.getUserUid(context));
        db.collection("Users").document(userData.getUserUid(context)).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            if(task.getResult().contains("UploadedVehicle")){
                                DocumentSnapshot document = task.getResult();

                                Map<String, Object> group= new HashMap<String, Object>((Map<? extends String, ?>) task.getResult().get("UploadedVehicle"));
                                group.put(radString(),db.collection("Vehicles").document("/Car/CarData/"+id));

                                DocData.put("UploadedVehicle",group );
                                DocRef.update(DocData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        //Toast.makeText(context, "Reference has been set", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                //Map<String, DocumentReference> ArrayData= new HashMap<>();
                                Map<String, Object> arrayData= new HashMap<String, Object>();
                                arrayData.put(radString(),db.collection("Vehicles").document("/Car/CarData/"+id));
                                DocData.put("UploadedVehicle", arrayData);
                                DocRef.update(DocData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        //Toast.makeText(context, "New Reference is created", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    }
                });
    }

    public void AddBikeDocumentReference(String id, Context context) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> DocData= new HashMap<>();
        UserData userData = new UserData();
        DocumentReference DocRef = db.collection("Users").document(userData.getUserUid(context));

        db.collection("Users").document(userData.getUserUid(context)).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            if(task.getResult().contains("UploadedVehicle")){
                                DocumentSnapshot document = task.getResult();

                                Map<String, Object> group= new HashMap<String, Object>((Map<? extends String, ?>) task.getResult().get("UploadedVehicle"));
                                group.put(radString(),db.collection("Vehicles").document("/Bike/BikeData/"+id));

                                DocData.put("UploadedVehicle",group );
                                DocRef.update(DocData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        //Toast.makeText(context, "Reference has been set", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                Map<String, Object> arrayData= new HashMap<String, Object>();
                                arrayData.put(radString(),db.collection("Vehicles").document("/Bike/BikeData/"+id));
                                DocData.put("UploadedVehicle", arrayData);
                                DocRef.update(DocData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // Toast.makeText(context, "New Reference is created", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    }
                });
    }

    public void AddBusDocumentReference(String id, Context context) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> DocData= new HashMap<>();
        UserData userData = new UserData();
        DocumentReference DocRef = db.collection("Users").document(userData.getUserUid(context));

        db.collection("Users").document(userData.getUserUid(context)).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            if(task.getResult().contains("UploadedVehicle")){
                                DocumentSnapshot document = task.getResult();

                                Map<String, Object> group= new HashMap<String, Object>((Map<? extends String, ?>) task.getResult().get("UploadedVehicle"));
                                group.put(radString(),db.collection("Vehicles").document("/Bus/BusData/"+id));

                                DocData.put("UploadedVehicle",group );
                                DocRef.update(DocData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // Toast.makeText(context, "Reference has been set", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                //Map<String, DocumentReference> ArrayData= new HashMap<>();
                                Map<String, Object> arrayData= new HashMap<String, Object>();
                                arrayData.put(radString(),db.collection("Vehicles").document("/Bus/BusData/"+id));
                                DocData.put("UploadedVehicle", arrayData);
                                DocRef.update(DocData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // Toast.makeText(context, "New Reference is created", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    }
                });

    }

    private String radString() {
        byte[] array = new byte[5]; // length is bounded by 5
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }



}
