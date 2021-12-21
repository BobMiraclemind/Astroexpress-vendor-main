package com.ashishandroid.ashishkumar.Adapters;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ashishandroid.ashishkumar.try1.R;
import com.ashishandroid.ashishkumar.ModelClass.offersmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class offersadapter extends RecyclerView.Adapter<offersadapter.myviewholder>{

    Context context;
    ArrayList<offersmodel> dataholder;
    String offers_uid;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference;
    String uid3, uid, chatprice, chatpricebeforeoffer, other = "false", chatofferstatus;

    int chatdiscount = 0, calldiscount = 0;
    String callprice, callpricebeforeoffer, callofferstatus;



    public offersadapter(Context context, ArrayList<offersmodel> dataholder) {
        this.context= context;
        this.dataholder = dataholder;
        //this.offers_uid = offers_uid;
    }


    @NonNull
    @Override
    public offersadapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_row_design,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull offersadapter.myviewholder holder, int position) {

        final offersmodel offerinfo = dataholder.get(position);
        holder.header.setText(offerinfo.getHeader());
        holder.desc.setText(offerinfo.getDesc());
        holder.type.setText(offerinfo.getType());
        holder.india.setText(offerinfo.getIndia());
        holder.share.setText(offerinfo.getShare());
        holder.pays.setText(offerinfo.getPays());





        mUser = mAuth.getInstance().getCurrentUser();
        if (mUser != null){
            uid = mUser.getUid();

            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    chatprice = snapshot.child("Astrologers").child(uid).child("chatprice").getValue(String.class);
                    callprice = snapshot.child("Astrologers").child(uid).child("callprice").getValue(String.class);

                    if (snapshot.child("Astrologers").child(uid).child("chatprice(beforeOffer)").getValue() != null){
                        chatpricebeforeoffer = snapshot.child("Astrologers").child(uid).child("chatprice(beforeOffer)").getValue(String.class);
                    }

                    if (snapshot.child("Astrologers").child(uid).child("callprice(beforeOffer)").getValue() != null){
                        callpricebeforeoffer = snapshot.child("Astrologers").child(uid).child("callprice(beforeOffer)").getValue(String.class);
                    }



                    if (snapshot.child("Astrologers").child(uid).child("chatOfferStatus").getValue() == null){
                        databaseReference.child("Astrologers").child(uid).child("chatOfferStatus").setValue("Inactive");
                    }
                    chatofferstatus = snapshot.child("Astrologers").child(uid).child("chatOfferStatus").getValue(String.class);

                    if (snapshot.child("Astrologers").child(uid).child("callOfferStatus").getValue() == null){
                        databaseReference.child("Astrologers").child(uid).child("callOfferStatus").setValue("Inactive");
                    }
                    callofferstatus = snapshot.child("Astrologers").child(uid).child("callOfferStatus").getValue(String.class);

                    if (snapshot.child("Astrologers").child(uid).child("offers").child(offerinfo.getOuid()).getValue() != null) {

                    if (snapshot.child("Astrologers").child(uid).child("offers").child(offerinfo.getOuid()).getValue().equals("Active")) {
                        holder.offer.setChecked(true);
                    }
                }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }



        holder.offer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                databaseReference = FirebaseDatabase.getInstance().getReference();
                if (isChecked) {
                    mUser = mAuth.getInstance().getCurrentUser();
                    if (mUser != null) {
                        uid3 = mUser.getUid();


                        if (other.equals("false") ) {
                            other = "true";

                            if (chatofferstatus.equals("Inactive")) {

                                if ("50% off for new users".equals(offerinfo.getHeader())) {

                                    chatdiscount = (int) (Integer.parseInt(chatprice) - (0.5 * Integer.parseInt(chatprice)));
                                    calldiscount = (int) (Integer.parseInt(callprice) - (0.5 * Integer.parseInt(callprice)));

                                } else if ("Happy birthday offer".equals(offerinfo.getHeader())) {

                                    chatdiscount = (int) (Integer.parseInt(chatprice) - (0.2 * Integer.parseInt(chatprice)));
                                    calldiscount = (int) (Integer.parseInt(callprice) - (0.2 * Integer.parseInt(callprice)));

                                } else if ("First time mega offer".equals(offerinfo.getHeader())) {

                                    chatdiscount = (int) (Integer.parseInt(chatprice) - (0.1 * Integer.parseInt(chatprice)));
                                    calldiscount = (int) (Integer.parseInt(callprice) - (0.1 * Integer.parseInt(callprice)));
                                }


                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        databaseReference.child("Astrologers").child(uid).child("chatprice(beforeOffer)").setValue("" + chatprice);
                                        databaseReference.child("Astrologers").child(uid).child("chatprice").setValue("" + chatdiscount);

                                        databaseReference.child("Astrologers").child(uid).child("callprice(beforeOffer)").setValue("" + callprice);
                                        databaseReference.child("Astrologers").child(uid).child("callprice").setValue("" + calldiscount);

                                    }
                                }, 1000);


                                Toast.makeText(context, "ChatPrice Rs. " + chatdiscount, Toast.LENGTH_SHORT).show();
                                Toast.makeText(context, "CallPrice Rs. " + calldiscount, Toast.LENGTH_SHORT).show();

                                databaseReference.child("Astrologers").child(uid).child("chatOfferStatus").setValue("Active");
                                databaseReference.child("Astrologers").child(uid).child("callOfferStatus").setValue("Active");

                                databaseReference.child("Astrologers").child(uid3).child("offers").child(offerinfo.getOuid()).setValue("Active");
                                holder.offer.setChecked(true);


                            }
                        }
                        else {
                            holder.offer.setChecked(false);
                            Toast.makeText(context, "Only one offer at a time", Toast.LENGTH_SHORT).show();
                        }



                    }
                } else {
                    if (mUser != null) {
                        other = "false";
                        uid3 = mUser.getUid();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                databaseReference.child("Astrologers").child(uid).child("chatprice(beforeOffer)").setValue(null);
                                databaseReference.child("Astrologers").child(uid).child("chatprice").setValue("" + chatpricebeforeoffer);

                                databaseReference.child("Astrologers").child(uid).child("callprice(beforeOffer)").setValue(null);
                                databaseReference.child("Astrologers").child(uid).child("callprice").setValue("" + callpricebeforeoffer);

                            }
                        }, 1000);

                        databaseReference.child("Astrologers").child(uid3).child("offers").child(offerinfo.getOuid()).setValue("Inactive");
                        databaseReference.child("Astrologers").child(uid).child("chatOfferStatus").setValue("Inactive");
                        databaseReference.child("Astrologers").child(uid).child("callOfferStatus").setValue("Inactive");
                    }
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView header,desc, type, india, share, pays;
        SwitchCompat offer;

        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            header=itemView.findViewById(R.id.t1);
            desc=itemView.findViewById(R.id.t2);
            type=itemView.findViewById(R.id.t3);
            india=itemView.findViewById(R.id.t4);
            share=itemView.findViewById(R.id.t5);
            pays=itemView.findViewById(R.id.t6);

            offer = itemView.findViewById(R.id.offers_toggle);

            //offer.setOnCheckedChangeListener(onCheckedChanged());


            }


        }





}

