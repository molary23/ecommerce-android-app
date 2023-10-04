package com.hassanadeola.mattire.viewmodels;

import static com.hassanadeola.mattire.utils.Utils.toggleDisable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.adapters.RecommendedAdapter;
import com.hassanadeola.mattire.adapters.SearchAdapter;
import com.hassanadeola.mattire.api.RequestManager;
import com.hassanadeola.mattire.listeners.OnFetchProductListener;
import com.hassanadeola.mattire.listeners.ProductListener;
import com.hassanadeola.mattire.models.Products;
import com.hassanadeola.mattire.utils.Section;
import com.hassanadeola.mattire.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity implements ProductListener {

    RecyclerView searchRecyclerView;
    SearchAdapter searchAdapter;
    FrameLayout progressBar;

    SearchView txt_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Utils.createActionBar(Objects.requireNonNull(getSupportActionBar()));

        progressBar = findViewById(R.id.progressBar);
        txt_search = findViewById(R.id.txt_search);

        RequestManager requestManager = new RequestManager(this);


        txt_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {
                toggleDisable(true, progressBar, getWindow());
                requestManager.getProductLists(listener, 0, 5, s, Section.SEARCH);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) {
                    List<Products> list = new ArrayList<>();
                    showSearchProducts(list);
                    return false;
                }
                return true;
            }
        });

    }

    @Override
    public void onProductClick(Products products) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("product", products);
        startActivity(intent);
    }


    private final OnFetchProductListener<Products> listener =
            new OnFetchProductListener<Products>() {
                @Override
                public void onFetchData(List<Products> list, String message, Section section) {
                    if (list.isEmpty()) {
                        Toast.makeText(SearchActivity.this, "No products found",
                                Toast.LENGTH_SHORT).show();
                        toggleDisable(false, progressBar, getWindow());
                    } else {

                        showSearchProducts(list);
                        toggleDisable(false, progressBar, getWindow());
                    }
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(SearchActivity.this, "Error Occurred: " + message,
                            Toast.LENGTH_SHORT).show();
                    toggleDisable(false, progressBar, getWindow());
                }

            };

    private void showSearchProducts(List<Products> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        searchRecyclerView = findViewById(R.id.searchRecyclerView);
        searchRecyclerView.setHasFixedSize(true);
        searchRecyclerView.setLayoutManager(layoutManager);
        searchAdapter = new SearchAdapter(this, list, this);
        searchRecyclerView.setAdapter(searchAdapter);
    }

}