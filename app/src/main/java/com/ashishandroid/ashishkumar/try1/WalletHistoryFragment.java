package com.ashishandroid.ashishkumar.try1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashishandroid.ashishkumar.Adapters.chathistoryadapter;
import com.ashishandroid.ashishkumar.Adapters.offersadapter;
import com.ashishandroid.ashishkumar.Adapters.wallethistoryadapter;
import com.ashishandroid.ashishkumar.ModelClass.chathistorymodel;
import com.ashishandroid.ashishkumar.ModelClass.offersmodel;
import com.ashishandroid.ashishkumar.ModelClass.wallethistorymodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class WalletHistoryFragment extends Fragment {

    View view;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;


    RecyclerView recyclerView;
    ArrayList<wallethistorymodel> dataholder;
    DatabaseReference databaseReference, databaseReference1;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    String uid, user_uid;
    double totalm;

    String name ,chattime, paid, earning, adminshare, tds, pgcharge, astroct;

    public WalletHistoryFragment() {

    }


    public static WalletHistoryFragment newInstance(String param1, String param2) {
        WalletHistoryFragment fragment = new WalletHistoryFragment();
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
        view =  inflater.inflate(R.layout.fragment_wallet_history, container, false);

        recyclerView = view.findViewById(R.id.recview1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataholder = new ArrayList<wallethistorymodel>();


        databaseReference1 = FirebaseDatabase.getInstance().getReference();
        mUser = mAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            uid = mUser.getUid();

            databaseReference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot2) {

                    astroct = snapshot2.child("Astrologers").child(uid).child("chatprice").getValue(String.class);


                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });







            databaseReference = FirebaseDatabase.getInstance().getReference().child("Astrologers").child(uid).child("Payments");
            databaseReference.orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    for (final DataSnapshot dsp : snapshot.getChildren()) {

                        if (dsp.child("uname").getValue(String.class) != null &&
                                dsp.child("timestamp").getValue() != null &&
                                dsp.child("wallet").getValue(String.class) != null) {

                            user_uid = dsp.getKey();

                            name = dsp.child("uname").getValue(String.class);
                            paid = dsp.child("wallet").getValue(String.class);
                            String time = dsp.child("timestamp").getValue().toString();
                            Long t = Long.parseLong(time);
                            SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");
                            String tim = sfd.format(new Date(t));

                            DecimalFormat df = new DecimalFormat("0.00");

                            //String chattime = df.format((Double.parseDouble(paid) / Double.parseDouble(astroct)));

                            String tds1 = df.format(((10 / 100) * Double.parseDouble(paid)));
                            String paygate = df.format(((1.25 / 100) * Double.parseDouble(paid)));

                            double leftmoney = Double.parseDouble(paid) - Double.parseDouble(tds1) - Double.parseDouble(paygate);
                            String admin = df.format(leftmoney / 2);
                            String astro = df.format(leftmoney / 2);


                            wallethistorymodel ob1 = new wallethistorymodel("Recieved from " + name, "" + tim, "+ Rs. " + astro,"- Rs. " + tds1,"- Rs. " + paygate);
                            dataholder.add(ob1);
                            Collections.reverse(dataholder);
                            recyclerView.setAdapter(new wallethistoryadapter(dataholder));
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