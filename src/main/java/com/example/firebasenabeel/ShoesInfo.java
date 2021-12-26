package com.example.firebasenabeel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShoesInfo extends AppCompatActivity implements BackGround_color {
FirebaseDatabase database;
DatabaseReference databaseReference;
Bundle bundle;
testUpload upload;
int size,prc , buyCount , test;
boolean Into;
String color , url , IDUser;
AdapterSize adapter;
FcmSender fcmSender;
AdapterColor adapter2;
RecyclerView recyclerView , recyclerView2;
Upload_image_price upload_image_price;
ArrayList<Integer> sizes;
ArrayList<String> colors;
TextView price , Type , Made , description;
ImageView itemPhoto;
DataBuying dataBuying;
CircularProgressDrawable circularProgressDrawable;
Button Buy ;
String type;
boolean[] selected_size = new boolean[32];
boolean[] selected_color = new boolean[32];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoes_info);
        init();
        getDataFromDataBase();
        BuyShoes();
    }
    private void BuyShoes() {
        Into = false;
        Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(!bundle.getBoolean("Login"))
                 Toast.makeText(getApplicationContext(),"You aren't registered yet",
                         Toast.LENGTH_SHORT).show();
             else if (!is_Size()){
                 Toast.makeText(getApplicationContext(),"You aren't choose size yet",
                         Toast.LENGTH_SHORT).show();
             }
             else if (color.equals("")){
                 Toast.makeText(getApplicationContext(),"You aren't choose color yet",
                         Toast.LENGTH_SHORT).show();
             }
             else{
                 fcmSender = new FcmSender("/topics/all","BuyShoes","The Type is : " +
                         type+" and size is : " + size,getApplicationContext(),ShoesInfo.this);
                 fcmSender.SendNotifications();
                 IDUser = bundle.getString("UserId");
                 databaseReference.child("Orders").child(IDUser).addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         buyCount = dataSnapshot.getValue(Integer.class);
                         if(!Into) {
                             test = buyCount;
                             dataBuying.setPrice(prc);
                             dataBuying.setUrl(url);
                             dataBuying.setColor(color);
                             dataBuying.setSize(size);
                             databaseReference.child("Profiles").child(IDUser).addChildEventListener(new ChildEventListener() {
                                 @Override
                                 public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                  upload = dataSnapshot.getValue(testUpload.class);
                                  dataBuying.setName(upload.getName());
                                     databaseReference.child("Customers").child(IDUser).child(String.valueOf(test))
                                             .push().setValue(dataBuying);
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
                             test++;
                             databaseReference.child("Orders").child(IDUser).setValue(test);
                             Into = true;
                         }
                     }
                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {
                     }
                 });
                 Toast.makeText(getApplicationContext(),"Buying succeeded!!",
                         Toast.LENGTH_SHORT).show();
                 Buy.setClickable(false);
             }
            }
        });
    }
    private boolean is_Size() {
        for(int i=0;i<sizes.size();i++)
            if(selected_size[i])
            {
                size = sizes.get(i);
                System.out.println("i = " + i + "Sizes = " + sizes.get(i));
                return true;
            }
        return false;
    }
    private void getDataFromDataBase() {
        databaseReference.child("InfoShoes").child(bundle.getString("ID")).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Type.append(dataSnapshot.getKey());
                type = dataSnapshot.getKey();
                getImageAndPrice(type,bundle.getString("ID"));
                Made.append(" "+dataSnapshot.child("more").child("Made").getValue(String.class));
                description.append(" "+dataSnapshot.child("more").child("description").getValue(String.class));
                for (DataSnapshot object : dataSnapshot.child("more").child("Sizes").getChildren()) {
                    sizes.add(object.getValue(Integer.class));
                }
                setAdapterSize();
                for (DataSnapshot object : dataSnapshot.child("more").child("colors").getChildren()) {
                    colors.add(object.getValue(String.class));
                }
                setAdapterColor();
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
    private void getImageAndPrice(String type, String id) {
    databaseReference.child("InfoShoes").child(id).child(type).addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            if(!dataSnapshot.getKey().equals("more")){
                upload_image_price = dataSnapshot.getValue(Upload_image_price.class);
                price.append(" "+upload_image_price.getPrice()+" SYR");
                prc = upload_image_price.getPrice();
                url = upload_image_price.getImage();
                Glide.with(getApplicationContext()).load(upload_image_price.getImage())
                        .into(itemPhoto);
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
    private void setAdapterColor() {
        adapter2.setArrayList(colors,(BackGround_color)this);
        RecyclerView.LayoutManager rec = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(rec);
        recyclerView2.setAdapter(adapter2);
    }
    private void setAdapterSize() {
     adapter.setArrayList(sizes,(BackGround_color)this);
     RecyclerView.LayoutManager rec = new LinearLayoutManager(getApplicationContext(),
             LinearLayoutManager.HORIZONTAL,false);
   recyclerView.setLayoutManager(rec);
   recyclerView.setAdapter(adapter);
    }
    private void init()
    {
    database = FirebaseDatabase.getInstance("https://fir-nabeel-default-rtdb.firebaseio.com/");
    databaseReference = database.getReference();
    bundle = new Bundle();
    bundle = getIntent().getExtras();
    price = findViewById(R.id.DataPrice);
    Type = findViewById(R.id.DataCategory);
    Made = findViewById(R.id.DataMade);
    sizes = new ArrayList<>();
    colors = new ArrayList<>();
    adapter = new AdapterSize();
    dataBuying = new DataBuying();
    adapter2 = new AdapterColor();
    for(int i=0;i<31;i++){
        selected_size[i] = false;
        selected_color[i] = false;
    }
    upload = new testUpload();
    Buy = findViewById(R.id.BuyShoes);
    itemPhoto = findViewById(R.id.DataImage);
    recyclerView = findViewById(R.id.recyclersize);
    recyclerView2 = findViewById(R.id.recyclercolor);
    description = findViewById(R.id.DataDescription);
    upload_image_price = new Upload_image_price();
    circularProgressDrawable = new CircularProgressDrawable(this);
        circularProgressDrawable.setStartEndTrim(5,300);
        circularProgressDrawable.start();
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
    @Override
    public void button_onClick_Size(View view, int pos, String s) {
        if(s.equals("one")){
            selected_size[pos] = false;
            view.setBackground(getResources().getDrawable(R.drawable.circle));
        }
        else{
            selected_size[pos] = true;
            view.setBackground(getResources().getDrawable(R.drawable.circle2));
        }
    }
    @Override
    public void button_onClick_Color(View view, int pos, String s) {
        if(s.equals("one")){
            selected_color[pos] = false;
            color = "";
            view.setBackground(getResources().getDrawable(R.drawable.circle));
        }
        else{
            selected_color[pos] = true;
            color = colors.get(pos);
            view.setBackground(getResources().getDrawable(R.drawable.circle2));
        }
    }

}