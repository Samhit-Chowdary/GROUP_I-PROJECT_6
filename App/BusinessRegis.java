package com.example.samhi.firebasedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class BusinessRegis extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_regis);
        spinner=(Spinner)findViewById(R.id.spregistype);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Type,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type=parent.getItemAtPosition(position).toString();
        Toast.makeText(BusinessRegis.this,type,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
