package com.ashishandroid.ashishkumar.try1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends AppCompatActivity {

    private Button logout;
    private Button mobileno, tandc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_fragment);

        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006666")));

        logout = (Button) findViewById(R.id.logout);
        mobileno = (Button) findViewById(R.id.mobileno);
        tandc = (Button) findViewById(R.id.termsandconditions);

        mobileno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Updatemobileno.class));
            }
        });

        tandc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),TermsAndCondition.class));
            }
        });




        final ProgressDialog mDialog = new ProgressDialog(this);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AlertDialog.Builder(SettingsFragment.this)
                        .setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                mDialog.setMessage("Logging out ....");
                                mDialog.setCanceledOnTouchOutside(false);
                                mDialog.show();

                                new Handler().postDelayed(new Runnable() {

                                    @Override
                                    public void run() {

                                        FirebaseAuth.getInstance().signOut();
                                        Toast.makeText(SettingsFragment.this,"Log Out Successfull", Toast.LENGTH_LONG).show();
                                        Intent intent=new Intent(SettingsFragment.this,login.class);
                                        startActivity(intent);
                                        mDialog.dismiss();
                                    }
                                }, 1500 );


                            }
                        })
                        .setNegativeButton("No", null).show();





            }
        });

    }



}
