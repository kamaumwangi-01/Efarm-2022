package com.example.wezeshabiashara;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ConfirnFinalActivity extends AppCompatActivity {

    private EditText nameEditText,phoneEditText,addressEditText,cityEditText;
    private Button confirmOrderBtn;

    private String totalAmount = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirn_final);

        totalAmount = getIntent().getStringExtra("Total Price");

        confirmOrderBtn = findViewById(R.id.confirm_new_order_btn);
        nameEditText = findViewById(R.id.shipment_name);
        phoneEditText = findViewById(R.id.shipment_phone_number);
        addressEditText = findViewById(R.id.settings_address);
        cityEditText = findViewById(R.id.shipment_city);

    }
}
