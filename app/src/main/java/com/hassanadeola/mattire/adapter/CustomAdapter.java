package com.hassanadeola.mattire.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.listeners.ProductListener;
import com.hassanadeola.mattire.models.Products;
import com.squareup.picasso.Picasso;


import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;

    private List<Products> products;

    private ProductListener productListener;


    public CustomAdapter(Context context, List<Products> products, ProductListener listener) {
        this.context = context;
        this.products = products;
        this.productListener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.product_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.product_name.setText(products.get(position).getName());
        holder.product_price.setText("$" + String.valueOf(products.get(position).getPrice()));
        holder.product_name.setText(products.get(position).getName());

        if (products.get(position).getImage() != null) {
            Picasso.get().load(products.get(position).getImage()).into(holder.product_image);
        }

        holder.product_image.setContentDescription(products.get(position).getName());

        holder.product_box.setOnClickListener((View view) -> productListener.onProductClick(products.get(position)));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
