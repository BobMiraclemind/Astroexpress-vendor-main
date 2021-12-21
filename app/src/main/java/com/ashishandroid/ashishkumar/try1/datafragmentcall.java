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

import com.ashishandroid.ashishkumar.Adapters.myadapter;
import com.ashishandroid.ashishkumar.Adapters.myadaptercall;
import com.ashishandroid.ashishkumar.ModelClass.datamodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class datafragmentcall extends Fragment {

    String username="",key="", gender="", dob, tob, pob;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String uid = "", cstatus="";

    ArrayList<String> user_uid = new ArrayList<String>();

    ArrayList<String> userslist = new ArrayList<>();

    int index=0;
    DatabaseReference databaseReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    ArrayList<datamodel> dataholder;
    private static RecyclerView.Adapter adapter;

    public datafragmentcall() {
        // Required empty public constructor
    }


    public static datafragmentcall newInstance(String param1, String param2) {
        datafragmentcall fragment = new datafragmentcall();
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_datafragmentcall, container, false);
        //  recyclerView.setAdapter(new myadapter(dataholder, username, gender));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataholder=new ArrayList<>();

        recyclerView=view.findViewById(R.id.recview12);
        adapter = new myadaptercall(getContext(), dataholder);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getuserslist();
        recyclerView.setAdapter(adapter);


    }


    public void getuserslist(){

        mUser = mAuth.getInstance().getCurrentUser();
        if (mUser != null){
            uid = mUser.getUid();
        }

        //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("chats");
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Users");

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dsp : snapshot.getChildren()){
                    key = dsp.getKey();


                    if (snapshot.child(key.trim()).child("Request").child(uid).getValue() !=null &&
                            snapshot.child(key.trim()).child("Request").child(uid).child("user_status").getValue() !=null) {

                        if (snapshot.child(key.trim()).child("Request").child(uid).child("user_status").getValue().equals("active")) {

                            if (snapshot.child(key.trim()).child("name").getValue() != null &&
                                    snapshot.child(key.trim()).child("gen").getValue() != null &&
                                    //snapshot.child(key.trim()).child("dateOfBirth").getValue() != null &&
                                    snapshot.child(key.trim()).child("dob").getValue() != null &&
                                    //snapshot.child(key.trim()).child("timeOfBirth").getValue() != null &&
                                    snapshot.child(key.trim()).child("tob").getValue() != null &&
                                    snapshot.child(key.trim()).child("Request").child(uid).child("callaccepted").getValue() != null &&
                                    snapshot.child(key.trim()).child("pob").getValue() != null) {

                                username = snapshot.child(key).child("name").getValue(String.class);
                                //gender = snapshot.child(key).child("gender").getValue(String.class);
                                gender = snapshot.child(key).child("gen").getValue(String.class);

                                //dob = snapshot.child(key).child("dateOfBirth").getValue(String.class);
                                dob = snapshot.child(key).child("dob").getValue(String.class);
                                //tob = snapshot.child(key).child("timeOfBirth").getValue(String.class);
                                tob = snapshot.child(key).child("tob").getValue(String.class);
                                pob = snapshot.child(key).child("pob").getValue(String.class);
                                cstatus = snapshot.child(key.trim()).child("Request").child(uid).child("callaccepted").getValue(String.class);

                                if (username != null && gender != null && dob != null && tob != null && pob != null && cstatus != ""
                                ) {
                                    if (username != "" && gender != "") {
                                        datamodel ob1 = new datamodel(username, gender, key, dob, tob, pob);
                                        dataholder.add(ob1);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                    }

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }



}