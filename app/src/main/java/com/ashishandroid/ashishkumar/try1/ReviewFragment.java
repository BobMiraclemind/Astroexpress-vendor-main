package com.ashishandroid.ashishkumar.try1;

import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashishandroid.ashishkumar.Adapters.offersadapter;
import com.ashishandroid.ashishkumar.Adapters.reviewadapter;
import com.ashishandroid.ashishkumar.ModelClass.offersmodel;
import com.ashishandroid.ashishkumar.ModelClass.reviewmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ReviewFragment extends Fragment
{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    ArrayList<reviewmodel> dataholder;

    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    String review_id, uid;


    public ReviewFragment() {
        // Required empty public constructor
    }



    public static ReviewFragment newInstance(String param1, String param2) {
        ReviewFragment fragment = new ReviewFragment();
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
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_datafragment, container, false);
        recyclerView=view.findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataholder=new ArrayList<>();


        mUser = mAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            uid = mUser.getUid();

            databaseReference = FirebaseDatabase.getInstance().getReference().child("Astrologers").child(uid).child("Reviews");
            databaseReference.orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    for (DataSnapshot dsp : snapshot.getChildren()) {

                        review_id = dsp.getKey();

                        if (dsp.child("timestamp").getValue() != null &&
                                dsp.child("username").getValue(String.class) != null &&
                                dsp.child("rating").getValue() != null &&
                                dsp.child("comments").getValue(String.class) != null){

                            String uname = dsp.child("username").getValue(String.class);
                        String rate = dsp.child("rating").getValue().toString();
                        String review = dsp.child("comments").getValue(String.class);
                        String time = dsp.child("timestamp").getValue().toString();
                        Long t = Long.parseLong(time);
                        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy");
                        String tim = sfd.format(new Date(t));

                                //reviewmodel ob1 = new reviewmodel(uname, rate, review_id, tim);
                                reviewmodel ob1 = new reviewmodel(uname, rate, review_id, "" + review + "\n" + tim);
                                dataholder.add(ob1);
                                Collections.reverse(dataholder);    // for reversing the data
                                recyclerView.setAdapter(new reviewadapter(dataholder));

                    }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }






        return view;
    }
}