package com.example.samhi.firebasedemo;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessActivity extends AppCompatActivity {
    private ListView requests;
    List<String> processname;
    public String[] name_arry;
    public String name1,name2,cuisine1,cuisine2,opening1,opening2,closing1,closing2;
    public String breakfast1,breakfast2,lunch1,lunch2,dinner1,dinner2,homedelivery1,homedelivery2,onlinebooking1,onlinebooking2,pureveg1,pureveg2;
    public String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        processname=new ArrayList<String>();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Requests").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        String temp=document.getId();
                        processname.add(temp);
                        Toast.makeText(ProcessActivity.this, temp, Toast.LENGTH_SHORT).show();
                    }
                    name_arry =new String[processname.size()];
                    name_arry=processname.toArray(name_arry);
                    requests=(ListView)findViewById(R.id.lvrequests);
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(ProcessActivity.this,android.R.layout.simple_list_item_1,name_arry);
                    requests.setAdapter(adapter);
                    requests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            AlertDialog.Builder builder= new AlertDialog.Builder(ProcessActivity.this);
                            builder.setTitle("Confirmation");
                            FirebaseFirestore db=FirebaseFirestore.getInstance();
                            db.collection("Requests").document(name_arry[position]).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    name1=documentSnapshot.getString("Name");
                                    cuisine1=documentSnapshot.getString("Cuisine");
                                    opening1=documentSnapshot.getString("Opening");
                                    closing1=documentSnapshot.getString("Closing");
                                    breakfast1=Boolean.toString(documentSnapshot.getBoolean("Breakfast"));
                                    lunch1=Boolean.toString(documentSnapshot.getBoolean("Lunch"));
                                    dinner1=Boolean.toString(documentSnapshot.getBoolean("Dinner"));
                                    homedelivery1=Boolean.toString(documentSnapshot.getBoolean("HomeDelivery"));
                                    onlinebooking1=Boolean.toString(documentSnapshot.getBoolean("OnlineBooking"));
                                    pureveg1=Boolean.toString(documentSnapshot.getBoolean("PureVeg"));
                                    FirebaseFirestore db=FirebaseFirestore.getInstance();
                                    db.collection("Restaurents").document(name_arry[position]).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            AlertDialog.Builder builder= new AlertDialog.Builder(ProcessActivity.this);
                                            builder.setTitle("Confirmation");
                                            String message="";
                                            if(!documentSnapshot.exists()){
                                                message+=name_arry[position]+" wants to register his bussiness\n";
                                                message+="Name: "+name1+"\n";
                                                message+="Cuisine: "+cuisine1+"\n";
                                                message+="ClosingTime: "+closing1+"\n";
                                                message+="Breakfast: "+breakfast1+"\n";
                                                message+="Lunch: "+lunch1+"\n";
                                                message+="Dinner: "+dinner1+"\n";
                                                message+="Homedelivery: "+homedelivery1+"\n";
                                                message+="OnlineBooking: "+onlinebooking1+"\n";
                                                message+="PureVeg: "+pureveg1;
                                            }
                                            else{
                                                name2=documentSnapshot.getString("Name");
                                                cuisine2=documentSnapshot.getString("Cuisine");
                                                opening2=documentSnapshot.getString("Opening");
                                                closing2=documentSnapshot.getString("Closing");
                                                breakfast2=Boolean.toString(documentSnapshot.getBoolean("Breakfast"));
                                                lunch2=Boolean.toString(documentSnapshot.getBoolean("Lunch"));
                                                dinner2=Boolean.toString(documentSnapshot.getBoolean("Dinner"));
                                                homedelivery2=Boolean.toString(documentSnapshot.getBoolean("HomeDelivery"));
                                                onlinebooking2=Boolean.toString(documentSnapshot.getBoolean("OnlineBooking"));
                                                pureveg2=Boolean.toString(documentSnapshot.getBoolean("PureVeg"));
                                                if(!name1.equals(name2)) {
                                                    message+="Name: "+name2+" to "+name1+"\n";
                                                }
                                                if(!cuisine1.equals(cuisine2)) {
                                                    message+="Cuisine: "+cuisine2+" to "+cuisine1+"\n";
                                                }
                                                if(!opening1.equals(opening2)) {
                                                    message+="OpeningTime: "+opening2+" to "+opening1+"\n";
                                                }
                                                if(!closing1.equals(closing2)) {
                                                    message+="ClosingTime: "+closing2+" to "+closing1+"\n";
                                                }
                                                if(!breakfast1.equals(breakfast2)) {
                                                    message+="Breakfast: "+breakfast2+" to "+breakfast1+"\n";
                                                }
                                                if(!lunch1.equals(lunch2)) {
                                                    message+="Lunch: "+lunch2+" to "+lunch1+"\n";
                                                }
                                                if(!dinner1.equals(dinner2)) {
                                                    message+="Dinner: "+dinner2+" to "+dinner1+"\n";
                                                }
                                                if(!homedelivery1.equals(homedelivery2)) {
                                                    message+="Homedelivery: "+homedelivery2+" to "+homedelivery1+"\n";
                                                }
                                                if(!onlinebooking1.equals(onlinebooking2)) {
                                                    message+="OnlineBooking: "+onlinebooking2+" to "+onlinebooking1+"\n";
                                                }
                                                if(!pureveg1.equals(pureveg2)) {
                                                    message+="PureVeg: "+pureveg2+" to "+pureveg1;
                                                }
                                            }
                                            builder.setMessage(message);
                                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    FirebaseFirestore db=FirebaseFirestore.getInstance();
                                                    Map<String,Object> restaurent=new HashMap<>();
                                                    restaurent.put("Name",name1);
                                                    restaurent.put("Cuisine",cuisine1);
                                                    restaurent.put("Opening",opening1);
                                                    restaurent.put("Closing",closing1);
                                                    restaurent.put("Breakfast",Boolean.parseBoolean(breakfast1));
                                                    restaurent.put("Lunch",Boolean.parseBoolean(lunch1));
                                                    restaurent.put("Dinner",Boolean.parseBoolean(dinner1));
                                                    restaurent.put("HomeDelivery",Boolean.parseBoolean(homedelivery1));
                                                    restaurent.put("OnlineBooking",Boolean.parseBoolean(onlinebooking1));
                                                    restaurent.put("PureVeg",Boolean.parseBoolean(pureveg1));
                                                    db.collection("Restaurents").document(name_arry[position]).set(restaurent).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            FirebaseFirestore db=FirebaseFirestore.getInstance();
                                                            db.collection("User").document(name_arry[position]).update("Registration",true);
                                                            Toast.makeText(ProcessActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(ProcessActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                    dialog.dismiss();
                                                    db.collection("Requests").document(name_arry[position]).delete();
                                                }
                                            });
                                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    FirebaseFirestore db=FirebaseFirestore.getInstance();
                                                    db.collection("Requests").document(name_arry[position]).delete();
                                                }
                                            });
                                            AlertDialog alertDialog=builder.create();
                                            alertDialog.show();
                                        }
                                    });
                                }
                            });
                           // builder.setMessage(name2+" to "+name1);
                        }
                    });
                }
                else{
                    Toast.makeText(ProcessActivity.this, "lol", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Toast.makeText(this, Integer.toString(processname.size()), Toast.LENGTH_SHORT).show();

    }
}
