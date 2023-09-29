package com.hassanadeola.mattire.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.adapters.BestAdapter;
import com.hassanadeola.mattire.adapters.CartAdapter;
import com.hassanadeola.mattire.adapters.DealAdapter;
import com.hassanadeola.mattire.api.RequestManager;
import com.hassanadeola.mattire.listeners.CartListener;
import com.hassanadeola.mattire.models.CartItem;
import com.hassanadeola.mattire.models.Products;
import com.hassanadeola.mattire.utils.CartItems;
import com.hassanadeola.mattire.utils.Section;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartListener {

    CartAdapter cartAdapter;
    RecyclerView cartRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        CartItems cartItems = new CartItems(this);
        showCartItems(cartItems.getCartItems());
    }


    private void showCartItems(List<CartItem> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setHasFixedSize(true);
        cartRecyclerView.setLayoutManager(layoutManager);
        cartAdapter = new CartAdapter(this, list, this);
        cartRecyclerView.setAdapter(cartAdapter);
    }

    @Override
    public void onRemoveFromCart(CartItem cartItem) {

    }
}