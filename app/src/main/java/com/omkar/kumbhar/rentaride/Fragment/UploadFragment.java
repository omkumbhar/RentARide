package com.omkar.kumbhar.rentaride.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.omkar.kumbhar.rentaride.Activity.UploadVehicleActivity;
import com.omkar.kumbhar.rentaride.Adapter.ResultAdapter;
import com.omkar.kumbhar.rentaride.Adapter.SearchAdapter;
import com.omkar.kumbhar.rentaride.Method.ResultModel;
import com.omkar.kumbhar.rentaride.Method.UserData;
import com.omkar.kumbhar.rentaride.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UploadFragment extends Fragment {
    FloatingActionButton fabUpload;
    RecyclerView uploadRef;
    FirebaseFirestore db;
    List<ResultModel> result = new ArrayList<>();
    List<String> group;
    DocumentReference document;
    UserData userData;
    SearchAdapter adapter;
    private OnFragmentInteractionListener mListener;

    public UploadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Uploaded Vehicles");


        userData = new UserData();
        db = FirebaseFirestore.getInstance();
        uploadRef = view.findViewById(R.id.uploadRef);

        adapter = new SearchAdapter(result ,getContext(),"No");

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        uploadRef.setLayoutManager(mLayoutManager);
        uploadRef.setAdapter(adapter);


        fabUpload = view.findViewById(R.id.fabUpload);
        fabUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to intent to upload
                Intent intent = new Intent(getActivity(),UploadVehicleActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();



        String id =  userData.getUserUid(getContext());

        db.collection("Users").document(id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){
                            //task.getResult().get("UploadedVehicle")
                            /*group = (List<String>)task.getResult().get("UploadedVehicle");
                            group = new ArrayList<String>(((List<String>) task.getResult().get("UploadedVehicle")).get());*/

                            if(task.getResult().contains("UploadedVehicle")){
                                Map<String, Object> data= new HashMap<String, Object>((Map<? extends String, ?>) task.getResult().get("UploadedVehicle"));

                                if(data != null){
                                    AddToAdapter(data);
                                }
                            }
                        }
                    }
                });
    }

    private void AddToAdapter(Map<String,Object> data) {

        result.clear();
        for (String key : data.keySet()) {
            document= (DocumentReference)data.get(key);
            document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()) {
                        ResultModel resultModel = task.getResult().toObject(ResultModel.class);
                        resultModel.setDocId(task.getResult().getId());
                        result.add(resultModel);
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
