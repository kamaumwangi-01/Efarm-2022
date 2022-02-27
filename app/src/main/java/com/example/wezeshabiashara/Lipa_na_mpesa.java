package com.example.wezeshabiashara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Lipa_na_mpesa extends AppCompatActivity {
    private EditText mpesaphoneno,mpesaamounts;
    private Button mpesastk;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lipa_na_mpesa);

        mpesaphoneno = findViewById(R.id.mpesa_phone);
        mpesaamounts = findViewById(R.id.mpesa_amount);
        mpesastk = findViewById(R.id.stk_push);



        mpesastk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lipanampesastkpush();
            }
        });
    }
    private void lipanampesastkpush() {
        String phone = mpesaphoneno.getText().toString();
        String amount = mpesaamounts.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "phone number format 2547...", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty (amount)) {
            Toast.makeText(this, "Enter amount", Toast.LENGTH_LONG).show();
        } else {

            gotoUrl("http://myonlineduka.com/test/?mpesa=true&phone="+phone+"&amount="+amount);

        }
    }
    private void gotoUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}
