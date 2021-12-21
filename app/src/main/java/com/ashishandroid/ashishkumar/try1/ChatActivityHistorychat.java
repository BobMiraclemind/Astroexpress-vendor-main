package com.ashishandroid.ashishkumar.try1;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashishandroid.ashishkumar.Adapters.MessagesAdapter;
import com.ashishandroid.ashishkumar.ModelClass.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivityHistorychat extends AppCompatActivity {

    EditText messageBox;
    ImageView sendBtn;

    MessagesAdapter adapter;
    ArrayList<Message> messages;
    String senderRoom, ReceiverRoom;
    FirebaseDatabase database;

    RecyclerView recyclerView;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference;

    String uid2, token, senderUid, receiverUid, username, name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_historychat);

        name = getIntent().getStringExtra("username");

        getSupportActionBar().setTitle(name + " (History)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006666")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        messageBox = (EditText) findViewById(R.id.messageBox);
        sendBtn = (ImageView) findViewById(R.id.sendBtn);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        messages = new ArrayList<>();
        adapter = new MessagesAdapter(this, messages);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        receiverUid = getIntent().getStringExtra("user_uid").trim();

        //for custom dialog box
        //new customdialogmodel(receiverUid);


        senderRoom = senderUid + receiverUid;
        ReceiverRoom = receiverUid + senderUid;

        receiverUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance();
        database.getReference().child("chats").child(senderRoom).child("messages")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        messages.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Message message = snapshot1.getValue(Message.class);
                            messages.add(message);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}