package com.ashishandroid.ashishkumar.Adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashishandroid.ashishkumar.ModelClass.reviewmodel;
import com.ashishandroid.ashishkumar.ModelClass.wallethistorymodel;
import com.ashishandroid.ashishkumar.try1.R;

import java.util.ArrayList;

public class wallethistoryadapter extends RecyclerView.Adapter<wallethistoryadapter.myviewholder>
{
    ArrayList<wallethistorymodel> dataholder;

    public wallethistoryadapter(ArrayList<wallethistorymodel> dataholder) {
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wallethistory_row_design,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position)
    {
        holder.name1.setText(dataholder.get(position).getName1());
        holder.wallet1.setText(dataholder.get(position).getWallet1());
        holder.date1.setText(dataholder.get(position).getDate1());
        holder.tds.setText(dataholder.get(position).getTds());
        holder.gateway.setText(dataholder.get(position).getGateway());
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView name1, wallet1, date1, tds, gateway;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            name1 = itemView.findViewById(R.id.name1);
            wallet1 = itemView.findViewById(R.id.wallet1);
            date1=itemView.findViewById(R.id.date1);
            tds = itemView.findViewById(R.id.tds);
            gateway = itemView.findViewById(R.id.paymentGateway);
        }
    }
}
