package com.hassanadeola.mattire.controllers;


import static android.content.ContentValues.TAG;
import static com.hassanadeola.mattire.utils.Utils.navigateToView;
import static com.hassanadeola.mattire.utils.Utils.toggleDisable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.adapters.BestAdapter;
import com.hassanadeola.mattire.adapters.DealAdapter;
import com.hassanadeola.mattire.adapters.RecommendedAdapter;
import com.hassanadeola.mattire.api.RequestManager;
import com.hassanadeola.mattire.listeners.OnFetchProductListener;
import com.hassanadeola.mattire.listeners.ProductListener;
import com.hassanadeola.mattire.models.CartItem;
import com.hassanadeola.mattire.models.Products;
import com.hassanadeola.mattire.utils.CartItems;
import com.hassanadeola.mattire.utils.CountDrawable;
import com.hassanadeola.mattire.utils.Section;
import com.hassanadeola.mattire.utils.Utils;

import java.util.List;

public class ProductActivity extends AppCompatActivity implements ProductListener {
    Button btn_logout;

    RecyclerView recommendedRecyclerView, bestRecyclerView, dealRecyclerView;
    RecommendedAdapter recommendedAdapter;
    BestAdapter bestAdapter;
    DealAdapter dealAdapter;

    LinearLayout rootView;

    FrameLayout progressBar;

    int cartCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        btn_logout = findViewById(R.id.btn_search);
        rootView = findViewById(R.id.rootView);
        progressBar = findViewById(R.id.progressBar);
        btn_logout.setOnClickListener((View view) -> goToSearch());


        RequestManager requestManager = new RequestManager(this);
        requestManager.getProductLists(listener, 0, 5, Section.RECOMMENDED);
        requestManager.getProductLists(listener, 1, 10, Section.BEST);
        requestManager.getProductLists(listener, 2, 10, Section.DEALS);


        CartItems cartItems = new CartItems(this);
        cartCount = cartItems.getCartItems().size();


    }

    @Override
    protected void onStart() {
        super.onStart();
        CartItems cartItems = new CartItems(this);
        cartCount = cartItems.getCartItems().size();
        Toast.makeText(ProductActivity.this, String.valueOf(cartItems.getCartItems().size()),
                Toast.LENGTH_SHORT).show();
    }


    public void goToSearch() {
        navigateToView(this, SearchActivity.class);
    }

    private final OnFetchProductListener<Products> listener =
            new OnFetchProductListener<Products>() {
                @Override
                public void onFetchData(List<Products> list, String message, Section section) {
                    if (list.isEmpty()) {
                        Toast.makeText(ProductActivity.this, "Data not available",
                                Toast.LENGTH_SHORT).show();
                        toggleDisable(false, progressBar, getWindow());
                    } else {
                        if (section == Section.BEST) {
                            showBestProducts(list);
                        } else if (section == Section.DEALS) {
                            showDealProducts(list);
                        } else if (section == Section.RECOMMENDED) {
                            showRecommendedProducts(list);
                        }

                        toggleDisable(false, progressBar, getWindow());
                    }
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(ProductActivity.this, "Error Occurred: " + message,
                            Toast.LENGTH_SHORT).show();
                    toggleDisable(false, progressBar, getWindow());
                }

            };

    private void showRecommendedProducts(List<Products> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recommendedRecyclerView = findViewById(R.id.recommendedRecyclerView);
        recommendedRecyclerView.setHasFixedSize(true);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        recommendedAdapter = new RecommendedAdapter(this, list, this);
        recommendedRecyclerView.setAdapter(recommendedAdapter);
    }


    private void showBestProducts(List<Products> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        bestRecyclerView = findViewById(R.id.bestRecyclerView);
        bestRecyclerView.setHasFixedSize(true);
        bestRecyclerView.setLayoutManager(layoutManager);
        bestAdapter = new BestAdapter(this, list, this);
        bestRecyclerView.setAdapter(bestAdapter);
    }

    private void showDealProducts(List<Products> list) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        dealRecyclerView = findViewById(R.id.dealRecyclerView);
        dealRecyclerView.setHasFixedSize(true);
        dealRecyclerView.setLayoutManager(gridLayoutManager);
        dealAdapter = new DealAdapter(this, list, this);
        dealRecyclerView.setAdapter(dealAdapter);
    }

    @Override
    public void onProductClick(Products products) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("product", products);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_cart) {
            navigateToView(this, CartActivity.class);
        } else if (item.getItemId() == R.id.menu_settings) {
            navigateToView(this, SettingsActivity.class);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setCount(this, String.valueOf(cartCount), menu);
        return true;
    }


    public void setCount(Context context, String count, Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.menu_cart);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();

        CountDrawable badge;

        // Reuse drawable if possible
        assert icon != null;
        Drawable reuse = icon.findDrawableByLayerId(R.id.cart_item_count);
        if (reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.cart_item_count, badge);
    }

}