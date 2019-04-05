package com.example.samhi.firebasedemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BussinessHome extends AppCompatActivity {
    private Button registerbussiness;
    private Button deregisterbussiness;
    private Button showbussinessdetails;
    private Button editbussinessdetails;
    private Button logout;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussiness_home);
        SharedPreferences sharedPreferences=getSharedPreferences("Logindata",Context.MODE_PRIVATE);
        email=sharedPreferences.getString("email","");
        password=sharedPreferences.getString("password","");
        registerbussiness=(Button)findViewById(R.id.btregisbussiness);
        deregisterbussiness=(Button)findViewById(R.id.btderegisbussiness);
        showbussinessdetails=(Button)findViewById(R.id.btshowbussinessdetails);
        editbussinessdetails=(Button)findViewById(R.id.btupdatebussinessdetails);
        logout=(Button)findViewById(R.id.btbussinesshomelogout);
        registerbussiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                DocumentReference ref=db.collection("User").document(email);
                ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            boolean registration=documentSnapshot.getBoolean("Registration");
                            if(registration== false){
                                startActivity(new Intent(BussinessHome.this,RegisterBussiness.class));
                            }
                            else {
                                Toast.makeText(BussinessHome.this, "Already Registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BussinessHome.this, "there is some mistake", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        editbussinessdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                DocumentReference ref=db.collection("User").document(email);
                ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            boolean registration=documentSnapshot.getBoolean("Registration");
                            if(registration== true){
                                startActivity(new Intent(BussinessHome.this,EditBussinessDetails.class));
                            }
                            else {
                                Toast.makeText(BussinessHome.this, "Not Registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BussinessHome.this, "there is some mistake", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        showbussinessdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                DocumentReference ref=db.collection("User").document(email);
                ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            boolean registration=documentSnapshot.getBoolean("Registration");
                            if(registration== true){
                                startActivity(new Intent(BussinessHome.this,ShowBussinessInfo.class));
                            }
                            else {
                                Toast.makeText(BussinessHome.this, "Not Registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BussinessHome.this, "there is some mistake", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        deregisterbussiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(BussinessHome.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure,you can't undo it");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseFirestore db=FirebaseFirestore.getInstance();
                        db.collection("Restaurents").document(email).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                FirebaseFirestore db=FirebaseFirestore.getInstance();
                                db.collection("User").document(email).update("Registration",false);
                                db.collection("Requests").document(email).delete();
                                Toast.makeText(BussinessHome.this, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(BussinessHome.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getSharedPreferences("Logindata",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(BussinessHome.this, "cleared", Toast.LENGTH_SHORT).show();
                FirebaseAuth firebaseAuth;
                firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(BussinessHome.this,MainActivity.class));
            }
        });
    }
}
