package com.example.samhi.firebasedemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {
    private String email;
    private TextView name;
    private TextView email1;
    private TextView mobile;
    private TextView gender;
    private TextView accounttype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SharedPreferences sharedPreferences=getSharedPreferences("Logindata",Context.MODE_PRIVATE);
        email=sharedPreferences.getString("email","");
        name=(TextView)findViewById(R.id.tvname);
        email1=(TextView)findViewById(R.id.tvemail);
        mobile=(TextView)findViewById(R.id.tvmobile);
        gender=(TextView)findViewById(R.id.tvgender);
        accounttype=(TextView)findViewById(R.id.tvaccounttype);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        DocumentReference ref=db.collection("User").document(email);
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    name.setText(documentSnapshot.getString("Name"));
                    email1.setText(email);
                    gender.setText(documentSnapshot.getString("Gender"));
                    accounttype.setText(documentSnapshot.getString("AccountType"));
                    mobile.setText((documentSnapshot.get("MobileNumber")).toString());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, "there is some mistake", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
