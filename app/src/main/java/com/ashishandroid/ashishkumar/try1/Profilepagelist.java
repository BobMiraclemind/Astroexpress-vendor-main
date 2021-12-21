package com.ashishandroid.ashishkumar.try1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Profilepagelist extends AppCompatActivity {

    private Button editprofile, chathistory, callhistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepagelist);

        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006666")));

        editprofile = (Button) findViewById(R.id.editprofile);
        chathistory = (Button) findViewById(R.id.chathistory);
        callhistory = (Button) findViewById(R.id.callhistory);

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), Profile.class));

            }
        });

        chathistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //chathistory.setText("History deleted");

                 startActivity(new Intent(getApplicationContext(), ChatHistory.class));

            }
        });



        callhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //chathistory.setText("History deleted");

                startActivity(new Intent(getApplicationContext(), CallHistory.class));

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


}