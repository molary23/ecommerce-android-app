package com.hassanadeola.mattire.controllers;


import static com.hassanadeola.mattire.utils.Utils.navigateToView;
import static com.hassanadeola.mattire.utils.Utils.toggleDisable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.adapter.CustomAdapter;
import com.hassanadeola.mattire.api.RequestManager;
import com.hassanadeola.mattire.listeners.OnFetchDataListener;
import com.hassanadeola.mattire.listeners.ProductListener;
import com.hassanadeola.mattire.models.Firebase;
import com.hassanadeola.mattire.models.Products;
import com.hassanadeola.mattire.utils.Section;

import java.util.List;

public class ProductActivity extends AppCompatActivity implements ProductListener, View.OnClickListener {
    Button btn_logout;

    RecyclerView recommendedRecyclerView, bestRecyclerView, dealRecyclerView;
    CustomAdapter adapter;

    LinearLayout rootView;

    FrameLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        btn_logout = findViewById(R.id.btn_logout);
        rootView = findViewById(R.id.rootView);
        progressBar = findViewById(R.id.progressBar);
        btn_logout.setOnClickListener((View view) -> logout());

        toggleDisable(true, progressBar, getWindow());

        RequestManager requestManager = new RequestManager(this);
        requestManager.getProductLists(recommendedListener, 0, 5);
     //   requestManager.getProductLists(bestListener, 1, 10);
     //   requestManager.getProductLists(dealListener, 2, 10);
    }

    public void logout() {
        Firebase firebase = new Firebase(this);
        firebase.logout();
        navigateToView(this, LoginActivity.class);
    }

    private final OnFetchDataListener<Products> recommendedListener =
            new OnFetchDataListener<Products>() {
                @Override
                public void onFetchData(List<Products> list, String message) {
                    if (list.isEmpty()) {
                        Toast.makeText(ProductActivity.this, "Data not available",
                                Toast.LENGTH_SHORT).show();
                        toggleDisable(false, progressBar, getWindow());
                    } else {
                        showRecommendedProducts(list);
                        toggleDisable(false, progressBar, getWindow());

                    }
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(ProductActivity.this, "Error Occurred: " + message,
                            Toast.LENGTH_SHORT).show();
                }
            };
/*
    private final OnFetchDataListener<Products> bestListener =
            new OnFetchDataListener<Products>() {
                @Override
                public void onFetchData(List<Products> list, String message) {
                    if (list.isEmpty()) {
                        Toast.makeText(ProductActivity.this, "Data not available",
                                Toast.LENGTH_SHORT).show();
                        toggleDisable(false, progressBar, getWindow());
                    } else {
                        showBestProducts(list);
                        toggleDisable(false, progressBar, getWindow());

                    }
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(ProductActivity.this, "Error Occurred: " + message,
                            Toast.LENGTH_SHORT).show();
                }
            };

    private final OnFetchDataListener<Products> dealListener =
            new OnFetchDataListener<Products>() {
                @Override
                public void onFetchData(List<Products> list, String message) {
                    if (list.isEmpty()) {
                        Toast.makeText(ProductActivity.this, "Data not available",
                                Toast.LENGTH_SHORT).show();
                        toggleDisable(false, progressBar, getWindow());
                    } else {
                        showDealProducts(list);
                        toggleDisable(false, progressBar, getWindow());

                    }
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(ProductActivity.this, "Error Occurred: " + message,
                            Toast.LENGTH_SHORT).show();
                }
            };
            */


    private void showRecommendedProducts(List<Products> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recommendedRecyclerView = findViewById(R.id.recommendedRecyclerView);
        recommendedRecyclerView.setHasFixedSize(true);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        adapter = new CustomAdapter(this, list, this, Section.RECOMMENDED);
        recommendedRecyclerView.setAdapter(adapter);


    }
/*

    private void showBestProducts(List<Products> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        bestRecyclerView = findViewById(R.id.bestRecyclerView);
        bestRecyclerView.setHasFixedSize(true);
        bestRecyclerView.setLayoutManager(layoutManager);
        adapter = new CustomAdapter(this, list, this, Section.BEST);
        bestRecyclerView.setAdapter(adapter);
    }

    private void showDealProducts(List<Products> list) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        dealRecyclerView = findViewById(R.id.dealRecyclerView);
        dealRecyclerView.setHasFixedSize(true);
        dealRecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new CustomAdapter(this, list, this, Section.DEALS);
        dealRecyclerView.setAdapter(adapter);


    }
*/
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onProductClick(Products products) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("product", products);
        startActivity(intent);
    }
}