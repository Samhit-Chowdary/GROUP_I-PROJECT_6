package com.example.dhruvsinghal.loginpage;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  EditText mUsername;
  EditText mPassword;
  Button mButton;
  TextView mText;
  private static int Splash_Time_out = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mUsername = findViewById(R.id.username_id);
        mPassword = findViewById(R.id.Password);
        mButton = findViewById(R.id.button_id);
        mText = findViewById(R.id.link);
        mText.setPaintFlags(mText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

       mButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(MainActivity.this , MapsActivity.class));
           }
       });

    }
}
