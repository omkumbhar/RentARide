package com.omkar.kumbhar.rentaride.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.omkar.kumbhar.rentaride.Adapter.ResultAdapter;
import com.omkar.kumbhar.rentaride.Method.ResultModel;
import com.omkar.kumbhar.rentaride.Method.SearchData;
import com.omkar.kumbhar.rentaride.R;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    FirebaseFirestore db;
    List<ResultModel> result = new ArrayList<>();
    RecyclerView Recycler;
    ResultAdapter adapter;
    SearchData searchData;
    String Vehicle;
    CollectionReference cRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        setTitle("Result");


        db = FirebaseFirestore.getInstance();
        searchData = new SearchData();

        Intent intent = getIntent();
        Vehicle = intent.getStringExtra("Vehicle");

        Recycler = (RecyclerView) findViewById(R.id.Recycler);
        adapter = new ResultAdapter(result,getApplicationContext(),Vehicle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        Recycler.setLayoutManager(mLayoutManager);
        Recycler.setAdapter(adapter);


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




        Query query = cRef.whereEqualTo("Location", searchData.getSource(getApplicationContext()));

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    result.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        ResultModel resultModel  = document.toObject(ResultModel.class);
                        resultModel.setDocId(document.getId());
                        result.add(resultModel);
                       // Toast.makeText(ResultActivity.this, ""+resultModel.getCarName(), Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }
}
