package com.example.wezeshabiashara.ViewHolder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wezeshabiashara.R;
import com.example.wezeshabiashara.interfac.itemClickListener;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtProductName,txtproductDescription,txtproductPrice;
    public ImageView imageView;
    public itemClickListener listner;


    public ProductViewHolder(@NonNull View itemView)
    {
        super(itemView);

        imageView =  itemView.findViewById(R.id.product_image);
        txtProductName =  itemView.findViewById(R.id.product_names);
        txtproductDescription =  itemView.findViewById(R.id.product_description);
        txtproductPrice =  itemView.findViewById(R.id.product_price);
    }

    public void setItemClickListener(itemClickListener listner)
    {
    this.listner = listner;
    }
    @Override
    public void onClick(View view)
    {
    listner.onClick(view, getAdapterPosition(), false);
    }
}
