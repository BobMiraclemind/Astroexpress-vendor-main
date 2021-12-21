package com.ashishandroid.ashishkumar.try1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ProgressDialog progressDialog;

    private String mParam1;
    private String mParam2;
    TextView name, email;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference, databaseReference1;
    String uid = "", displayname = "", emailid = "", walletvendor, link;


    CardView cardView, chat_card, call_card, my_reviews_card, offers_card, suggestedRemedies_card, reports_card, query_card;
    ImageView status_act, profileimg, wallet;

    public HomeFragment() {

    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        name = (TextView) view.findViewById(R.id.displayn);
        email = (TextView) view.findViewById(R.id.email);
        cardView = (CardView) view.findViewById(R.id.settings_card);
        chat_card = (CardView) view.findViewById(R.id.chat_card);
        status_act = (ImageView) view.findViewById(R.id.status_act);
        wallet = (ImageView) view.findViewById(R.id.wallet);
        call_card = (CardView) view.findViewById(R.id.call_card);
        my_reviews_card = (CardView) view.findViewById(R.id.my_reviews_card);
        offers_card = (CardView) view.findViewById(R.id.offers_card);
        suggestedRemedies_card = (CardView) view.findViewById(R.id.suggestedRemedies_card);
        reports_card = (CardView) view.findViewById(R.id.reports_card);
        query_card = (CardView) view.findViewById(R.id.query_card);


        // progress bar

        progressDialog = new ProgressDialog(getContext());

        if (name.getText().equals("User Name")) {

            if (progressDialog == null) {
                progressDialog.setMessage("Loading Please Wait...");
                progressDialog.setIndeterminate(true);
                progressDialog.show();
            }

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            }, 3700);


        }

        // progress bar


        profileimg = (ImageView) view.findViewById(R.id.profileimg);

        mUser = mAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            uid = mUser.getUid();
            // emailid = mUser.getEmail();

            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    displayname = snapshot.child("Astrologers").child(uid).child("displayname").getValue(String.class);
                    emailid = snapshot.child("Astrologers").child(uid).child("email").getValue(String.class);

                    walletvendor = snapshot.child("Astrologers").child(uid).child("wallet").getValue(String.class);

                    if (walletvendor == null) {

                        databaseReference.child("Astrologers").child(uid).child("wallet").setValue("0");

                    }

                    name.setText(displayname);
                    email.setText(emailid);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }


        // for image

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Astrologers").child(uid);
        storageReference.child("Profile_Image.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                link = uri.toString();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {

                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Astrologers").child(uid).child("profilepic").setValue("" + link);
//
            }
        }, 4000);


        // for image

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingsFragment.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        chat_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), userslist.class);
                startActivity(intent);
            }
        });

        status_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StatusActivity.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Profilepagelist.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });


        call_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Page Under Construction !!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), userslistcall.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);


            }
        });

        my_reviews_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReviewActivity.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        offers_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Offers.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);

            }
        });

        suggestedRemedies_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Page Under Construction !!", Toast.LENGTH_SHORT).show();
            }
        });

        reports_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Page Under Construction !!", Toast.LENGTH_SHORT).show();
            }
        });

        query_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), query.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);

            }
        });

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });
        // Inflate the layout for this fragment

        return view;

    }



}