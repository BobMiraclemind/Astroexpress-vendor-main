package com.ashishandroid.ashishkumar.try1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    public static final String TAG="LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getSupportActionBar().hide();

        Button button = findViewById(R.id.button);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager manager = (ConnectivityManager)
                        getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = manager.getActiveNetworkInfo();


                if(null != activeNetwork){

                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
                        Toast.makeText(getApplicationContext(), "Wifi Enabled", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StartActivity.this, login.class);
                        startActivity(intent);
                        finish();
                    }

                    else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
                        Toast.makeText(getApplicationContext(), "Data Network Enabled", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StartActivity.this, login.class);
                        startActivity(intent);
                        finish();
                    }

                }

                else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection Available", Toast.LENGTH_SHORT).show();
                }


            }
        });










    }




}