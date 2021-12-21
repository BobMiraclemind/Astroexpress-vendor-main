package com.ashishandroid.ashishkumar.try1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Updatemobileno extends AppCompatActivity {

    private EditText mobileno;
    private Button savebutton;
    private String uid1;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference;

    ProgressDialog mDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatemobileno);

        getSupportActionBar().setTitle("Update Mobile Number");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006666")));

        mobileno = (EditText) findViewById(R.id.mobileno);
        savebutton = (Button) findViewById(R.id.savebutton);

        mDialog = new ProgressDialog(this);

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mobileno.getText().length() == 10 ){

                    mDialog.setMessage("Updating ....");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();

                    mUser = mAuth.getInstance().getCurrentUser();
                    if (mUser != null) {
                        uid1 = mUser.getUid();
                        // emailid = mUser.getEmail();

                        databaseReference = FirebaseDatabase.getInstance().getReference();

                        databaseReference.child("Astrologers").child(uid1).child("mobile").setValue(mobileno.getText().toString());


                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                startActivity(new Intent(Updatemobileno.this,SettingsFragment.class));
                                Toast.makeText(Updatemobileno.this, "Mobile Number Updated !!", Toast.LENGTH_SHORT).show();
                                mDialog.dismiss();
                            }
                        }, 1000 );//time in milisecond

                    }else{
                        Toast.makeText(Updatemobileno.this,"Error while updating , Please try again !!",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Updatemobileno.this, "Please Enter a valid Mobile Number", Toast.LENGTH_SHORT).show();
                }


            }
        });





    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onStart() {
        super.onStart();


        mUser = mAuth.getInstance().getCurrentUser();
        if (mUser != null){
            uid1 = mUser.getUid();
            // emailid = mUser.getEmail();

            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    mobileno.setText(snapshot.child("Astrologers").child(uid1).child("mobile").getValue(String.class));


                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }


    }


}