package com.hassanadeola.mattire.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.holders.CartViewHolder;
import com.hassanadeola.mattire.holders.ProductViewHolder;
import com.hassanadeola.mattire.listeners.CartListener;
import com.hassanadeola.mattire.listeners.ProductListener;
import com.hassanadeola.mattire.models.CartItem;
import com.hassanadeola.mattire.models.Products;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private final Context context;

    private final List<CartItem> cartItem;

    private final CartListener cartListener;

    public CartAdapter(Context context, List<CartItem> cartItem, CartListener listener) {
        this.context = context;
        this.cartItem = cartItem;
        this.cartListener = listener;

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.cart_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        String price = "$" + String.valueOf(cartItem.get(position).getProduct().getPrice());
        holder.product_price.setText(price);
        holder.product_name.setText(cartItem.get(position).getProduct().getName());
        holder.product_quantity.setText(String.valueOf(cartItem.get(position).getQuantity()));
        if (cartItem.get(position).getProduct().getImage() != null) {
            Picasso.get().load(cartItem.get(position).getProduct().getImage()).into(holder.product_image);
        }

        holder.product_image.setContentDescription(cartItem.get(position).getProduct().getName());

        holder.remove_from_cart.setOnClickListener((View view) ->
                cartListener.onRemoveFromCart(cartItem.get(position).getProduct().getId()));

        holder.increase_product.setOnClickListener((View view) ->
                cartListener.onIncreaseProduct(cartItem.get(position).getProduct().getId()));

        holder.reduce_product.setOnClickListener((View view) ->
                cartListener.onReduceProduct(cartItem.get(position).getProduct().getId()));
    }

    @Override
    public int getItemCount() {
        return cartItem.size();
    }
}
