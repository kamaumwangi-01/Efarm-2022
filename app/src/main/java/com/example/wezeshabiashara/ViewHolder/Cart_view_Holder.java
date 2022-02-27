package com.example.wezeshabiashara.ViewHolder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wezeshabiashara.R;
import com.example.wezeshabiashara.interfac.itemClickListener;

public class Cart_view_Holder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtproductprice,txtproductquantity;
    private itemClickListener itemClickListener;



    public Cart_view_Holder(@NonNull View itemView) {

        super(itemView);

        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtproductprice = itemView.findViewById(R.id.cart_product_price);
        txtproductquantity= itemView.findViewById(R.id.cart_product_quantity);
    }

    @Override
    public void onClick(View view)
    {
      itemClickListener.onClick(view,getAdapterPosition(),false);
    }

    public void setItemClickListener(itemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }
}
