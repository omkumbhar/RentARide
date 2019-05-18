package com.omkar.kumbhar.rentaride.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.omkar.kumbhar.rentaride.Adapter.OrderAdapter;
import com.omkar.kumbhar.rentaride.Adapter.ResultAdapter;
import com.omkar.kumbhar.rentaride.Method.OrderModel;
import com.omkar.kumbhar.rentaride.Method.ResultModel;
import com.omkar.kumbhar.rentaride.Method.UserData;
import com.omkar.kumbhar.rentaride.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OderFragment extends Fragment {
    RecyclerView OrderRef;
    UserData userData;
    FirebaseFirestore db;
   // List<ResultModel> result = new ArrayList<>();
    List<OrderModel> result = new ArrayList<>();
    DocumentReference document;
    OrderAdapter adapter;
    String ImgUrl1;
  //  OrderAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OderFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static OderFragment newInstance(String param1, String param2) {
        OderFragment fragment = new OderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_oder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        OrderRef = (RecyclerView) view.findViewById(R.id.recycler);
        userData = new UserData();
        db = FirebaseFirestore.getInstance();

        adapter = new OrderAdapter(result,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        OrderRef.setLayoutManager(mLayoutManager);
        OrderRef.setAdapter(adapter);
    }



    @Override
    public void onResume() {
        super.onResume();

        String id = userData.getUserUid(getContext());
        db.collection("Users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().contains("PlacedOrders")){
                    Map<String, Object> data= new HashMap<String, Object>((Map<? extends String, ?>) task.getResult().get("PlacedOrders"));
                    if(data != null){
                        AddToAdapter(data);
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
                        OrderModel resultModel = task.getResult().toObject(OrderModel.class);
                        // String ImgUrl = getImagString(task.getResult().get("VehicleRef"));
                        DocumentReference doc = (DocumentReference) task.getResult().get("VehicleRef");
                      // resultModel.setImgUrl(ImgUrl);
                        resultModel.setOrderID(task.getResult().getId());
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    ImgUrl1 = (String) task.getResult().get("ImgUrl");
                                   // Toast.makeText(getContext(), ""+ImgUrl1, Toast.LENGTH_SHORT).show();
                                    resultModel.setImgUrl(ImgUrl1);

                                    Toast.makeText(getContext(), ""+resultModel.getImgUrl(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        result.add(resultModel);
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    private String getImagString(Object vehicleRef) {

        DocumentReference document = (DocumentReference) vehicleRef;
        document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    ImgUrl1 = (String) task.getResult().get("ImgUrl");
                }
            }
        });
        return ImgUrl1;
    }


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
        void onFragmentInteraction(Uri uri);
    }
}
