package com.example.samhi.firebasedemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPass extends AppCompatActivity {
    private EditText resetemail;
    private Button send;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        resetemail=(EditText)findViewById(R.id.etrpemail);
        send=(Button)findViewById(R.id.btsend);
        firebaseAuth=FirebaseAuth.getInstance();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=resetemail.getText().toString().trim();
                if(mail.equals("")){
                    Toast.makeText(ResetPass.this,"Please enter your email",Toast.LENGTH_SHORT);
                }else{
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ResetPass.this,"Sent",Toast.LENGTH_SHORT);
                                finish();
                                startActivity(new Intent(ResetPass.this,MainActivity.class));
                            }else{
                                Toast.makeText(ResetPass.this,"NotSent",Toast.LENGTH_SHORT);
                            }
                        }
                    });
                }
            }
        });
    }
}
