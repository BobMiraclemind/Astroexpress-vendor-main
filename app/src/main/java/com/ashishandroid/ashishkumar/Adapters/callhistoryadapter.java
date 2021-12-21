package com.ashishandroid.ashishkumar.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashishandroid.ashishkumar.ModelClass.callhistorymodel;
import com.ashishandroid.ashishkumar.ModelClass.chathistorymodel;
import com.ashishandroid.ashishkumar.ModelClass.offersmodel;
import com.ashishandroid.ashishkumar.try1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class callhistoryadapter extends RecyclerView.Adapter<callhistoryadapter.myviewholder> {

    ArrayList<callhistorymodel> dataholder;
    String offers_uid;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference;
    String uid3;


    public callhistoryadapter(ArrayList<callhistorymodel> dataholder) {
        this.dataholder = dataholder;

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chathistory_row_design,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        holder.name.setText(dataholder.get(position).getName());
        holder.chattime.setText(dataholder.get(position).getChattime());
        holder.paid.setText(dataholder.get(position).getPaid());
        holder.earning.setText(dataholder.get(position).getEarning());
        holder.adminshare.setText(dataholder.get(position).getAdminshare());
        holder.tds.setText(dataholder.get(position).getTds());
        holder.pgcharge.setText(dataholder.get(position).getPgcharge());

        holder.dname.setText("Call Time (in mins) : ");

    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }


    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView name,chattime, paid, earning, adminshare, tds, pgcharge, dname;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.t1);
            chattime=itemView.findViewById(R.id.t2);
            paid=itemView.findViewById(R.id.t3);
            earning=itemView.findViewById(R.id.t7);
            adminshare=itemView.findViewById(R.id.t4);
            tds=itemView.findViewById(R.id.t5);
            pgcharge=itemView.findViewById(R.id.t6);

            dname = itemView.findViewById(R.id.dname);


        }
    }
}
