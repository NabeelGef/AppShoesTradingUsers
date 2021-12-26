package com.example.firebasenabeel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class Orders extends AppCompatActivity {
  RecyclerView recyclerView;
  ArrayList <DataBuying> dataBuyings;
  AdapterOrders adapterOrders;
  DataBuying dataBuying;
  Bundle bundle;
    int n = 0;
  int Price , Size ;
  String color , url , Name;
  FirebaseDatabase firebaseDatabase;
  DatabaseReference databaseReference;
  ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        recyclerView = findViewById(R.id.recyclerOrder);
        dataBuyings = new ArrayList<>();
        adapterOrders = new AdapterOrders();
        bundle = new Bundle();
        progressBar = findViewById(R.id.Prog_Order);
        bundle = getIntent().getExtras();
        dataBuying = new DataBuying();
        firebaseDatabase = FirebaseDatabase.getInstance("https://fir-nabeel-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference();
        try{
            System.out.println("ID = " + bundle.getString("ID"));
            databaseReference.child("Customers").child(bundle.getString("ID"))
                    .addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                getData(bundle.getString("ID"),dataSnapshot.getKey());
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
        catch (Exception e)
        {
            System.out.println("Error is : " + e.getMessage());
        }

    }

    private void getData(String ID , String key) {
    databaseReference.child("Customers").child(ID).child(key).addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
         dataBuying = dataSnapshot.getValue(DataBuying.class);
            color = dataBuying.getColor();
            Price = dataBuying.getPrice();
            Size = dataBuying.getSize();
            url = dataBuying.getUrl();
            Name = dataBuying.getName();
            dataBuyings.add(new DataBuying(Price, Size, color, url, Name));
            adapterOrders.setList(dataBuyings);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
            recyclerView.setAdapter(adapterOrders);
            progressBar.setVisibility(View.GONE);
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
}
