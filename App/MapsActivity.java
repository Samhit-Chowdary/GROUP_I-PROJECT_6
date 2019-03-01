package com.example.dhruvsinghal.loginpage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationCallBack {

    private GoogleMap mMap;
   com.example.dhruvsinghal.loginpage.LocationProvider locationProvider;
    private static final int REQUEST_PERMISSIO_CODE = 111;
    private double lat  ;
    private double lang ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        requestLocationPermission();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

    }

    protected  void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[] {ACCESS_COARSE_LOCATION , ACCESS_FINE_LOCATION} , REQUEST_PERMISSIO_CODE);
    }

   @Override
   public void onRequestPermissionsResult(int requestCode , @NonNull String[] permissions , @NonNull int[] grantResults ){
        super.onRequestPermissionsResult(requestCode , permissions , grantResults);
        switch(requestCode) {
            case REQUEST_PERMISSIO_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                            LProvider();
        }
   }


    private void LProvider() {


        if (Build.VERSION.SDK_INT >= 24) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationProvider =  new com.example.dhruvsinghal.loginpage.LocationProvider(this,this );
                locationProvider.connect();
            } else
                requestLocationPermission(); ;


        } else {
            locationProvider = new LocationProvider(this ,this );
            locationProvider.connect();
        }

    }

    @Override
    public void handlecurrentLocation(Location location) {

         lat = location.getLatitude();
         lang =  location.getLongitude();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
     Toast.makeText(this , lat + " , " +  lang , Toast.LENGTH_LONG).show();
        // Add a marker in Sydney and move the camera
      LatLng currentLocation = new LatLng(lat,lang );
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Marker in current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
    }
}
