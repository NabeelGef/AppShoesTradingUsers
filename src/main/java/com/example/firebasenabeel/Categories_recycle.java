package com.example.firebasenabeel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Categories_recycle extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterImage adapterImage;
    ArrayList <ShoesImages> shoes;
    Bundle bundle;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Upload_image_price upload_image_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_recycle);
        init();
        getShoesFromDataBase();
    }

    private void init() {
        recyclerView = findViewById(R.id.category_recycle);
        adapterImage = new AdapterImage();
        shoes = new ArrayList<>();
        upload_image_price = new Upload_image_price();
        bundle = getIntent().getExtras();
        firebaseDatabase = FirebaseDatabase.getInstance("https://fir-nabeel-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference();
    }

    private void getShoesFromDataBase() {
    databaseReference.child("InfoShoes").addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            getMoreData(dataSnapshot.getKey());
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

    private void getMoreData(String key) {
        databaseReference.child("InfoShoes").child(key).child(bundle.getString("Type")).addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            String Type = bundle.getString("Type");
            if(!dataSnapshot.getKey().equals("more")){
                upload_image_price = dataSnapshot.getValue(Upload_image_price.class);
                int  price = upload_image_price.getPrice();
                String url = upload_image_price.getImage();
                shoes.add(new ShoesImages(url,price,Type,key));
                adapterImage.setList(shoes);
                adapterImage.setIDUser(bundle.getString("IdUser"));
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(adapterImage);
                adapterImage.setLogin(bundle.getBoolean("Login"));
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

    private void getImageAndPrice(String type, String key) {
    databaseReference.child("InfoShoes").child(key).child(type).addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
}