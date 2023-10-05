package com.hassanadeola.mattire.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.holders.ProductViewHolder;
import com.hassanadeola.mattire.listeners.ProductListener;
import com.hassanadeola.mattire.models.Products;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BestAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private final Context context;

    private final List<Products> products;

    private final ProductListener productListener;


    public BestAdapter(Context context, List<Products> products, ProductListener listener) {
        this.context = context;
        this.products = products;
        this.productListener = listener;

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.best_product_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
    /*    int width = 1200,
                height = 1200;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        holder.imageCard.setLayoutParams(params);
        holder.imageCard.setPadding(50, 50, 50, 50);
        holder.imageCard.setRadius(50);
        holder.product_name.setTextSize(20);*/
        holder.product_name.setText(products.get(position).getName());
        String price = "$" + products.get(position).getPrice();
        holder.product_price.setText(price);
        holder.product_name.setText(products.get(position).getName());


        if (products.get(position).getImage() != null) {
            Picasso.get().load(products.get(position).getImage()).into(holder.product_image);
        }
/*
        width = 1000;
        height = 1000;

        FrameLayout.LayoutParams imageParams = new FrameLayout.LayoutParams(width, height);
        holder.product_image.setLayoutParams(imageParams);
        holder.product_image.setScaleType(ShapeableImageView.ScaleType.FIT_CENTER);*/

        holder.product_image.setContentDescription(products.get(position).getName());

        holder.product_box.setOnClickListener((View view) -> productListener.onProductClick(products.get(position)));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
