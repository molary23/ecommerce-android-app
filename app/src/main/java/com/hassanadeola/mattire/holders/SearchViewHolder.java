package com.hassanadeola.mattire.holders;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.hassanadeola.mattire.R;

public class SearchViewHolder extends RecyclerView.ViewHolder{

    public ShapeableImageView product_image;
    public MaterialTextView product_name,  product_price;
    public LinearLayout search_box;
    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        product_image = itemView.findViewById(R.id.product_image);
        product_price = itemView.findViewById(R.id.product_price);
        product_name = itemView.findViewById(R.id.product_name);
        search_box = itemView.findViewById(R.id.search_box);

    }
}
