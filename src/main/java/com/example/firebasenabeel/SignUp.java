package com.example.firebasenabeel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.CommonNotificationBuilder;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SignUp extends AppCompatActivity implements View.OnClickListener , Serializable {
    FirebaseAuth firebaseAuth;
    FileOutputStream fileOutputStream;
    ObjectOutputStream objectOutputStream;
    FirebaseDatabase database;
    testUpload Test;
    FirebaseUser user;
    String s;
    SharedPreferences sharedPreferences;
    DatabaseReference myRef;
    StorageReference storage;
    TextView textView , txt;
    Uri uri;
    boolean sendName = false , sendNum = false;
    Bundle bundle;
    Bundle imgProfile;
    EditText name, password, email, numbertel;
    ProgressBar progressBar;
    ImageView imageView;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init(); // initialize variables //
        }

    private void init() {
        Test = new testUpload();
        findViewById(R.id.Save).setOnClickListener(this);
        findViewById(R.id.yourPhoto).setOnClickListener(this);
        name = findViewById(R.id.Name);
        textView = findViewById(R.id.yourPhoto);
        txt = findViewById(R.id.txt2);
        imageView = findViewById(R.id.imagePhoto);
        storage = FirebaseStorage.getInstance().getReference();
        password = findViewById(R.id.Password);
        email = findViewById(R.id.Email);
        bundle = new Bundle();
        sharedPreferences = getSharedPreferences("Info",MODE_PRIVATE);
        intent = new Intent(getApplicationContext(),MainActivity.class);
        numbertel = findViewById(R.id.TelNumber);
        progressBar = findViewById(R.id.progress);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://fir-nabeel-default-rtdb.firebaseio.com/");
        myRef = database.getReference();
        imgProfile = new Bundle();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Save:
                Signup(); // to sign up  your account //
                break;
            case R.id.yourPhoto:
                // to make your photo account //
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
                break;
        }
    }

    private void Signup() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //Connected.IsConnect();
        //if(!Connected.isConnect)
          //  Toast.makeText(getApplicationContext(),"Error in connection",Toast.LENGTH_LONG).show();
         if (name.getText().equals("")) {
            Toast.makeText(getApplicationContext(), "Name is empty", Toast.LENGTH_LONG).show();
        } else if (numbertel.getText().equals("")) {
            Toast.makeText(getApplicationContext(), "Telephone Number is empty", Toast.LENGTH_LONG).show();
        } else if (uri == null) {
            Toast.makeText(getApplicationContext(), "image not found", Toast.LENGTH_LONG).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user = firebaseAuth.getCurrentUser();
                                s = user.getUid();
                                /*myRef.child("Profiles").child(s).child("Name:").setValue(name.getText().toString())
                                        .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                                myRef.child("Profiles").child(s).child("NumberTel:").setValue(numbertel.getText().toString())
                                        .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });*/
                                myRef.child("Orders").child(s).setValue(0);
                                storage = storage.child("Profiles").child(s);
                                storage.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            storage.getDownloadUrl().addOnCompleteListener(
                                                    new OnCompleteListener<Uri>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Uri> task) {
                                                            if (task.isSuccessful()) {
                                                                Test.setUri(task.getResult().toString());
                                                                Test.setName(name.getText().toString());
                                                                Test.setNumberTel(numbertel.getText().toString());
                                                                myRef.child("Profiles").child(s).
                                                                        push().setValue(Test)
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                           if(task.isSuccessful()){
                                                                               bundle.putString("Success", s);
                                                                               intent.putExtras(bundle);
                                                                               editor.putString("Uri",Test.getUri());
                                                                               editor.putString("Name",Test.getName());
                                                                               editor.putString("NumberTel",Test.getNumberTel());
                                                                               editor.commit();
                                                                               progressBar.setVisibility(View.INVISIBLE);
                                                                               Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                                                               finish();
                                                                               startActivity(intent);
                                                                           }
                                                                            }
                                                                        });
                                                                }
                                                        }
                                                    });
                                        }
                                    }
                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                        int p = (int) (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                        progressBar.setProgress(p);
                                        txt.setText("" + p);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        int p = 0;
                                        progressBar.setProgress(p);
                                        txt.setText("");
                                    }
                                });
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2&&resultCode==RESULT_OK)
        {
            // fetch data from gallery //
            uri = data.getData();
            imageView.setImageURI(uri);
            textView.setVisibility(View.INVISIBLE);
        }
    }
}