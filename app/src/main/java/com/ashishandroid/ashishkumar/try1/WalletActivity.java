package com.ashishandroid.ashishkumar.try1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ashishandroid.ashishkumar.Adapters.chathistoryadapter;
import com.ashishandroid.ashishkumar.ModelClass.chathistorymodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class WalletActivity extends AppCompatActivity {

    TextView walletb;

    DatabaseReference databaseReference, databaseReference1;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    String uid, user_uid, taxValue;
    double totalm;
    TextView taxValueView;
    String name , paid, astroct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        getSupportActionBar().setTitle("Wallet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006666")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        walletb = findViewById(R.id.walletb);
        taxValueView = findViewById(R.id.taxTotalValue);




        databaseReference1 = FirebaseDatabase.getInstance().getReference();
        mUser = mAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            uid = mUser.getUid();

            databaseReference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot2) {

                    astroct = snapshot2.child("Astrologers").child(uid).child("chatprice").getValue(String.class);

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });




            databaseReference = FirebaseDatabase.getInstance().getReference().child("Astrologers").child(uid).child("Payments");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Double total_tax = 0.0;
                    for (final DataSnapshot dsp : snapshot.getChildren()) {

                        if (dsp.child("uname").getValue(String.class) != null &&
                                dsp.child("wallet").getValue() != null) {

                            user_uid = dsp.getKey();

                            name = dsp.child("uname").getValue(String.class);
                            paid = dsp.child("wallet").getValue().toString();
                            DecimalFormat df = new DecimalFormat("0.00");

                            // String chattime = df.format((Double.parseDouble(paid) / Double.parseDouble(astroct)));

                            String tds1 = df.format(((10 / 100) * Double.parseDouble(paid)));
                            String paygate = df.format(((1.25 / 100) * Double.parseDouble(paid)));

                            total_tax += (Double.parseDouble(tds1) + Double.parseDouble(paygate));

                            double leftmoney = Double.parseDouble(paid) - Double.parseDouble(tds1) - Double.parseDouble(paygate);
                            String admin = df.format(leftmoney / 2);
                            String astro = df.format(leftmoney / 2);
                            totalm = totalm + Double.parseDouble(admin);

                        }
                    }
                    taxValueView.setText(String.valueOf(total_tax   ));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });


            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    DecimalFormat df = new DecimalFormat("0.00");
                    databaseReference1.child("Astrologers").child(uid).child("wallet").setValue("" + df.format(totalm));

                }
            }, 1000);


        }




        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot2) {

                String wallettotal = snapshot2.child("Astrologers").child(uid).child("wallet").getValue(String.class);

                walletb.setText("Rs " + wallettotal);

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
                startActivity(new Intent(getApplicationContext(), WalletHistory.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}