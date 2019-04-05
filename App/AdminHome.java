package com.example.samhi.firebasedemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminHome extends AppCompatActivity {
    private Button processrequest;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        processrequest=(Button)findViewById(R.id.btprocessrequests);
        logout=(Button)findViewById(R.id.btadminloguot);
        processrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminHome.this, "hi", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminHome.this,ProcessActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getSharedPreferences("Logindata",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(AdminHome.this, "cleared", Toast.LENGTH_SHORT).show();
                FirebaseAuth firebaseAuth;
                firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(AdminHome.this,MainActivity.class));
            }
        });
    }
}
