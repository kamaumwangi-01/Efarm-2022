package com.example.wezeshabiashara;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.database.FirebaseDatabase;

public class MapActivity extends AppCompatActivity {

    //==google play services
    //==google play services
    private static  final String TAG = "Map Activity";
    private static final int ERROR_DIALOGUE_REQUEST = 9001;
   // private FirebaseDatabase mDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //==google play services
        if(isServicesOk())
        {
            init();
        }

    }

    //==========Google play services============//
    //==========Google play services============//

    private void init()
    {
        Button btnMap = findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MapActivity.this,MappingActivity.class);
                startActivity(intent);
            }
        });
    }


    public boolean isServicesOk(){
        Log.d(TAG, "isServiceOk: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapActivity.this);
        if(available == ConnectionResult.SUCCESS)
        {
            //everything is and the user can make map request
            Log.d(TAG, "isServiceOk: Google play services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            //an error occured but we ca resolve it
            Log.d(TAG,"isServiceOk: error occured but we can resolve it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MapActivity.this,available,ERROR_DIALOGUE_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this,"You cant make map request",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
