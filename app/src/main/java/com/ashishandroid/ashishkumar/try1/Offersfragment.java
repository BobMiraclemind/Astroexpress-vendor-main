package com.ashishandroid.ashishkumar.try1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashishandroid.ashishkumar.Adapters.offersadapter;
import com.ashishandroid.ashishkumar.ModelClass.offersmodel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Offersfragment extends Fragment {

    View view;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    ArrayList<offersmodel> dataholder;
    DatabaseReference databaseReference, databaseReference1;

    private static RecyclerView.Adapter adapter;

    String offers_uid;

    public Offersfragment() {

    }


    public static Offersfragment newInstance(String param1, String param2) {
        Offersfragment fragment = new Offersfragment();
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
        view  = inflater.inflate(R.layout.fragment_offersfragment, container, false);
        return view;



    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("offer");

        dataholder=new ArrayList<>();
        adapter = new offersadapter(getContext(), dataholder);

        recyclerView=view.findViewById(R.id.recview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getOffers();
        recyclerView.setAdapter(adapter);


    }


    public void getOffers(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                for (DataSnapshot dsp : snapshot.getChildren()) {

                    offers_uid = dsp.getKey();

                    String offername = dsp.child("offername").getValue(String.class);
                    String displayname = dsp.child("displayname").getValue(String.class);
                    String usertype = dsp.child("usertype").getValue(String.class);
                    String myshare = dsp.child("myshare").getValue(String.class);
                    String atshare = dsp.child("atshare").getValue(String.class);
                    String pays = dsp.child("customerpays").getValue(String.class);

                    offersmodel ob1=new offersmodel(offername, displayname,usertype, myshare, atshare, pays, offers_uid);
                    dataholder.add(ob1);
                    adapter.notifyDataSetChanged();
                    //recyclerView.setAdapter(new offersadapter(adapter));


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }



}