package com.example.samhi.firebasedemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

public class MainActivity extends AppCompatActivity {
    private TextView NewUser;
    private EditText loginEmail;
    private EditText loginPass;
    private Button loginbt;
    private FirebaseAuth firebaseAuth;
    private TextView forgotPass;
    private TextView regis;
  //  private Button map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NewUser=(TextView)findViewById(R.id.tvNewUser);
        loginEmail=(EditText)findViewById(R.id.etloginemail);
        loginPass=(EditText)findViewById(R.id.etloginpass);
        loginbt=(Button)findViewById(R.id.btlogin);
        forgotPass=(TextView)findViewById(R.id.tvforgotpass);
//        map=(Button)findViewById(R.id.btmap);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();

        if(user!=null){
            finish();
            startActivity(new Intent(MainActivity.this, CategoryActivity.class));
        }
//        map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this,MapsActivity.class));
//            }
//        });
        NewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });
        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vallidate(loginEmail.getText().toString(),loginPass.getText().toString());
            }
        });
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ResetPass.class));
            }
        });
    }
    private void vallidate(final String email, final String pass){
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user =firebaseAuth.getCurrentUser();
                    if(user.isEmailVerified()||true)
                    {
                        Toast.makeText(MainActivity.this,"sucessfull",Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences=getSharedPreferences("Logindata",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("email",email);
                        editor.putString("password",pass);
                        editor.commit();
                        FirebaseFirestore db=FirebaseFirestore.getInstance();
                        DocumentReference ref=db.collection("User").document(email);
                        Source source = Source.CACHE;
                        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()){
                                    String accounttype=documentSnapshot.getString("AccountType");
                                    if(accounttype.equals("Bussiness")){
                                        startActivity(new Intent(MainActivity.this,BussinessHome.class));
                                    }
                                    else if(accounttype.equals("Admin")){
                                        startActivity(new Intent(MainActivity.this,AdminHome.class));
                                    }
                                    else {
                                        startActivity(new Intent(MainActivity.this, CategoryActivity.class));
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "there is some mistake", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Email is not verified", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
