package com.ashishandroid.ashishkumar.try1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    EditText name, longbio, address, dob, skills, shortbio, account, aadhaar, chatprice, callp;
    String uid;
    Button savebutton;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference;

    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006666")));


        name = (EditText) findViewById(R.id.editName);
        longbio = (EditText) findViewById(R.id.editlongbio);
        address = (EditText) findViewById(R.id.editaddress);
        dob = (EditText) findViewById(R.id.editdate);
        skills = (EditText) findViewById(R.id.editskills);
        shortbio = (EditText) findViewById(R.id.editshortbio);
        account = (EditText) findViewById(R.id.editaccount);
        aadhaar = (EditText) findViewById(R.id.editaadhaar);
        callp = (EditText) findViewById(R.id.callprice);
        chatprice = (EditText) findViewById(R.id.chatprice);
        savebutton = (Button) findViewById(R.id.savebutton);
        mDialog = new ProgressDialog(this);


        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDialog.setMessage("Updating Details ....");
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();

                mUser = mAuth.getInstance().getCurrentUser();
                if (mUser != null) {
                    uid = mUser.getUid();
                    // emailid = mUser.getEmail();

                    databaseReference = FirebaseDatabase.getInstance().getReference();

                    databaseReference.child("Astrologers").child(uid).child("displayname").setValue(name.getText().toString());
                    databaseReference.child("Astrologers").child(uid).child("longbio").setValue(longbio.getText().toString());
                    databaseReference.child("Astrologers").child(uid).child("address").setValue(address.getText().toString());
                    databaseReference.child("Astrologers").child(uid).child("dateOfBirth").setValue(dob.getText().toString());
                    databaseReference.child("Astrologers").child(uid).child("skills").setValue(skills.getText().toString());
                    databaseReference.child("Astrologers").child(uid).child("shortbio").setValue(shortbio.getText().toString());
                    databaseReference.child("Astrologers").child(uid).child("account").setValue(account.getText().toString());
                    databaseReference.child("Astrologers").child(uid).child("aadhaar").setValue(aadhaar.getText().toString());
                    databaseReference.child("Astrologers").child(uid).child("chatprice").setValue(chatprice.getText().toString());
                    databaseReference.child("Astrologers").child(uid).child("callprice").setValue(callp.getText().toString());


                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            startActivity(new Intent(Profile.this, Profilepagelist.class));
                            Toast.makeText(Profile.this, "Profile Updated !!", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    }, 1000);//time in milisecond
                } else {
                    Toast.makeText(Profile.this, "Error while saving details, Please try again !!", Toast.LENGTH_SHORT).show();
                }

            }

        });


    }


    @Override
    public void onStart() {
        super.onStart();


        mUser = mAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            uid = mUser.getUid();

            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    name.setText(snapshot.child("Astrologers").child(uid).child("displayname").getValue(String.class));
                    longbio.setText(snapshot.child("Astrologers").child(uid).child("longbio").getValue(String.class));
                    address.setText(snapshot.child("Astrologers").child(uid).child("address").getValue(String.class));
                    dob.setText(snapshot.child("Astrologers").child(uid).child("dateOfBirth").getValue(String.class));
                    skills.setText(snapshot.child("Astrologers").child(uid).child("skills").getValue(String.class));
                    shortbio.setText(snapshot.child("Astrologers").child(uid).child("shortbio").getValue(String.class));
                    account.setText(snapshot.child("Astrologers").child(uid).child("account").getValue(String.class));
                    aadhaar.setText(snapshot.child("Astrologers").child(uid).child("aadhaar").getValue(String.class));
                    chatprice.setText(snapshot.child("Astrologers").child(uid).child("chatprice").getValue(String.class));
                    callp.setText(snapshot.child("Astrologers").child(uid).child("callprice").getValue(String.class));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


}