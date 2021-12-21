package com.ashishandroid.ashishkumar.try1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Date;

public class StatusActivity extends AppCompatActivity {

    private String chatt = "Offline", callt = "Offline", reportt = "Offline", uid = "Offline", uid2, username;
    EditText chat_time, call_time, report_time;
    Button save_status;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference;

    ProgressDialog mDialog ;

    SwitchCompat chat, call, report;

    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        getSupportActionBar().setTitle("Status Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006666")));

         chat_time = (EditText) findViewById(R.id.chat_time);
         call_time = (EditText) findViewById(R.id.call_time);
         report_time = (EditText) findViewById(R.id.report_time);

        chat = (SwitchCompat) findViewById(R.id.chat_toggle);
        chat.setOnCheckedChangeListener(onCheckedChanged());

        call = (SwitchCompat) findViewById(R.id.call_toggle);
        call.setOnCheckedChangeListener(onCheckedChanged());

        report = (SwitchCompat) findViewById(R.id.report_toggle);
        report.setOnCheckedChangeListener(onCheckedChanged());

        //chat.setChecked(true);


        text1 = findViewById(R.id.statusview);

        //text1.setPaintFlags(text1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        save_status = (Button) findViewById(R.id.save_status);

        mDialog = new ProgressDialog(this);



        // for notifications

        //FirebaseMessaging.getInstance().subscribeToTopic("all");

        FirebaseUser mUser1 = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser1 != null) {
            uid2 = mUser1.getUid();

            DatabaseReference dr1 = FirebaseDatabase.getInstance().getReference();
            dr1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    username = snapshot.child("Astrologers").child(uid2).child("displayname").getValue(String.class);


                    if(snapshot.child("Astrologers").child(uid2).child("token").getValue(String.class) == null){

                        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                        dr1.child("Astrologers").child(uid2).child("token").setValue("" + refreshedToken);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

        // for notifications


        save_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDialog.setMessage("Updating Sattus ....");
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();

                // new changes
                mUser = mAuth.getInstance().getCurrentUser();
                uid = mUser.getUid();

                if (chatt.equals("Online")){

                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all",username,"Online for Chat now"
                            ,getApplicationContext(),StatusActivity.this);
                    notificationsSender.SendNotifications();

                    databaseReference.child("Notifications").child(uid).child("timestamp").setValue("" + new Date().getTime());
                    databaseReference.child("Notifications").child(uid).child("Astroname").setValue("" + username);
                    databaseReference.child("Notifications").child(uid).child("desc").setValue("Online for Chat now");

                }
                if (callt.equals("Online")){

                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all",username,"Online for Call now"
                            ,getApplicationContext(),StatusActivity.this);
                    notificationsSender.SendNotifications();

                    // new changes
                    databaseReference.child("Notifications").child(uid).child("timestamp").setValue("" + new Date().getTime());
                    databaseReference.child("Notifications").child(uid).child("Astroname").setValue("" + username);
                    databaseReference.child("Notifications").child(uid).child("desc").setValue("Online for Call now");
                }



                mUser = mAuth.getInstance().getCurrentUser();
                if (mUser != null) {
                    uid = mUser.getUid();

                    databaseReference = FirebaseDatabase.getInstance().getReference();

                    databaseReference.child("Astrologers").child(uid).child("chattime").setValue(chat_time.getText().toString());
                    databaseReference.child("Astrologers").child(uid).child("calltime").setValue(call_time.getText().toString());
                    databaseReference.child("Astrologers").child(uid).child("reporttime").setValue(report_time.getText().toString());

                    databaseReference.child("Astrologers").child(uid).child("chatstatus").setValue(chatt);
                    databaseReference.child("Astrologers").child(uid).child("callstatus").setValue(callt);
                    databaseReference.child("Astrologers").child(uid).child("reportstatus").setValue(reportt);

                    startActivity(new Intent(StatusActivity.this,DashboardActivity.class));
                    Toast.makeText(StatusActivity.this, "Status Updated !!", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();

                }else{
                    Toast.makeText(StatusActivity.this,"Error while saving details, Please try again !!",Toast.LENGTH_SHORT).show();
                }

            }

        });



    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChanged() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.chat_toggle:
                        setState1(isChecked);
                        break;
                    case R.id.call_toggle:
                        setState2(isChecked);
                        break;
                    case R.id.report_toggle:
                        setState3(isChecked);
                        break;
                }
            }
        };
    }



    private void setState1(boolean state) {
        if (state) {
            chatt = "Online";
            Toast.makeText(StatusActivity.this, "Online for chat", Toast.LENGTH_SHORT).show();
        } else {
            chatt = "Offline";
            Toast.makeText(StatusActivity.this, "Offline for chat", Toast.LENGTH_SHORT).show();
        }
    }

    private void setState2(boolean state) {
        if (state) {
            callt = "Online";
            Toast.makeText(StatusActivity.this, "Online for call", Toast.LENGTH_SHORT).show();
        } else {
            callt = "Offline";
            Toast.makeText(StatusActivity.this, "Offline for call", Toast.LENGTH_SHORT).show();
        }
    }

    private void setState3(boolean state) {
        if (state) {
            reportt = "Online";
            Toast.makeText(StatusActivity.this, "Online", Toast.LENGTH_SHORT).show();
        } else {
            reportt = "Offline";
            Toast.makeText(StatusActivity.this, "Offline", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onStart() {
        super.onStart();


        mUser = mAuth.getInstance().getCurrentUser();
        if (mUser != null){
            uid = mUser.getUid();

            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    chat_time.setText(snapshot.child("Astrologers").child(uid).child("chattime").getValue(String.class));
                    call_time.setText(snapshot.child("Astrologers").child(uid).child("calltime").getValue(String.class));
                    report_time.setText(snapshot.child("Astrologers").child(uid).child("reporttime").getValue(String.class));


                    if (snapshot.child("Astrologers").child(uid).child("chatstatus").getValue(String.class).equals("Online")){
                        chat.setChecked(true);
                    }

                    if (snapshot.child("Astrologers").child(uid).child("callstatus").getValue(String.class).equals("Online")){
                        call.setChecked(true);
                    }

                    if (snapshot.child("Astrologers").child(uid).child("reportstatus").getValue(String.class).equals("Online")){
                        report.setChecked(true);
                    }



                    
                    if (snapshot.child("Astrologers").child(uid).child("chatstatus").getValue(String.class).equals("Offline")){
                        chat.setChecked(false);
                    }
                    if (snapshot.child("Astrologers").child(uid).child("chatstatus").getValue(String.class).equals("Offline")){
                        call.setChecked(false);
                    }
                    if (snapshot.child("Astrologers").child(uid).child("chatstatus").getValue(String.class).equals("Offline")){
                        report.setChecked(false);
                    }



                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }


    }


}