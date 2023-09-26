package com.hassanadeola.mattire.adapter;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.hassanadeola.mattire.R;

public class CustomViewHolder extends RecyclerView.ViewHolder{


    MaterialTextView product_name, product_price;


    ShapeableImageView product_image;

    LinearLayout product_box;

    MaterialCardView imageCard;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        product_image = itemView.findViewById(R.id.product_image);
        product_price = itemView.findViewById(R.id.product_price);
        product_name = itemView.findViewById(R.id.product_name);
        product_box = itemView.findViewById(R.id.product_box);
        imageCard = itemView.findViewById(R.id.imageCard);
    }
}
