package com.hassanadeola.mattire.listeners;

import com.hassanadeola.mattire.models.Products;

import java.util.List;

public interface OnFetchDataListener<Products> {

    void onFetchData(List<Products> products, String message);
    void onError(String message);
}
