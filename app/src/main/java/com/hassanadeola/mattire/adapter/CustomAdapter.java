package com.hassanadeola.mattire.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.listeners.ProductListener;
import com.hassanadeola.mattire.models.Products;
import com.hassanadeola.mattire.utils.Section;
import com.squareup.picasso.Picasso;


import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private final Context context;

    private final List<Products> products;

    private final ProductListener productListener;

    private final Section section;


    public CustomAdapter(Context context, List<Products> products, ProductListener listener, Section section) {
        this.context = context;
        this.products = products;
        this.productListener = listener;
        this.section = section;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.product_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        int width = 0, height = 0;
        if (section == Section.BEST) {
            width = 1200;
            height = 1200;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            holder.imageCard.setLayoutParams(params);
            holder.imageCard.setRadius(50);
            holder.product_name.setTextSize(20);
        } else if (section == Section.DEALS) {
            width = 600;
            height = 600;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            holder.imageCard.setLayoutParams(params);
            holder.imageCard.setRadius(20);
        }
        holder.product_name.setText(products.get(position).getName());
        String price = "$" + String.valueOf(products.get(position).getPrice());
        holder.product_price.setText(price);
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
