package com.ashishandroid.ashishkumar.try1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashishandroid.ashishkumar.Adapters.myadapter;
import com.ashishandroid.ashishkumar.ModelClass.datamodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class datafragment extends Fragment {
    String username = "", key = "", gender = "", dob, tob, pob;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String uid = "", str = "";

    ArrayList<String> user_uid = new ArrayList<String>();

    ArrayList<String> userslist = new ArrayList<>();

    int index = 0;
    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ArrayList<datamodel> dataholder;
    private static RecyclerView.Adapter adapter;


    public datafragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_datafragment, container, false);

        //  recyclerView.setAdapter(new myadapter(dataholder, username, gender));

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataholder = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recview);
        adapter = new myadapter(getContext(), dataholder);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        getuserslist();
    }


    public void getuserslist() {

        mUser = mAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            uid = mUser.getUid();
        }

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("chats");

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dsp : snapshot.getChildren()) {
                    key = dsp.getKey();

                    // String str = "programmingJavaisa"; = key
                    index = key.indexOf(uid);
                    char[] ch = new char[key.length()];

                    if (index == 0) {
                        for (int i = uid.length(); i < key.length(); i++) {
                            ch[i] = key.charAt(i);
                        }
                    }

                    if (index == 0) {
                        user_uid.add(String.valueOf(ch).trim());
                    }

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //for ( String str: user_uid)
                for (DataSnapshot dsp21 : snapshot.getChildren()) {
                    str = dsp21.getKey();

                    if (snapshot.child(str.trim()).child("Request").child(uid).getValue() != null &&
                            snapshot.child(str.trim()).child("Request").child(uid).child("user_status").getValue() != null) {

                        if (snapshot.child(str.trim()).child("Request").child(uid).child("user_status").getValue().equals("active")) {

                            if (snapshot.child(str.trim()).child("name").getValue() != null &&
                                    snapshot.child(str.trim()).child("gen").getValue() != null &&
                                    snapshot.child(str.trim()).child("dob").getValue() != null &&
                                    snapshot.child(str.trim()).child("tob").getValue() != null &&
                                    snapshot.child(str.trim()).child("pob").getValue() != null) {

                                username = snapshot.child(str.trim()).child("name").getValue(String.class);
                                gender = snapshot.child(str.trim()).child("gen").getValue(String.class);
                                dob = snapshot.child(str.trim()).child("dob").getValue(String.class);
                                tob = snapshot.child(str.trim()).child("tob").getValue(String.class);
                                pob = snapshot.child(str.trim()).child("pob").getValue(String.class);


                                //&& status.equals("true")

                                if (username != "" && gender != "" && dob != "" && tob != "" && pob != "") {
                                    datamodel ob1 = new datamodel(username, gender, str, dob, tob, pob);
                                    dataholder.add(ob1);
                                    adapter.notifyDataSetChanged();
//                                    recyclerView.setAdapter(adapter);
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