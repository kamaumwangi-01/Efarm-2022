package com.example.wezeshabiashara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.wezeshabiashara.Model.Cart;
import com.example.wezeshabiashara.Prevalent.Prevalent;
import com.example.wezeshabiashara.ViewHolder.Cart_view_Holder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rey.material.widget.TextView;

public class CartActivity extends AppCompatActivity
{

   private RecyclerView recyclerView;
   private  RecyclerView.LayoutManager layoutManager;
   private Button Nextprocessbtn;
   private TextView txttotalAmount;

   private int  overTotalPrice = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

       recyclerView=findViewById(R.id.cart_lists);
       recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Nextprocessbtn = findViewById(R.id.next_process_btn);
       //txttotalAmount= findViewById(R.id.total_price);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options =
                new  FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("User View")
                .child(Prevalent.currentUserSession.getPhone())
                        .child("Products"),Cart.class)
                        .build();

        FirebaseRecyclerAdapter<Cart, Cart_view_Holder> adapter
                = new FirebaseRecyclerAdapter<Cart, Cart_view_Holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Cart_view_Holder holder, int position, @NonNull final Cart model)
            {
                holder.txtproductquantity.setText("Quantity = "+model.getQuantity());
                holder.txtproductprice.setText("Service Price = "+model.getPrice() + "/=");
                holder.txtProductName.setText(model.getPname());

              // int oneTypeproductPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
                //overTotalPrice = overTotalPrice + oneTypeproductPrice;

                Nextprocessbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       //txttotalAmount.setText(String.valueOf(overTotalPrice));
                        //Intent intent = new Intent(CartActivity.this,ConfirnFinalActivity.class);
                       // intent.putExtra("Total Price",String.valueOf(overTotalPrice));
                        //startActivity(intent);
                       // finish();
                        Intent intent = new Intent(CartActivity.this,PayPalMain.class);
                        startActivity(intent);
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]
                                {
                                  "Edit",
                                   "Remove"
                                };
                        AlertDialog.Builder builder =new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options:");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i)
                            {
                               if(i == 0)
                               {
                                   Intent intent = new Intent(CartActivity.this,ProductDeatailsActivity.class);
                                   intent.putExtra("pid",model.getPid());
                                   startActivity(intent);
                               }
                               if (i == 1)
                               {
                                   cartListRef.child("User View")
                                           .child(Prevalent.currentUserSession.getPhone())
                                           .child("Products")
                                           .child(model.getPid())
                                           .removeValue()
                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                               @Override
                                               public void onComplete(@NonNull Task<Void> task) {
                                                  if(task.isSuccessful())
                                                  {
                                                     Toast.makeText(CartActivity.this,"Removed successifully", Toast.LENGTH_LONG).show();
                                                      Intent intent = new Intent(CartActivity.this,HomeActivity.class);
                                                      startActivity(intent);
                                                  }
                                               }
                                           });
                               }
                            }
                        });
                        builder.show();

                    }
                });
            }

            @NonNull
            @Override
            public Cart_view_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                Cart_view_Holder holder = new Cart_view_Holder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
