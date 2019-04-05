package com.example.samhi.firebasedemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class FilterActivity extends AppCompatActivity {

    //private static final String desc = "view restaurents";

    private final String TAG = "filter";
    private  String img_des  =  "uyo";
    TextView mCat;

    Button mFilter;
    List<FilterModel> mUserlist  =  new ArrayList<>();
    List<FilterModel> tempuserlist =  new ArrayList<>();
    RecyclerView recyclerView ;
    Toolbar toolbar ;
    List<String> desc = new ArrayList<>();
   List<String>   desc2 =  new ArrayList<>();
    List<String>   desc3 =  new ArrayList<>();
    List<String>   desc4 =  new ArrayList<>();
    List<String>   desc5 =  new ArrayList<>();
    FirebaseFirestore db =  FirebaseFirestore.getInstance();
    AutoCompleteTextView autoCompleteTextView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        autoCompleteTextView = findViewById(R.id.search_bar);
        mCat = findViewById(R.id.cate);


        mFilter = findViewById(R.id.filter);
        recyclerView = findViewById(R.id.filter_Recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        run();

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
       img_des = bundle.getString("Image");
        mCat.setText(img_des.toUpperCase());








      mFilter.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

                 tempuserlist.addAll(mUserlist);
               Intent intent = new Intent(FilterActivity.this  , FilterOptionsActivity.class );

            startActivityForResult(intent , 1 );


          }


      });

      autoCompleteTextView.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {

              filterCard(s);

          }

          @Override
          public void afterTextChanged(Editable s) {

          }
      });







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
                startActivity(new Intent(FilterActivity.this,Profile.class));
                break;
            case R.id.logout  :
                SharedPreferences sharedPreferences=getSharedPreferences("Logindata",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(FilterActivity.this, "cleared", Toast.LENGTH_SHORT).show();
                FirebaseAuth firebaseAuth;
                firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(FilterActivity.this,MainActivity.class));

                finish();
                break;


        }
        return true;
    }


    protected String run(Void... voids) {
            db.collection("Restaurents").addSnapshotListener(new EventListener<QuerySnapshot>() {
                String s = "def";
                String s1 = "def";
                String s2 = "def";
                String s3 = "def";
                @Override
                public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(TAG , "Listen failed.", e);
                        return;
                    }

                    if (queryDocumentSnapshots != null) {
                        desc2.clear();
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {

                            s = documentSnapshot.get("Name").toString();
                            s1= documentSnapshot.get("Opening").toString();
                            s2= documentSnapshot.get("Closing").toString();
                            s3= documentSnapshot.get("Cuisine").toString();
                            // Value of string S changed

                            Log.d( TAG , s );
                            desc2.add(s);
                            desc3.add(s1);
                            desc4.add(s2);
                            desc5.add(s3);


                            //   Toast.makeText(FilterActivity.this, s, Toast.LENGTH_LONG).show();  // This toast works at the end
                        }
                    } else {
                        Log.d(TAG , "Current data: null");
                    }
                    Log.d(TAG,desc2.toString());

                    updateRecyclerView();
                }
            });
            return "";
        }

    private void updateRecyclerView() {

        int img[] = {
                R.drawable.aladeen, R.drawable.zippy,
                R.drawable.taste_bud, R.drawable.teapost
        };
        for( int i=0 ; i<img.length ; i++ )
            mUserlist.add( new FilterModel(img[i], desc2.get(i),desc3.get(i),desc4.get(i),desc5.get(i)));
        tempuserlist.addAll(mUserlist);
        FilterAdapter filterAdapter =  new FilterAdapter(tempuserlist , this , img_des);
        recyclerView.setAdapter(filterAdapter);




    }

    private void filterCard(CharSequence s) {
        tempuserlist.clear();
        if(TextUtils.isEmpty(s))
            tempuserlist.addAll(mUserlist);
        else
        {
            for( FilterModel filterModel : mUserlist)
            {

                String cardName =  filterModel.getTextView().trim().toLowerCase();
                if( cardName.contains(s))
                    tempuserlist.add(filterModel);
            }
        }


        recyclerView.getAdapter().notifyDataSetChanged();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(resultCode ==  RESULT_OK )
            {
                assert data != null;
                List<String> list  =  data.getStringArrayListExtra("list");

                doFilter(list);
            }
        }

    }

    private void doFilter(List<String> list) {


         if(list.isEmpty()) {
             tempuserlist.clear();
             tempuserlist.addAll(mUserlist);
             recyclerView.getAdapter().notifyDataSetChanged();
         }
      else {

                final List<FilterModel> newList =  new ArrayList<>();
             for (int i = 0; i < list.size(); i++) {

                 db.collection("Restaurents").whereEqualTo(list.get(i) , true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {

                         if (task.isSuccessful()) {
                             //Toast.makeText(FilterActivity.this,"yo" , Toast.LENGTH_LONG).show();


                             for (QueryDocumentSnapshot document : task.getResult()) {

                                 //Toast.makeText(FilterActivity.this, (CharSequence) document.get("Name"), Toast.LENGTH_LONG).show();

                                 for (FilterModel filterModel : tempuserlist) {

                                     if (document.get("Name").equals(filterModel.getTextView())) {

                                         newList.add(filterModel);
                                                  break;

                                     }
                                 }

                             }

                           tempuserlist.clear();
                             tempuserlist.addAll(newList);
                             recyclerView.getAdapter().notifyDataSetChanged();
                             newList.clear();
                         }
                     }
                 });

             }

         }


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //function definition

     /* public void retreiveData()
     {
         db.collection("Restaurants ").addSnapshotListener(new EventListener<QuerySnapshot>() {
             @Override
             public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                 if (e != null) {
                     Log.w(TAG, "Listen failed.", e);
                     return;
                 }

                 if (queryDocumentSnapshots != null) {
                     desc2.clear();
                     for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {

                         s = documentSnapshot.get("Name").toString();   // Value of string S changed
                         Log.d(TAG , s );
                         desc2.add(s);


                      //   Toast.makeText(FilterActivity.this, s, Toast.LENGTH_LONG).show();  // This toast works at the end
                     }
                 } else {
                     Log.d(TAG , "Current data: null");
                 }
             }
         });

     }
*/

     public class fetchData extends AsyncTask< Void , Void , String>{
         String s = "def";
         @Override
         protected String doInBackground(Void... voids) {

             db.collection("Restaurents").addSnapshotListener(new EventListener<QuerySnapshot>() {
                 @Override
                 public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                     if (e != null) {
                         Log.w(TAG , "Listen failed.", e);
                         return;
                     }

                     if (queryDocumentSnapshots != null) {
                         desc2.clear();
                         for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {

                             s = documentSnapshot.get("Name").toString();   // Value of string S changed

                             Log.d( TAG , s );
                             desc2.add(s);


                             //   Toast.makeText(FilterActivity.this, s, Toast.LENGTH_LONG).show();  // This toast works at the end
                         }
                     } else {
                         Log.d(TAG , "Current data: null");
                     }
                 }
             });
             Log.d(TAG,"do in background  "+s);
             return s;
         }

         @Override
         protected void onPostExecute(String s) {
             super.onPostExecute(s);
             Log.d(TAG,"On post execute "+s);
             Toast.makeText(FilterActivity.this , "On Post Execute" + s , Toast.LENGTH_LONG).show();
         }
     }



}
