package com.example.wezeshabiashara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.wezeshabiashara.Model.Products;
import com.example.wezeshabiashara.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class  ProductDeatailsActivity extends AppCompatActivity
{
    //private FloatingActionButton addTocatBtn;
    private Button addTocatBtn;
    private ImageView ProductImage;
    private ElegantNumberButton numbaButton;
    private TextView Productprice,productDescription,productName;
    private  String ProductID="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_deatails);


        ProductID = getIntent().getStringExtra("pid");


       addTocatBtn=findViewById(R.id.add_product_to_cart_btn);
        numbaButton=findViewById(R.id.number_btn);
        ProductImage=findViewById(R.id.product_image_detail);
        productName=findViewById(R.id.product_name_details);
        productDescription=findViewById(R.id.product_description_service);
        Productprice=findViewById(R.id.product_price_details);

        getProductDetail(ProductID);

        addTocatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
            addingToCartList();
            }
        });
    }

    private void addingToCartList()
{
    String saveCurrentTime,saveCurrentDate;
    Calendar CalForDate = Calendar.getInstance();
    SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
    saveCurrentDate = currentDate.format(CalForDate.getTime());

    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
    saveCurrentTime = currentDate.format(CalForDate.getTime());

    final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
    final HashMap<String, Object> cartMap = new HashMap<>();
    cartMap.put("pid", ProductID);
    cartMap.put("pname", productName.getText().toString());
    cartMap.put("price", Productprice.getText().toString());
    cartMap.put("date", saveCurrentDate);
    cartMap.put("time", saveCurrentTime);
    cartMap.put("quantity", numbaButton.getNumber());
    cartMap.put("discount", "");

    cartListRef.child("User View").child(Prevalent.currentUserSession.getPhone())
            .child("Products").child(ProductID)
            .updateChildren(cartMap)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if (task.isSuccessful())
                    {
                        cartListRef.child("Admin View").child(Prevalent.currentUserSession.getPhone())
                                .child("Products").child(ProductID)
                                .updateChildren(cartMap);//here
                                String user= Prevalent.currentUserSession.getPhone();//here
                                cartListRef.child(user).child("Products").child(ProductID).updateChildren(cartMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(ProductDeatailsActivity.this,"Added to cart List",Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(ProductDeatailsActivity.this,new_user_dashboard.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                    }

                }
            });

}

    private void getProductDetail(String ProductID)
    {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(ProductID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    Products products = dataSnapshot.getValue(Products.class);

                    productName.setText(products.getPname());
                    Productprice.setText(products.getPrice());
                    productDescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(ProductImage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
