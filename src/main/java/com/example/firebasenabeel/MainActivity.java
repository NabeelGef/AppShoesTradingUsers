package com.example.firebasenabeel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import ir.drax.netwatch.NetWatch;
import ir.drax.netwatch.cb.NetworkChangeReceiver_navigator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , NavigationView.OnNavigationItemSelectedListener {
    boolean f ;
    boolean isLogin = false;
    Bundle bundle , bundle2 , bundle3 , bundle4;
    String fetch;
    Menu menu1;
    testUpload testupload;
    FirebaseMessaging firebaseMessaging;
    SharedPreferences sharedPreferences;
    FirebaseAuth mAuth;
    StorageReference storage;
    DatabaseReference databaseReference , databaseReference2 ;
    FirebaseDatabase database;
    public DrawerLayout drawerLayout;
    NavigationView navigationView;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    RecyclerView rec1  ;
    ArrayList<ShoesImages> shoes ;
    AdapterImage adapter;
    ProgressBar progressBar;
    boolean into ;
    Upload_image_price upload_image_price;
    public static final String Channel_ID = "simplified_coding";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        cheakFoundChild();
    }
    private void init() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        bundle = getIntent().getExtras();
        bundle2 = new Bundle();
        bundle3 = new Bundle();
        bundle4 = new Bundle();
        testupload = new testUpload();
        firebaseMessaging = FirebaseMessaging.getInstance();
        firebaseMessaging.subscribeToTopic("all");
        upload_image_price = new Upload_image_price();
        storage = FirebaseStorage.getInstance().getReference();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        database = FirebaseDatabase.getInstance("https://fir-nabeel-default-rtdb.firebaseio.com/");
        databaseReference = database.getReference();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        shoes = new ArrayList<>();
        databaseReference2 = database.getReference(".info/connected");
        rec1 = findViewById(R.id.recycler1);
        adapter = new AdapterImage();
        progressBar = findViewById(R.id.progHome);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("Info",MODE_PRIVATE);
    }
    private void cheakFoundChild() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("CountShoes")) {
                    getShoesFromDataBase();
                    System.out.println("There Child");
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
    public  void getShoesFromDataBase()
    {
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
                if(error.getCode()==DatabaseError.DISCONNECTED){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Error in Connection",Toast.LENGTH_LONG).show();
                }
                System.out.println("Code = " + error.getCode());
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
                if(error.getCode()==DatabaseError.DISCONNECTED){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Error in Connection",Toast.LENGTH_LONG).show();
                }
                System.out.println("Code = " + error.getCode());
            }
        });
    }

    private void getMoreData(String key) {
        databaseReference.child("InfoShoes").child(key).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String Type = snapshot.getKey();
                getPriceAndImage(key,Type);
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
                if(error.getCode()==DatabaseError.DISCONNECTED){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Error in Connection",Toast.LENGTH_LONG).show();
                }
                System.out.println("Code = " + error.getCode());

            }
        });
    }

    private void getPriceAndImage(String key ,String type) {
    databaseReference.child("InfoShoes").child(key).child(type).addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            if(!dataSnapshot.getKey().equals("more")) {
                upload_image_price = dataSnapshot.getValue(Upload_image_price.class);
                String Url = upload_image_price.getImage();
                int Price = upload_image_price.getPrice();
                shoes.add(new ShoesImages(Url, Price, type, key));
                setAdapter(shoes);
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
            if(databaseError.getCode()==DatabaseError.DISCONNECTED){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Error in Connection",Toast.LENGTH_LONG).show();
            }
            System.out.println("Code = " + databaseError.getCode());

        }
    });
    }

    private void setAdapter(ArrayList <ShoesImages> images) {
        adapter.setList(images);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        rec1.setLayoutManager(mLayoutManager);
        rec1.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notification, menu);
        menu1 = menu;
        try {
              if (bundle.getString("Success") != null) {
                  adapter.setIDUser(bundle.getString("Success"));
                  SharedPreferences.Editor editor = sharedPreferences.edit();
                  editor.putString("ID",bundle.getString("Success"));
                  editor.commit();
                menu.getItem(1).setActionView(new ProgressBar(this));
                menu.getItem(1).getActionView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        menu.getItem(1).setActionView(new ProgressBar(getApplicationContext()));
                        getImageFromDatabase(bundle.getString("Success"), menu, getApplicationContext());
                    }
                }, 1000);
                isLogin = true;
            } else {
                  throw new NullPointerException("");
            }
        } catch (NullPointerException e) {
            try {
                String test = sharedPreferences.getString("Uri" ,"");
                if (!test.equals("")) {
                        menu.getItem(1).setActionView(new ProgressBar(this));
                        menu.getItem(1).getActionView().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                menu.getItem(1).setActionView(new ProgressBar(getApplicationContext()));
                                getDataFromSharedPrefrences( menu, getApplicationContext());
                            }
                        }, 1000);
                        isLogin = true;
                    }
                else{
                    menu.getItem(1).setIcon(R.drawable.ic_baseline_person_24);
                }
            } catch (Exception e1) {
                menu.getItem(1).setIcon(R.drawable.ic_baseline_person_24);
            }
        }
        adapter.setLogin(isLogin);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (id == R.id.notification)
        {
            Intent intent = new Intent(getApplicationContext(), Get_Notification.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.ClickProfile) {
            if (!isLogin) {
                finish();
                Intent intent2 = new Intent(getApplicationContext(), SignAndLogin.class);
                startActivity(intent2);
            } else {
                finish();
                Intent intent2 = new Intent(getApplicationContext(), Profile.class);
                if(bundle2!=null)
                {
                    intent2.putExtras(bundle2);
                }
                startActivity(intent2);
            }
            return true;
        }
        if(id==R.id.order){
            if(!isLogin)
            {
                Toast.makeText(getApplicationContext(),"You are not register yet!!",Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent3 = new Intent(getApplicationContext(), Orders.class);
                if (!sharedPreferences.getString("Name","").equals("")) {
                    bundle3.putString("Name", sharedPreferences.getString("Name",""));
                    try {
                        String id2 = sharedPreferences.getString("ID","");
                        bundle3.putString("ID",id2);
                    }catch (Exception e){
                        System.out.println("Error " + e.getMessage());
                    }
                    intent3.putExtras(bundle3);
                }
                startActivity(intent3);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {
    }
    @Override
    public void onBackPressed() {
        f = false;
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("are you sure exit app !!");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    public void getImageFromDatabase(String s, Menu menu, Context context) {
        if (isLogin) {
            adapter.setIDUser(sharedPreferences.getString("ID",""));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            databaseReference.child("Profiles").child(s).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                   testupload = dataSnapshot.getValue(testUpload.class);
                    String Name = testupload.getName();
                    String NumberTel = testupload.getNumberTel();
                    fetch = testupload.getUri();
                    Glide.with(context).load(fetch).into(new SimpleTarget<Drawable>() {
                                                             @Override
                                                             public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                                                 menu.getItem(1).setActionView(null);
                                                                 menu.getItem(1).setIcon(resource);
                                                             }
                                                         });
                    editor.putString("Name",Name);
                    editor.putString("NumberTel",NumberTel);
                    editor.putString("Uri",fetch);
                    bundle2.putString("img",fetch);
                    editor.commit();
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
    private void getDataFromSharedPrefrences(Menu menu, Context context) {

        Glide.with(context).load(sharedPreferences.getString("Uri","")).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                menu.getItem(1).setActionView(null);
                menu.getItem(1).setIcon(resource);
            }
        });
        bundle2.putString("img",sharedPreferences.getString("Uri",""));
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("are you sure logout !!");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            isLogin = false;
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Uri","");
                            editor.putString("Name","");
                            editor.putString("NumberTel","");
                            editor.putString("ID","");
                            editor.commit();
                            menu1.getItem(1).setIcon(R.drawable.ic_baseline_person_24);
                            dialog.cancel();
                            adapter.setLogin(isLogin);
                            adapter.setIDUser("");
                        }
                    });
            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        else if(item.getItemId() == R.id.aboutUs)
        {
            finish();
            Intent intent = new Intent(getApplicationContext(), InfoUser.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.category){
            bundle4.putBoolean("Login",isLogin);
            bundle4.putString("IdUser",adapter.getIDUser());
            Intent intent = new Intent(getApplicationContext(),Categories.class);
            intent.putExtras(bundle4);
            startActivity(intent);
       }
        return false;
    }
}