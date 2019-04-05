package com.example.samhi.firebasedemo;

import android.content.Context;
import android.content.Intent;
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

public class ShowBussinessInfo extends AppCompatActivity {
    private String email;
    private TextView name;
    private TextView openingtime;
    private TextView closingtime;
    private TextView cuisine;
    private TextView breakfast;
    private TextView lunch;
    private TextView dinner;
    private TextView homedelivery;
    private TextView onlinebooking;
    private TextView pureveg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences=getSharedPreferences("Logindata",Context.MODE_PRIVATE);
        email=sharedPreferences.getString("email","");
        setContentView(R.layout.activity_show_bussiness_info);
        name=(TextView)findViewById(R.id.tvname);
        openingtime=(TextView)findViewById(R.id.tvopeningtime);
        closingtime=(TextView)findViewById(R.id.tvclosingtime);
        cuisine=(TextView)findViewById(R.id.tvcuisine);
        breakfast=(TextView)findViewById(R.id.tvbreakfast);
        lunch=(TextView)findViewById(R.id.tvlunch);
        dinner=(TextView)findViewById(R.id.tvdinner);
        homedelivery=(TextView)findViewById(R.id.tvhomedelivery);
        onlinebooking=(TextView)findViewById(R.id.tvonlinebooking);
        pureveg=(TextView)findViewById(R.id.tvpureveg);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        DocumentReference ref=db.collection("Restaurents").document(email);
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    name.setText(documentSnapshot.getString("Name"));
                    openingtime.setText(documentSnapshot.getString("Opening"));
                    closingtime.setText(documentSnapshot.getString("Closing"));
                    cuisine.setText(documentSnapshot.getString("Cuisine"));
                    breakfast.setText(Boolean.toString(documentSnapshot.getBoolean("Breakfast")));
                    lunch.setText(Boolean.toString(documentSnapshot.getBoolean("Lunch")));
                    dinner.setText(Boolean.toString(documentSnapshot.getBoolean("Dinner")));
                    homedelivery.setText(Boolean.toString(documentSnapshot.getBoolean("HomeDelivery")));
                    onlinebooking.setText(Boolean.toString(documentSnapshot.getBoolean("OnlineBooking")));
                    pureveg.setText(Boolean.toString(documentSnapshot.getBoolean("PureVeg")));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ShowBussinessInfo.this, "there is some mistake", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
