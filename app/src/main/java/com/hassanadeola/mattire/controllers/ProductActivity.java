package com.hassanadeola.mattire.controllers;

import static com.hassanadeola.mattire.utils.Utils.navigate;
import static com.hassanadeola.mattire.utils.Utils.toggleDisable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.List;

public class ProductActivity extends AppCompatActivity implements ProductListener, View.OnClickListener {
    Button btn_logout;

    RecyclerView recommendedRecyclerView;
    CustomAdapter adapter;

    LinearLayout rootView;

    FrameLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        btn_logout = (Button) findViewById(R.id.btn_logout);
        rootView = (LinearLayout) findViewById(R.id.rootView);
        progressBar = (FrameLayout) findViewById(R.id.progressBar);
        btn_logout.setOnClickListener((View view) -> logout());

        toggleDisable(true, progressBar, getWindow());

        RequestManager requestManager = new RequestManager(this);
        requestManager.getProductLists(listener, 0, 10);
    }

    public void logout() {
        Firebase firebase = new Firebase(this);
        firebase.logout();
        navigate(this, LoginActivity.class);
    }

    private final OnFetchDataListener<Products> listener =
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

    private void showRecommendedProducts(List<Products> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recommendedRecyclerView = findViewById(R.id.recommendedRecyclerView);
        recommendedRecyclerView.setHasFixedSize(true);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        adapter = new CustomAdapter(this, list, this);
        recommendedRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onProductClick(Products products) {

    }
}