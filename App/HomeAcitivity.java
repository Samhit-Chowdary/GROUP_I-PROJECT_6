package com.example.samhi.firebasedemo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeAcitivity extends AppCompatActivity {

    private static int Splash_Time_out = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_acitivity);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =  new Intent( HomeAcitivity.this , MainActivity.class);
                startActivity(intent);
                finish();
            }
        } , Splash_Time_out);
    }
}
