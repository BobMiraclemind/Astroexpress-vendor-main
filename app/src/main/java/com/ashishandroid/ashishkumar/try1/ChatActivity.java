package com.ashishandroid.ashishkumar.try1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashishandroid.ashishkumar.Adapters.MessagesAdapter;
import com.ashishandroid.ashishkumar.ModelClass.Message;
import com.ashishandroid.ashishkumar.ModelClass.customdialogmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity implements customdialogclass.ExampleDialogListener {

    EditText messageBox;
    ImageView sendBtn, mic;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    MessagesAdapter adapter;

    ArrayList<Message> messages;
    String senderRoom, ReceiverRoom;
    FirebaseDatabase database;

    ValueEventListener chatListener;
    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference;
    String uid2, token, senderUid, receiverUid, username, name;
    String chatt = "Busy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        name = getIntent().getStringExtra("username");

        Objects.requireNonNull(getSupportActionBar()).setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006666")));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        messageBox = (EditText) findViewById(R.id.messageBox);
        mic = findViewById(R.id.mic);

        sendBtn = (ImageView) findViewById(R.id.sendBtn);
        recyclerView = findViewById(R.id.recyclerView);
        messages = new ArrayList<>();
        adapter = new MessagesAdapter(this, messages);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        receiverUid = getIntent().getStringExtra("user_uid");

        //for custom dialog box
        new customdialogmodel(receiverUid);

        senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        senderRoom = senderUid + receiverUid;
        ReceiverRoom = receiverUid + senderUid;

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
            dbRef.child("Astrologers").child(senderUid).child("token").setValue("" + refreshedToken);

        }
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            uid2 = mUser.getUid();

            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Users").child(receiverUid).child("Request").child(uid2).child("chataccepted").setValue("true");
            databaseReference.child("Astrologers").child(uid2).child("chatstatus").setValue(chatt);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    token = snapshot.child("Users").child(receiverUid).child("token").getValue(String.class);
                    username = snapshot.child("Astrologers").child(senderUid).child("displayname").getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        database = FirebaseDatabase.getInstance();
        database.getReference().child("chats").child(ReceiverRoom).child("messages")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messages.clear();

                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            try {
                                Message message = new Message(String.valueOf(snapshot1.child("message").getValue()),
                                        String.valueOf(snapshot1.child("senderId").getValue()),
                                        Long.parseLong(String.valueOf("1234567890")),
                                        String.valueOf(snapshot1.child("sender").getValue()));
                                message.setMessageId(snapshot1.getKey());
                                messages.add(message);
                                recyclerView.scrollToPosition(messages.size() - 1);

                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(ChatActivity.this, " Error is 1" + error, Toast.LENGTH_SHORT).show();

                    }
                });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!messageBox.getText().toString().isEmpty()){
                    String messageTxt = messageBox.getText().toString();
                    Date date = new Date();
                    final Message message = new Message(messageTxt, senderUid, date.getTime(), username);
                    messageBox.setText("");

                    String keyReceiverRoom = database.getReference().child("chats").child(ReceiverRoom).child("messages").push().getKey();
                    database.getReference().child("chats").child(ReceiverRoom).child("messages").child(keyReceiverRoom).child("message").setValue(messageTxt);
                    database.getReference().child("chats").child(ReceiverRoom).child("messages").child(keyReceiverRoom).child("sender").setValue(username);
                    database.getReference().child("chats").child(ReceiverRoom).child("messages").child(keyReceiverRoom).child("senderId").setValue(senderUid);
                    database.getReference().child("chats").child(ReceiverRoom).child("messages").child(keyReceiverRoom).child("timestamp").setValue(date.getTime());
                }
            }
        });


        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                } catch (Exception e) {
                    Toast.makeText(ChatActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                messageBox.setText(Objects.requireNonNull(result).get(0));
            }
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainchat, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.info:
                //Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
                customdialogclass exampleDialog = new customdialogclass();
                exampleDialog.show(getSupportFragmentManager(), "ashishandroid dialog");
                return true;

            case R.id.chathis1:
                Toast.makeText(this, "Chat History", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChatActivity.this, ChatActivityHistorychat.class);
                intent.putExtra("username", name);
                intent.putExtra("user_uid", receiverUid);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void applyTexts(String username, String password) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(receiverUid).child("Request").child(uid2).child("chataccepted").setValue("false");
        databaseReference.child("Astrologers").child(uid2).child("chatstatus").setValue("Online");
        finish();
    }


}