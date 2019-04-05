package com.example.samhi.firebasedemo;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.List;

public class FilterOptionsActivity extends AppCompatActivity {

   RecyclerView recyclerView ;
    Button mApply;
    AutoCompleteTextView autoCompleteTextView;
     List<String> filterList = new ArrayList<>();
     List<TextViewModel>  filtersUpdateList =  new ArrayList<>();
     List<TextViewModel>  tempList =  new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_options);

     mApply =  findViewById(R.id.applyfilter);
     autoCompleteTextView =  findViewById(R.id.search_bar);
     recyclerView =  findViewById(R.id.recyclerview);


        GridLayoutManager gridLayoutManager =  new GridLayoutManager(getApplicationContext() ,  2 );
        recyclerView.setLayoutManager( gridLayoutManager);


    String[]  filters = {
             "PureVeg" ,
            "Breakfast"
    };

    for( String s :  filters )
        filtersUpdateList.add( new TextViewModel(s));

    tempList.addAll(filtersUpdateList);
    TextViewAdapter textViewAdapter =  new TextViewAdapter(tempList , FilterOptionsActivity.this );
    recyclerView.setAdapter(textViewAdapter);




     autoCompleteTextView.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
                  filterFilters(s);
         }

         @Override
         public void afterTextChanged(Editable s) {

         }
     });






     mApply.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

               Intent intent =  new Intent( FilterOptionsActivity.this  , FilterActivity.class);
               intent.putExtra("list" , (Serializable) filterList);
               setResult(RESULT_OK  , intent);
          finish();
         }
     });







    }

    private void filterFilters(CharSequence s) {
       tempList.clear();
     if(TextUtils.isEmpty(s))
         tempList.addAll(filtersUpdateList);

     else{

         for( TextViewModel textViewModel  : filtersUpdateList )
         {

             String textviewdesc  = textViewModel.getString();
             if( textviewdesc.contains(s))
                 tempList.add(textViewModel);
         }



     }

      recyclerView.getAdapter().notifyDataSetChanged();

    }


    public void textClicked(View view) {

        TextView v =  (TextView) view ;
     if( v.getCurrentTextColor() == getResources().getColor(R.color.orange))
     {
         v.setBackgroundResource(R.drawable.border);
         v.setTextColor(getResources().getColor(R.color.black));
         filterList.remove(v.getText().toString());
     }
     else {
               v.setTextColor(getResources().getColor(R.color.orange));
               v.setBackgroundResource(R.drawable.clickedborder);
               filterList.add(v.getText().toString());
        }
    }
}
