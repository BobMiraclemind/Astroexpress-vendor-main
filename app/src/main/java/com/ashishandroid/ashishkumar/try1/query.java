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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class query extends AppCompatActivity {

    private EditText astro;
    private TextView admin;
    Button submitq;
    String uid12;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        getSupportActionBar().setTitle("Query");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006666")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        astro = (EditText) findViewById(R.id.astro);
        admin = (TextView) findViewById(R.id.admin);
        submitq = (Button) findViewById(R.id.submitq);


        mUser = mAuth.getInstance().getCurrentUser();
        if (mUser != null){
            uid12 = mUser.getUid();

            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    admin.setText(snapshot.child("query").child("astro-admin").child(uid12).child("queriesadmin121").child("querymessage").getValue(String.class));

                    if (astro.getText()!= null) {
                        astro.setText(snapshot.child("query").child("astro-admin").child(uid12).child("queriesastro121").child("querymessage").getValue(String.class));
                    }


                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }


        final ProgressDialog mDialog = new ProgressDialog(this);

        submitq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDialog.setMessage("Submitting Query ....");
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();

                mUser = mAuth.getInstance().getCurrentUser();
                if (mUser != null || astro.getText() != null) {
                    uid12 = mUser.getUid();

                    databaseReference = FirebaseDatabase.getInstance().getReference();

                    databaseReference.child("query").child("astro-admin").child(uid12).child("queriesastro121").child("querymessage").setValue(astro.getText().toString());
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            startActivity(new Intent(query.this,DashboardActivity.class));
                            Toast.makeText(query.this, "Query Submitted", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    }, 1000 );//time in milisecond
                }else{
                    Toast.makeText(query.this,"Error while submitting query, Please try again !!",Toast.LENGTH_SHORT).show();
                }


            }

        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}