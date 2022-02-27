package com.example.wezeshabiashara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class new_user_dashboard extends AppCompatActivity {

    private ImageView user_pay, track_with_message, track_with_map, lipanampesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_dashboard);

        user_pay = findViewById(R.id.user_payment_mode);
        track_with_message = findViewById(R.id.user_track_message);
        track_with_map = findViewById(R.id.track_with_map);
        lipanampesa = findViewById(R.id.lipanampesa);

        user_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new_user_dashboard.this,PayPalMain.class);
                startActivity(intent);
            }
        });
        track_with_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new_user_dashboard.this,whatsappActivity.class);
                startActivity(intent);
            }
        });
        track_with_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new_user_dashboard.this,MappingActivity.class);
                startActivity(intent);
            }
        });
        lipanampesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(new_user_dashboard.this,Lipa_na_mpesa.class);
                startActivity(intent);

            }
        });
    }
}

