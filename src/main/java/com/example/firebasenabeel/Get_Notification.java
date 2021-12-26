package com.example.firebasenabeel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Get_Notification extends AppCompatActivity {
RecyclerView recyclerView;
DatabaseReference databaseReference;
ProgressBar progressBar;
FirebaseDatabase firebaseDatabase;
Upload_image_price upload_image_price;
ArrayList<Upload_price_type> arrayList;
Adapter_Notification adapter_notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        init();
        cheakFoundChild();
    }

    private void cheakFoundChild() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("CountShoes")) {
                    getShoesFromDataBase();
                } else {
                    Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getShoesFromDataBase() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("CountShoes").getValue(Integer.class) > 0) {
                    getData();
                } else {
                    Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getData() {
        databaseReference.child("InfoShoes").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                getMoreData(snapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getMoreData(String key) {
        databaseReference.child("InfoShoes").child(key).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String Type = snapshot.getKey();
                getPrice(key,Type);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getPrice(String key, String type) {
        databaseReference.child("InfoShoes").child(key).child(type).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(!dataSnapshot.getKey().equals("more")) {
                    upload_image_price = dataSnapshot.getValue(Upload_image_price.class);
                    int Price = upload_image_price.getPrice();
                    arrayList.add(new Upload_price_type(type,Price));
                    adapter_notification.setList(arrayList);
                    setAdapter();
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter_notification);
        progressBar.setVisibility(View.GONE);
    }
    private void init() {
        firebaseDatabase = FirebaseDatabase.getInstance("https://fir-nabeel-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference();
        recyclerView = findViewById(R.id.rec_notify);
        progressBar = findViewById(R.id.Prog_notify);
        arrayList = new ArrayList<>();
        adapter_notification = new Adapter_Notification();
    }
}