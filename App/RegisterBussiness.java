package com.example.samhi.firebasedemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterBussiness extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String email;
    private String Password;
    private Spinner cuisine;
    private String cuisinestring;
    private String bussinessnamest;
    private String openingtimest;
    private String closingtimest;
    private boolean breakfastb;
    private boolean lunchb;
    private boolean dinnerb;
    private boolean homedeliveryb;
    private boolean onlinebookingb;
    private boolean purevegb;
    private EditText bussinessname;
    private EditText openingtime;
    private EditText closingtime;
    private Switch breakfast;
    private Switch lunch;
    private Switch dinner;
    private Switch homedelivery;
    private Switch pureveg;
    private Switch onlinebooking;
    private Button register;
  //  LocationManager locationManager;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bussiness);
        SharedPreferences sharedPreferences = getSharedPreferences("Logindata", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");
        Password = sharedPreferences.getString("password", "");
        cuisine = (Spinner) findViewById(R.id.spcuisine);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Cuisne, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisine.setAdapter(adapter);
        cuisine.setOnItemSelectedListener(this);
        bussinessname = (EditText) findViewById(R.id.etbussinessname);
        openingtime = (EditText) findViewById(R.id.etopeningtime);
        closingtime = (EditText) findViewById(R.id.etclosingtime);
        breakfast = (Switch) findViewById(R.id.swbreakfast);
        lunch = (Switch) findViewById(R.id.swlunch);
        dinner = (Switch) findViewById(R.id.swdinner);
        homedelivery = (Switch) findViewById(R.id.swhomedelivery);
        pureveg = (Switch) findViewById(R.id.swpureveg);
        onlinebooking = (Switch) findViewById(R.id.swonlinebooking);
        register = (Button) findViewById(R.id.btreister);
        //Toast.makeText(RegisterBussiness.this,email+" "+Password,Toast.LENGTH_SHORT).show();
        breakfast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                breakfastb = isChecked;
            }
        });
        lunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lunchb = isChecked;
            }
        });
        dinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dinnerb = isChecked;
            }
        });
        homedelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                homedeliveryb = isChecked;
            }
        });
        onlinebooking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onlinebookingb = isChecked;
            }
        });
        pureveg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                purevegb = isChecked;
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bussinessnamest = bussinessname.getText().toString();
                openingtimest = openingtime.getText().toString();
                closingtimest = closingtime.getText().toString();
                db = FirebaseFirestore.getInstance();
                final Map<String, Object> restaurent = new HashMap<>();
                //bussinessnamest="samhit";
                restaurent.put("Name", bussinessnamest);
                restaurent.put("Opening", openingtimest);
                restaurent.put("Closing", closingtimest);
                restaurent.put("Cuisine", cuisinestring);
                restaurent.put("Breakfast", breakfastb);
                restaurent.put("Lunch", lunchb);
                restaurent.put("Dinner", dinnerb);
                restaurent.put("HomeDelivery", homedeliveryb);
                restaurent.put("OnlineBooking", onlinebookingb);
                restaurent.put("PureVeg", purevegb);
                db.collection("Requests").document(email).set(restaurent).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        FirebaseFirestore db=FirebaseFirestore.getInstance();
                      //  db.collection("User").document(email).update("Registration",true);
                        Toast.makeText(RegisterBussiness.this, "Registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterBussiness.this,BussinessHome.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterBussiness.this, "NotRegistered", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cuisinestring=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
