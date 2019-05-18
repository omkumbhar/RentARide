package com.omkar.kumbhar.rentaride.Method;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AddOrderRef {
    public void AddRefernce(Context context){
        //Toast.makeText(context, "Add REferences has started", Toast.LENGTH_SHORT).show();
        FirebaseFirestore db  = FirebaseFirestore.getInstance();
        DocumentReference dRef = null;
        Map<String, Object> DocData= new HashMap<>();
        SearchData searchData = new SearchData();
        String Vehicle = searchData.getVehicle(context);
        String ID = searchData.getDocID(context);
        UserData userData = new UserData();
        switch (Vehicle) {
            case "Car":
                dRef = db.collection("Vehicles").document("Car").collection("CarData").document(ID);
                break;
            case "Bike":
                dRef = db.collection("Vehicles").document("Bike").collection("BikeData").document(ID);
                break;
            case "Bus":
                dRef = db.collection("Vehicles").document("Bus").collection("BusData").document(ID);
                break;
        }
        DocData.put("VehicleRef", dRef );
        DocData.put("UserOrderId",userData.getUserUid(context));
        DocData.put("FromTime", searchData.getStartDate(context)+searchData.getStartTime(context));
        DocData.put("TillTime",searchData.getEndDate(context)+searchData.getEndTime(context));
        DocData.put("RentPaid",searchData.getRent(context));

        //Toast.makeText(context, "Add to Orders has started", Toast.LENGTH_SHORT).show();


        db.collection("Orders").add(DocData).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    AddToUser(task.getResult().getId());
                }
            }

            private void AddToUser(String id) {
                Map<String, Object> map= new HashMap<>();
                DocumentReference DocRef = db.collection("Users").document(userData.getUserUid(context));

                DocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        Toast.makeText(context, ""+ task.getResult().contains("PlacedOrders"), Toast.LENGTH_SHORT).show();

                        if(task.getResult().contains("PlacedOrders")){
                            Map<String, Object> group= new HashMap<String, Object>((Map<? extends String, ?>) task.getResult().get("PlacedOrders"));
                            group.put(radString(),db.collection("Orders").document(id));
                            map.put("PlacedOrders",group);

                            DocRef.update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                   // Toast.makeText(context, "Order has been added to users data", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            Map<String, Object> arrayData= new HashMap<String, Object>();
                            arrayData.put(radString(),db.collection("Orders").document(id));
                            map.put("PlacedOrders",arrayData);
                            DocRef.update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                   // Toast.makeText(context, "NEw field has been created", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
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
