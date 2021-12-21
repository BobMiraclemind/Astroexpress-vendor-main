package com.ashishandroid.ashishkumar.try1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.webrtc.audio.JavaAudioDeviceModule;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ashishandroid.ashishkumar.ModelClass.customdialogmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AstrologerCallActivity extends AppCompatActivity implements customdialogclass.ExampleDialogListener{
    SinchClient sinchClient;
    String receiverUid;
    Call call;
    FirebaseDatabase firebaseDatabase;
    Map<String, Object> status;
    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;

    String username, token, uid2, callername;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astrologer_call);

        getSupportActionBar().setTitle("Call");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006666")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        receiverUid = getIntent().getStringExtra("user_uid");
        new customdialogmodel(receiverUid);

        firebaseDatabase = FirebaseDatabase.getInstance();
        status = new HashMap<>();





        // for notifications

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            uid2 = mUser.getUid();


            databaseReference = FirebaseDatabase.getInstance().getReference();

            //databaseReference.child("Astrologers").child(uid2).child("Request").child("callstatus").setValue("true");
            databaseReference.child("Users").child(receiverUid).child("Request").child(uid2).child("callaccepted").setValue("true");


            DatabaseReference dr1 = FirebaseDatabase.getInstance().getReference();
            dr1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

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








        sinchClient = Sinch.getSinchClientBuilder()
                .context(AstrologerCallActivity.this)
                .userId(FirebaseAuth.getInstance().getUid())
                .applicationKey("9bab0172-082c-492b-8307-d576310df98f")
                .applicationSecret("koavzeBJuUm1yCaDi6TPuA==")
                .environmentHost("clientapi.sinch.com")
                .build();

        sinchClient.setSupportCalling(true);
        sinchClient.startListeningOnActiveConnection();

        requestAudioPermissions();
        
        //CallClient call1 = new CA

        sinchClient.getCallClient().addCallClientListener(new CallClientListener() {
            @Override
            public void onIncomingCall(final CallClient callClient, final Call incomingCall) {
                AlertDialog alertDialog = new AlertDialog.Builder(AstrologerCallActivity.this).create();
                alertDialog.setTitle("Incoming Call");
                call = incomingCall;


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.child("Users").child(call.getRemoteUserId()).child("name").getValue(String.class) != null)
                        {
                            callername = snapshot.child("Users").child(call.getRemoteUserId()).child("name").getValue(String.class);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });



                //changes -- time delay added

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        alertDialog.setTitle(callername + " Calling ...");
                        alertDialog.setCancelable(false);



                        //to get reciever id
                        Toast.makeText(AstrologerCallActivity.this, callername + "\n" + "Calling", Toast.LENGTH_SHORT).show();

                        String userid = call.getRemoteUserId();
                        new customdialogmodel(userid);

                        //to get reciever id

                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Reject", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                call.hangup();
                            }
                        });

                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Pick", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                call.answer();
                                call.addCallListener(new sinchCallListener());
                                status.clear();
                                status.put("isBusy", "true");
                                firebaseDatabase.getReference().child("Astrologers").child(FirebaseAuth.getInstance().getUid()).updateChildren(status);
                                Toast.makeText(AstrologerCallActivity.this, "Call is started!", Toast.LENGTH_SHORT).show();
                            }
                        });

                        alertDialog.show();

                    }
                }, 1500);
                

                //changes -- time delay added



            }
        });

        sinchClient.start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return super.onSupportNavigateUp();
    }






    private class sinchCallListener implements CallListener {

        @Override
        public void onCallProgressing(Call call) {
            Toast.makeText(AstrologerCallActivity.this, "Ringing...", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCallEstablished(Call call) {
            Toast.makeText(AstrologerCallActivity.this, "Call Established!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCallEnded(Call endedCall) {
            Toast.makeText(AstrologerCallActivity.this, "Call Ended!", Toast.LENGTH_SHORT).show();
            call = null;
            status.clear();
            status.put("isBusy", "false");
            firebaseDatabase.getReference().child("Astrologers").child(FirebaseAuth.getInstance().getUid()).updateChildren(status);
            endedCall.hangup();
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> list) {

        }
    }

    private void requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);
            }
        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
        }
    }

    //Handling callback
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "Permissions Denied to record audio", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.info:
                //Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();

                customdialogclass exampleDialog = new customdialogclass();
                exampleDialog.show(getSupportFragmentManager(), "");

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
        //databaseReference.child("Astrologers").child(uid2).child("Request").child("callstatus").setValue("false");
        databaseReference.child("Users").child(receiverUid).child("Request").child(uid2).child("callaccepted").setValue("false");

    }


}