package com.hassanadeola.mattire.holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.hassanadeola.mattire.R;

public class CartViewHolder extends RecyclerView.ViewHolder {
    public ShapeableImageView product_image, remove_from_cart;
    public MaterialTextView product_name, product_quantity, product_price;


    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        product_image = itemView.findViewById(R.id.product_image);
        product_price = itemView.findViewById(R.id.product_price);
        product_quantity = itemView.findViewById(R.id.product_quantity);
        product_name = itemView.findViewById(R.id.product_name);
        remove_from_cart = itemView.findViewById(R.id.remove_from_cart);
    }
}
