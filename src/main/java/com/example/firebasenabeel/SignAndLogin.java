package com.example.firebasenabeel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SignAndLogin extends AppCompatActivity implements View.OnClickListener , NavigationView.OnNavigationItemSelectedListener {
    EditText email, Password;
    TextView txtView;
    boolean f = false;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    StorageReference storage;
    Bundle bundle;
    String  s;
    Intent intent;
    NavigationView navigationView;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_and_login);
        init();
        }

    private void init() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        txtView = findViewById(R.id.txt);
        findViewById(R.id.SignUp).setOnClickListener(this);
        findViewById(R.id.SignIn).setOnClickListener(this);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        email = findViewById(R.id.email_login);
        Password = findViewById(R.id.password_login);
        storage = FirebaseStorage.getInstance().getReference();
        cheakDrawable();
        database = FirebaseDatabase.getInstance("https://fir-nabeel-default-rtdb.firebaseio.com/");
        databaseReference = database.getReference();
        progressBar = findViewById(R.id.prog_signin);
        intent = new Intent();
        bundle = new Bundle();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

    }

    private void cheakDrawable() {
        Password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (Password.getRight() - Password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (!f) {
                            Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                            f = true;
                        } else {
                            Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            f = false;
                        }
                        return true;
                    }
                }
                return false;
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SignUp:
                finish();
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                break;
            case R.id.SignIn:
                SignIn();
                break;
        }
    }

    private void SignIn() {
        //Connected.IsConnect();
        //if(!Connected.isConnect)
          //  Toast.makeText(getApplicationContext(),"Error in connection ",Toast.LENGTH_LONG).show();
         if (email.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Email not found", Toast.LENGTH_LONG).show();
        else if (Password.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Password not found", Toast.LENGTH_LONG).show();
        else {
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), Password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                user = firebaseAuth.getCurrentUser();
                                s = user.getUid();
                                bundle.putString("Success",s);
                                intent.putExtras(bundle);
                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(intent);
                                } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
    @Override
    public void onBackPressed() {
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       int id = item.getItemId();
           if(id==R.id.home) {
               finish();
               Intent intent = new Intent(getApplicationContext(), MainActivity.class);
               intent.putExtras(bundle);
               startActivity(intent);
           }
        return true;
    }
}