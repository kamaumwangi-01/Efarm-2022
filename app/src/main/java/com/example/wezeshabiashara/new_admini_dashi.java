package com.example.wezeshabiashara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class new_admini_dashi extends AppCompatActivity {

    private ImageView Backtologin,AddNewCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_admini_dashi);

        Backtologin = findViewById(R.id.back_to_login_btn);
        AddNewCategory =findViewById(R.id.admin_add_category);

        AddNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new_admini_dashi.this, AdminAddNewProductActivity.class);
                startActivity(intent);
            }
        });

        Backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new_admini_dashi.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
