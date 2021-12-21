package com.ashishandroid.ashishkumar.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashishandroid.ashishkumar.try1.AstrologerCallActivity;
import com.ashishandroid.ashishkumar.try1.ChatActivity;
import com.ashishandroid.ashishkumar.try1.R;
import com.ashishandroid.ashishkumar.ModelClass.datamodel;

import java.util.ArrayList;

public class myadaptercall extends RecyclerView.Adapter<myadaptercall.myviewholder>
{
    Context context;
    ArrayList<datamodel> dataholder;
    String username, useruid;

    public myadaptercall(Context context, ArrayList<datamodel> dataholder) {
        this.context = context;
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design_call,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position)
    {
        final datamodel chatholder = dataholder.get(position);
        holder.name.setText(chatholder.getName());
        holder.gender.setText(chatholder.getEmail());

        holder.dob.setText(chatholder.getDob());
        holder.tob.setText(chatholder.getTob());
        holder.pob.setText(chatholder.getPob());


        //holder.chatbutton.setText("CALL");


        /*
        holder.chatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("username",chatholder.getName().trim());
                intent.putExtra("user_uid",chatholder.getUidu().trim());

                context.startActivity(intent);

            }
        });

         */

        holder.callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, chatholder.getUidu().trim(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, AstrologerCallActivity.class);
                intent.putExtra("username",chatholder.getName().trim());
                intent.putExtra("user_uid",chatholder.getUidu().trim());

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView name,gender,sett,dob, tob, pob;
        Button chatbutton, callbutton;

        public myviewholder(@NonNull final View itemView)
        {
            super(itemView);

            name =itemView.findViewById(R.id.t1);
            gender =itemView.findViewById(R.id.t2);

            dob =itemView.findViewById(R.id.t5);
            tob =itemView.findViewById(R.id.t7);
            pob =itemView.findViewById(R.id.t9);


            //chatbutton =itemView.findViewById(R.id.chatbutton);
            callbutton =itemView.findViewById(R.id.callbutton);


            /*

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //gender.setText(getUseruid());

                   Intent intent = new Intent(itemView.getContext(), ChatActivity.class);
                   intent.putExtra("username",getUsername().trim());
                   intent.putExtra("user_uid",getUseruid().trim());

                   itemView.getContext().startActivity(intent);



                }
            });


             */


        }
    }
}
