package com.example.samhi.firebasedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    String gen;
    String user2;
    Button buttonRegister;
    RadioGroup radioSex;
    RadioGroup radio_user;
    EditText editTextEmail;
    EditText userName;
    EditText editTextPassword;
    EditText confirmpPassword;
    EditText mobileNumber;
    TextView TextViewSignin;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    RadioButton radiobutton;
    RadioButton radiobutton2;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);

        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        confirmpPassword = (EditText) findViewById(R.id.confirmPassword);
        userName = (EditText) findViewById(R.id.userName);
        TextViewSignin = (TextView) findViewById(R.id.textViewSignin);
        radioSex = (RadioGroup) findViewById(R.id.radiosex);
        radio_user = (RadioGroup) findViewById(R.id.radio_user);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        TextViewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
            }
        });
    }
    public void registerUser()
    {
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        String confirmpassword = confirmpPassword.getText().toString().trim();
        final String username=userName.getText().toString().trim();
        String gender=radiobutton.getText().toString();
        //String user_type = radiobutton2.getText().toSting();
        //  String user=radio_user.getText().toString();

        final String mobile = mobileNumber.getText().toString();

        final DocumentReference mdoc_ref = firebaseFirestore.getInstance().collection("User").document(email);



        if(email.isEmpty()||username.isEmpty()||password.isEmpty()||confirmpassword.isEmpty()||gender.isEmpty()||mobile.isEmpty()||user2.isEmpty())
        {
            Toast.makeText(RegistrationActivity.this,"Please enter all the details",Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this,"password should be of minimum length 6", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!confirmpassword.equals(password))
        {
            Toast.makeText(this,"please confirm password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mobile.length() != 10)
        {
            Toast.makeText(this,"enter valid mobile number",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering user ...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {   progressDialog.hide();

                    Map<String, Object> general_user = new HashMap<>();
                    general_user.put("Name", username);
                    general_user.put("Gender",gen);
                    general_user.put("MobileNumber", mobile);
                    general_user.put("AccountType",user2);
                    general_user.put("Registration",false);

                    mdoc_ref.set(general_user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RegistrationActivity.this,"REGISTERED SUCCESSFULLY" +
                                    "",Toast.LENGTH_SHORT).show();
                            firebaseAuth.signInWithEmailAndPassword(email,password);
                            FirebaseUser user=firebaseAuth.getCurrentUser();
                            user.sendEmailVerification();
                            firebaseAuth.signOut();
                            startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegistrationActivity.this," not REGISTERED",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else
                {       progressDialog.hide();
                    Toast.makeText(RegistrationActivity.this,"Failed to Register, user with this email already exists...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void check(View v){

        int radioid=radioSex.getCheckedRadioButtonId();
        radiobutton = (RadioButton) findViewById(radioid);
        gen = radiobutton.getText().toString();

       // Toast.makeText(RegistrationActivity.this,radiobutton.getText().toString(),Toast.LENGTH_SHORT).show();
    }
    public void check1(View v){

        int user1= radio_user.getCheckedRadioButtonId();
        radiobutton2 = (RadioButton) findViewById(user1);
        user2 = radiobutton2.getText().toString();
        //return gen;
        //Toast.makeText(MainActivity.this,radiobutton.getText().toString(),Toast.LENGTH_SHORT).show();
    }
}
