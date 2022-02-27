package com.example.wezeshabiashara;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MappingActivity extends AppCompatActivity implements OnMapReadyCallback {
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this,"Ready to use maps now",Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onMapReady: map is ready");
        nMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();


        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this
                ,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            return;
        }
        nMap.setMyLocationEnabled(true);
        nMap.getUiSettings().setMyLocationButtonEnabled(false);
        init();

        }
    }
    private static final String TAG="MappingActivity";
    private  static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final  float DEFAULT_ZOOM = 15f;

    //widgets
    private EditText mSearchText;

    //vars
    private Boolean mLocationPermissionsGranted =false;
    private GoogleMap nMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping);
        mSearchText = findViewById(R.id.input_search);

        getLocationPermission();

    }

    private void init(){
        Log.d(TAG, "init: initializing");
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v,int actionId,KeyEvent event)
            {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || event.getAction()==KeyEvent.ACTION_DOWN
                || event.getAction()==KeyEvent.KEYCODE_ENTER){

                    //execute searching method
                    geoLocate();

                }
                return false;
            }
        });
    }

    private void geoLocate(){
        Log.d(TAG,"geolocate, geolocating");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(MappingActivity.this);
        List<Address>list=new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString,1);

        }catch (IOException e){
          Log.e(TAG,"geoLocate: I/OException: " + e.getMessage());
        }

        if(list.size()>0){
           Address address = list.get(0);
           Log.d(TAG,"geolocate: found a location:" + address.toString());
            //Toast.makeText(this,address.toString(),Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLongitude(),address.getLatitude()),DEFAULT_ZOOM,address.getAddressLine(0));
        }

    }

    private void getDeviceLocation(){
        Log.d(TAG,"getDeviceLocation: getting the current devices current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"onComplete: found location");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),DEFAULT_ZOOM,"my Location");

                        }else{
                            Log.d(TAG,"onComplete: current location is null");
                            Toast.makeText(MappingActivity.this, "unable to get current location",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }

        }catch(SecurityException e){
            Log.e(TAG, "get device location: SecurityException: "+e.getMessage() );
        }
    }

    private void moveCamera(com.google.android.gms.maps.model.LatLng latlng,float zoom, String title){
        Log.d(TAG,"moveCamera: moving the camera to: lat "+ latlng.latitude+", lng: "+latlng.longitude);
        nMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,zoom));

        MarkerOptions options = new MarkerOptions()
                .position(latlng)
                .title(title);
        nMap.addMarker(options);
    }
    private void initMap()
    {
        Log.d(TAG,"initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MappingActivity.this);
    }
    private void getLocationPermission()
    {
        Log.d(TAG,"gettingLocationPermission: getting location permission");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};

            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                        COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionsGranted = true;
                } else {
                    ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
                }
            }
            else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       Log.d(TAG,"onRequestPermissionResult: called");
        mLocationPermissionsGranted = false;
        switch(requestCode)
        {
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for (int i = 0; i < grantResults.length; i++)
                    {
                        if(grantResults[i]!=PackageManager.PERMISSION_GRANTED)
                        {
                            mLocationPermissionsGranted=false;
                            Log.d(TAG,"onRequestPermissionResult: permission granted");
                            return;
                        }
                    }

                       mLocationPermissionsGranted=true;
                       //initialize map
                    initMap();
                }
            }
        }
    }


}
