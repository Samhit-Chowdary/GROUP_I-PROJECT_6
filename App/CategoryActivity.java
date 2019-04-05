package com.example.samhi.firebasedemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryActivity extends AppCompatActivity {




    AutoCompleteTextView mEditText;
          Toolbar toolbar ;

 /*   ImageView mTravel;
    ImageView mTaxi;
    ImageView mRestaurants;
    ImageView mMedicare;
    ImageView mServices;
*/

   RecyclerView recyclerView;
    List<UserModel> userList = new ArrayList<>();
    List<UserModel> tempUserList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);




        mEditText =  findViewById(R.id.search_id);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);



       recyclerView =  findViewById(R.id.recyclerview);
       GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
       recyclerView.setLayoutManager(gridLayoutManager);

        String hints[] = { "travel" , "taxi" , "medicare" , "services" , "restaurants" } ;
        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this , android.R.layout.simple_dropdown_item_1line , hints);
        mEditText.setThreshold(1);
        mEditText.setAdapter(adapter);




        SharedPreferences preferences  =  getSharedPreferences("username" , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  preferences.edit();




        int[] images = {

                 R.drawable.travel , R.drawable.taxi,
                R.drawable.services , R.drawable.medicare,
                R.drawable.restaurants
        };

        String[] image_Desc = {

                "travel","taxi","services","medicare","restaurants"
        };
        for (int i = 0 ; i< images.length ; i++)
            userList.add( new UserModel(images[i],image_Desc[i]));

        tempUserList.addAll(userList);


        UserAdapter userAdapter = new UserAdapter(tempUserList ,  this);
        recyclerView.setAdapter(userAdapter);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                doFilter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        }




    private void doFilter(String toString) {

           tempUserList.clear();
           if(TextUtils.isEmpty(toString))
               tempUserList.addAll(userList);

           else{
               for( UserModel userModel : userList )
               {

                   String image = userModel.getDesc().trim().toLowerCase();
                 if( image.contains(toString))
                     tempUserList.add(userModel);



               }



           }

               Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater =  getMenuInflater();
        menuInflater.inflate(R.menu.menutoolbar , menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int res_id =  item.getItemId();
        switch(res_id)
        {

            case R.id.Profile :
                startActivity(new Intent(CategoryActivity.this,Profile.class));
                break;
            case R.id.logout  :
                SharedPreferences sharedPreferences=getSharedPreferences("Logindata",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(CategoryActivity.this, "cleared", Toast.LENGTH_SHORT).show();
                FirebaseAuth firebaseAuth;
                firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(CategoryActivity.this,MainActivity.class));

                                              finish();
                break;

        }
        return true;
    }
}
