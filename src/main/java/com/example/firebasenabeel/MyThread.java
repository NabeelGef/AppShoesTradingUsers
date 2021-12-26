package com.example.firebasenabeel;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;

public class MyThread extends Thread {
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    boolean isConnect;
    public MyThread( DatabaseReference databaseReference, FirebaseDatabase database, boolean isConnect) {
        this.databaseReference = databaseReference;
        this.database = database;
        this.isConnect = isConnect;
    }
    @Override
    public void run() {
        databaseReference = database.getReference(".info/connected");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    isConnect = dataSnapshot.getValue(Boolean.class);
                    System.out.println("Connected1 = " + isConnect);
                } catch (NullPointerException e) {
                    System.out.println("Error = " + e.getMessage());
                }
                if (!isConnect) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            isConnect = dataSnapshot.getValue(Boolean.class);
                            System.out.println("Connect2 = " + isConnect);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public boolean isConnect() {
        return isConnect;
    }
}
