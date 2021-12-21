package com.ashishandroid.ashishkumar.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashishandroid.ashishkumar.ModelClass.Message;
import com.ashishandroid.ashishkumar.try1.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter {


    Context context;
    ArrayList<Message> messages;

    final int ITEM_SENT = 1;
    final int ITEM_RECEIVE = 2;

    // String senderRoom;
    // String receiverRoom;

    public MessagesAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messages = messages;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_sent, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_receive, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(message.getSenderId())) {
            return ITEM_SENT;
        } else {
            return ITEM_RECEIVE;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message message = messages.get(position);

        if (holder.getClass() == SentViewHolder.class) {
            SentViewHolder viewHolder = (SentViewHolder) holder;
            viewHolder.messageBoxS.setText(message.getMessage());

        } else {
            ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;
            viewHolder.messageBoxR.setText(message.getMessage());

        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    public class SentViewHolder extends RecyclerView.ViewHolder {
        TextView messageBoxS;

        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            messageBoxS = itemView.findViewById(R.id.messageS);

        }
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView messageBoxR;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            messageBoxR = itemView.findViewById(R.id.messageR);

        }
    }



}
