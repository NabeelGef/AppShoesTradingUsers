package com.example.firebasenabeel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Categories extends AppCompatActivity implements View.OnClickListener {
    Bundle bundle , bundle2 ;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    Upload_image_price upload_image_price;
    ImageView men , women , girl , boy , batik , baby ;
    boolean IsMen = false, IsWomen = false , IsGirl = false , IsBoy = false  , IsBatik = false,
            IsBaby = false ;
    ProgressBar progMen , progWomen , progBoy , progGirl , progBaby , progbatik ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        men = findViewById(R.id.men);
        women = findViewById(R.id.women);
        boy = findViewById(R.id.boy);
        girl = findViewById(R.id.girl);
        baby = findViewById(R.id.baby);
        batik = findViewById(R.id.batic);
        progMen = findViewById(R.id.ProgressMen);
        progWomen = findViewById(R.id.ProgressWomen);
        progBoy = findViewById(R.id.ProgressBoy);
        progGirl = findViewById(R.id.ProgressGirl);
        progBaby = findViewById(R.id.ProgressBaby);
        progbatik = findViewById(R.id.ProgressBatik);
        init();
        getDataBase();
    }

    private void getDataBase() {
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
    databaseReference.child("InfoShoes").child(key).child("Men").addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            if(!dataSnapshot.getKey().equals("more")&&!IsMen) {
                upload_image_price = dataSnapshot.getValue(Upload_image_price.class);
                String url = upload_image_price.getImage();
                Glide.with(getApplicationContext()).load(url).into(men);
                IsMen = true;
                progMen.setVisibility(View.GONE);
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
        databaseReference.child("InfoShoes").child(key).child("Boy").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(!dataSnapshot.getKey().equals("more")&&!IsBoy) {
                    upload_image_price = dataSnapshot.getValue(Upload_image_price.class);
                    String url = upload_image_price.getImage();
                    Glide.with(getApplicationContext()).load(url).into(boy);
                    IsBoy = true;
                    progBoy.setVisibility(View.GONE);
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
        databaseReference.child("InfoShoes").child(key).child("Women").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(!dataSnapshot.getKey().equals("more")&&!IsWomen) {
                    upload_image_price = dataSnapshot.getValue(Upload_image_price.class);
                    String url = upload_image_price.getImage();
                    Glide.with(getApplicationContext()).load(url).into(women);
                    IsWomen = true;
                    progWomen.setVisibility(View.GONE);
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
        databaseReference.child("InfoShoes").child(key).child("Girl").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(!dataSnapshot.getKey().equals("more")&&!IsGirl) {
                    upload_image_price = dataSnapshot.getValue(Upload_image_price.class);
                    String url = upload_image_price.getImage();
                    Glide.with(getApplicationContext()).load(url).into(girl);
                    IsGirl = true;
                    progGirl.setVisibility(View.GONE);
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
        databaseReference.child("InfoShoes").child(key).child("Batik").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(!dataSnapshot.getKey().equals("more")&&!IsBatik) {
                    upload_image_price = dataSnapshot.getValue(Upload_image_price.class);
                    String url = upload_image_price.getImage();
                    Glide.with(getApplicationContext()).load(url).into(batik);
                    IsBatik = true;
                    progbatik.setVisibility(View.GONE);
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
        databaseReference.child("InfoShoes").child(key).child("Baby").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(!dataSnapshot.getKey().equals("more")&&!IsBaby) {
                    upload_image_price = dataSnapshot.getValue(Upload_image_price.class);
                    String url = upload_image_price.getImage();
                    Glide.with(getApplicationContext()).load(url).into(baby);
                    IsBaby = true;
                    progBaby.setVisibility(View.GONE);
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

    private void init() {
        bundle = new Bundle();
        upload_image_price = new Upload_image_price();
        firebaseDatabase = FirebaseDatabase.getInstance("https://fir-nabeel-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference();
        bundle2 = getIntent().getExtras();
        findViewById(R.id.men).setOnClickListener(this);
        findViewById(R.id.women).setOnClickListener(this);
        findViewById(R.id.boy).setOnClickListener(this);
        findViewById(R.id.girl).setOnClickListener(this);
        findViewById(R.id.batic).setOnClickListener(this);
        findViewById(R.id.baby).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
          if(v.getId()==R.id.men){
              recycle_Categories("Men");
          }else if(v.getId()==R.id.women){
            recycle_Categories("Women");
        }else if(v.getId()==R.id.boy){
              recycle_Categories("Boy");
          }else if(v.getId()==R.id.girl){
              recycle_Categories("Girl");
          }else if(v.getId()==R.id.batic){
              recycle_Categories("Batik");
          }else if(v.getId()==R.id.baby){
              recycle_Categories("Baby");
          }
    }

    private void recycle_Categories(String s) {
        bundle.putString("Type",s);
        bundle.putString("IdUser",bundle2.getString("IdUser"));
        bundle.putBoolean("Login",bundle2.getBoolean("Login"));
        Intent intent = new Intent(getApplicationContext(),Categories_recycle.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
