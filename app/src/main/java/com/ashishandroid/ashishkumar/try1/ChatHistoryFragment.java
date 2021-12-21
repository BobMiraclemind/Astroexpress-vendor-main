package com.ashishandroid.ashishkumar.try1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashishandroid.ashishkumar.Adapters.chathistoryadapter;
import com.ashishandroid.ashishkumar.ModelClass.callhistorymodel;
import com.ashishandroid.ashishkumar.ModelClass.chathistorymodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.awt.font.NumericShaper;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class ChatHistoryFragment extends Fragment {

    View view;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    ArrayList<chathistorymodel> dataholder1;
    DatabaseReference databaseReference, databaseReference1;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    String uid, user_uid;
    double totalm, totalchattime = 0.0;


    String name ,chattime, paid, earning, adminshare, tds, pgcharge, astroct;

    public ChatHistoryFragment() {

    }


    public static ChatHistoryFragment newInstance(String param1, String param2) {
        ChatHistoryFragment fragment = new ChatHistoryFragment();
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

        view = inflater.inflate(R.layout.fragment_chat_history, container, false);
        recyclerView = view.findViewById(R.id.recview2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataholder1 = new ArrayList<chathistorymodel>();


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



        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Astrologers").child(uid).child("Payments");


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Astrologers").child(uid).child("Payments");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                for (final DataSnapshot dsp : snapshot.getChildren()) {

                    if (dsp.child("uname").getValue(String.class) != null &&
                            dsp.child("wallet").getValue() != null) {

                        user_uid = dsp.getKey();

                        name = dsp.child("uname").getValue(String.class);
                        paid = dsp.child("wallet").getValue().toString();
                        DecimalFormat df = new DecimalFormat("0.00");

                        String chattime = df.format((Double.parseDouble(paid) / Double.parseDouble(astroct)));
                        reference.child(user_uid).child("chat_time").setValue("" + chattime + " mins");

                        String tds1 = df.format(((10 / 100) * Double.parseDouble(paid)));
                        String paygate = df.format(((1.25 / 100) * Double.parseDouble(paid)));

                        double leftmoney = Double.parseDouble(paid) - Double.parseDouble(tds1) - Double.parseDouble(paygate);
                        String admin = df.format(leftmoney / 2);
                        String astro = df.format(leftmoney / 2);

                        totalm = totalm + Double.parseDouble(admin);
                        totalchattime = totalchattime + (Double.parseDouble(paid) / Double.parseDouble(astroct));

                        DecimalFormat df1 = new DecimalFormat("0.00");

                        chathistorymodel ob1 = new chathistorymodel(name, chattime, "Rs. " + df.format(Double.parseDouble(paid)), "Rs. " + astro, "Rs. " + admin, "Rs. " + tds1, "Rs. " + paygate);
                        dataholder1.add(ob1);
                        recyclerView.setAdapter(new chathistoryadapter(dataholder1));

                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    DecimalFormat df = new DecimalFormat("0.00");
                    databaseReference1.child("Astrologers").child(uid).child("wallet").setValue("" + df.format(totalm));
                    databaseReference1.child("Astrologers").child(uid).child("totalchattime").setValue("" + df.format(totalchattime));

                }
            }, 2000);


    }


        return view;
    }
}