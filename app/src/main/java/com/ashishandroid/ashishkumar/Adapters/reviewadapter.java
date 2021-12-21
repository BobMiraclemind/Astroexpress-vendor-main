package com.ashishandroid.ashishkumar.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashishandroid.ashishkumar.ModelClass.reviewmodel;
import com.ashishandroid.ashishkumar.try1.R;

import java.util.ArrayList;

public class reviewadapter extends RecyclerView.Adapter<reviewadapter.myviewholder>
{
    ArrayList<reviewmodel> dataholder;

    public reviewadapter(ArrayList<reviewmodel> dataholder) {
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_single_row_design,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position)
    {
        holder.name.setText(dataholder.get(position).getHeader());
        holder.rate.setText(dataholder.get(position).getDesc());
        holder.review.setText(dataholder.get(position).getReview());
        holder.orderid.setText(dataholder.get(position).getOrderid());
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView name,orderid,rate,review;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            orderid = itemView.findViewById(R.id.oid);
            review = itemView.findViewById(R.id.review);
            name=itemView.findViewById(R.id.t1);
            rate=itemView.findViewById(R.id.rate);
        }
    }
}
