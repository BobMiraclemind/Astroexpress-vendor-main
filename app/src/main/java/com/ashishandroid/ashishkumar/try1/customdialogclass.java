package com.ashishandroid.ashishkumar.try1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ashishandroid.ashishkumar.ModelClass.customdialogmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class customdialogclass extends AppCompatDialogFragment {
    private TextView nameu, genderu, dobu, pobu, tobu;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference;

    String a;

    private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        a = customdialogmodel.getUid_users();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.customview, null);
        nameu = view.findViewById(R.id.nameu);
        genderu = view.findViewById(R.id.genderu);
        dobu = view.findViewById(R.id.dobu);
        pobu = view.findViewById(R.id.pobu);
        tobu = view.findViewById(R.id.tobu);


        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null && a != null && !a.equals("")) {

            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if ((snapshot.child("Users").child(a).child("name").getValue(String.class)) != null &&
                            (snapshot.child("Users").child(a).child("gen").getValue(String.class)) != null &&
                            (snapshot.child("Users").child(a).child("dob").getValue(String.class)) != null &&
                            (snapshot.child("Users").child(a).child("pob").getValue(String.class)) != null &&
                            (snapshot.child("Users").child(a).child("tob").getValue(String.class)) != null) {

                        nameu.setText(snapshot.child("Users").child(a).child("name").getValue(String.class));
                        genderu.setText(snapshot.child("Users").child(a).child("gen").getValue(String.class));
                        dobu.setText(snapshot.child("Users").child(a).child("dob").getValue(String.class));
                        pobu.setText(snapshot.child("Users").child(a).child("pob").getValue(String.class));
                        tobu.setText(snapshot.child("Users").child(a).child("tob").getValue(String.class));
                    } else {
                        Toast.makeText(getContext(), "Details Not Available", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else {
            Toast.makeText(getContext(), "Wait for the call...", Toast.LENGTH_SHORT).show();
        }


        builder.setView(view).setTitle("");

        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String username, String password);
    }
}