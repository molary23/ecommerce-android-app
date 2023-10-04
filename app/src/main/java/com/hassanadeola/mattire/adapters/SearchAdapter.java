package com.hassanadeola.mattire.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.holders.SearchViewHolder;
import com.hassanadeola.mattire.listeners.ProductListener;
import com.hassanadeola.mattire.models.Products;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>{

    private final Context context;

    private final List<Products> products;

    private final ProductListener productListener;

    public SearchAdapter(Context context, List<Products> products, ProductListener productListener) {
        this.context = context;
        this.products = products;
        this.productListener = productListener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.search_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        String price = "$" + products.get(position).getPrice();
        holder.product_price.setText(price);
        holder.product_name.setText(products.get(position).getName());

        if (products.get(position).getImage() != null) {
            Picasso.get().load(products.get(position).getImage()).into(holder.product_image);
        }

        holder.product_image.setContentDescription(products.get(position).getName());

        holder.search_box.setOnClickListener((View view) -> productListener.onProductClick(products.get(position)));
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
